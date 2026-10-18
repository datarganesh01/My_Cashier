package com.example.mycashier;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import com.example.mycashier.Common.NetworkChangeListener;
import com.example.mycashier.Database.DBHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {


    ImageView ivLoginLogo,ivLoginGoogle,ivLoginFacebook;
    EditText etLoginUsername,etLoginPassword;
    CheckBox cbShowHidePassword;
    Button btnLogin;
    TextView tvNewLogin,tvTandC;

    public boolean doubletap = false;
    SharedPreferences preference;
    SharedPreferences.Editor editor;
    
    DBHelper dbHelper;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ivLoginLogo = findViewById(R.id.ivloginlogo);
        etLoginUsername = findViewById(R.id.etloginusername);
        etLoginPassword = findViewById(R.id.etpasswordlogin);
        cbShowHidePassword = findViewById(R.id.cbshowhidepassword);
        btnLogin = findViewById(R.id.btnlogin);
        tvNewLogin = findViewById(R.id.tvnewlogin);
        ivLoginGoogle = findViewById(R.id.ivLoginGoogle);
        ivLoginFacebook = findViewById(R.id.ivLoginFacebook);
        tvTandC = findViewById(R.id.tvTermsandCondition);

        preference = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        editor = preference.edit();
        
        dbHelper = new DBHelper(this);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // 👈 MUST match web client ID from google-services.json
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);



        if (preference.getBoolean("islogin",false))
        {
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);

            finish();  //lately added
        }


        cbShowHidePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    etLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    etLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }

        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String strUsername = etLoginUsername.getText().toString();
                String strPassword = etLoginPassword.getText().toString();
                
                

                if (etLoginUsername.getText().toString().isEmpty())
                {
                    etLoginUsername.setError("Please enter Username");

                } else if (etLoginUsername.getText().toString().length() < 8)
                {
                    etLoginUsername.setError("Username must be at least 8 character");

                } else if (etLoginPassword.getText().toString().isEmpty())
                {
                    etLoginPassword.setError("Please enter Password");

                } else if (etLoginPassword.getText().toString().length() < 8)
                {
                    etLoginPassword.setError("Password must be at least 8 character");
                }

                else {
                    
                    validateUser(strUsername,strPassword);

                    
                }
            }
        });


        tvNewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });



        ivLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
            }
        });


        ivLoginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(LoginActivity.this,FacebookLoginActivity.class);
                startActivity(i2);
            }
        });
        
        tvTandC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://en.wikipedia.org/wiki/Terms_of_service";

                Toast.makeText(LoginActivity.this, "Terms & Conditions Applies", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                startActivity(intent);
            }
        });


    }

    private void signIn() {

        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                editor.putBoolean("islogin",true).commit();
                startActivity(intent);
                finish();

            } catch (ApiException e) {
                Toast.makeText(this, "Google Sign-In failed: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

    }

    private void validateUser(String strUsername, String strPassword) {
        
        if (dbHelper.validateLogin(strUsername,strPassword))
        {
            Toast.makeText(LoginActivity.this, "Login Successful..!", Toast.LENGTH_SHORT).show();

            Intent afterlogin = new Intent(LoginActivity.this, HomeActivity.class);
            editor.putBoolean("islogin",true).commit();

            editor.putString("username",etLoginUsername.getText().toString()).commit();
            startActivity(afterlogin);
            finish();    //task 7 edit
        }
        else
        {
            Toast.makeText(this, "❌ Invalid Credentials", Toast.LENGTH_SHORT).show();
        }
        
    }

    @Override
    public void onBackPressed() {
        //laterly added
        super.onBackPressed();
        if (doubletap)
        {
            finishAffinity();
        }
        else {
            Toast.makeText(LoginActivity.this, "Press again to Exit!", Toast.LENGTH_SHORT).show();

            doubletap=true;
            Handler h1 = new Handler();
            h1.postDelayed(new Runnable() {
                @Override
                public void run() {

                    doubletap=false;

                }
            },2000);

        }

    }

    NetworkChangeListener networkChangeListener = new NetworkChangeListener(
            this, null,
            NetworkChangeListener.PopupType.ALERT_DIALOG,
            R.layout.check_internet
    );

    @Override
    protected void onStart() {



        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,intentFilter);
        super.onStart();

    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);

        super.onStop();
    }
}