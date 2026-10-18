package com.example.mycashier;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mycashier.Common.BaseActivity;
import com.example.mycashier.Database.DBHelper;

public class RegistrationActivity extends BaseActivity {

    EditText etRegisterName,etRegisterMobileNo,etRegisterEmail,etRegisterUsername,etRegisterPassword;

    CheckBox cbShowHidePassword;

    Button btnRegisterRegister,btnRegisterGoToLoginPage;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);

        etRegisterName = findViewById(R.id.etregistername);
        etRegisterMobileNo = findViewById(R.id.etregistermobileno);
        etRegisterEmail = findViewById(R.id.etregisteremail);
        etRegisterUsername = findViewById(R.id.etregisterusername);
        etRegisterPassword = findViewById(R.id.etregisterpassword);
        cbShowHidePassword = findViewById(R.id.cbshowhidepassword);
        btnRegisterRegister = findViewById(R.id.btnregisterregister);
        btnRegisterGoToLoginPage = findViewById(R.id.btnregistergotologinpage);

        preferences = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
        editor = preferences.edit();

        dbHelper = new DBHelper(this);



        cbShowHidePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    etRegisterPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    etRegisterPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnRegisterRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strName = etRegisterName.getText().toString();
                String strMobileNo = etRegisterMobileNo.getText().toString();
                String strEmailId = etRegisterEmail.getText().toString();
                String strUsername = etRegisterUsername.getText().toString();
                String strPassword = etRegisterPassword.getText().toString();


                if (etRegisterName.getText().toString().isEmpty())
                {
                    etRegisterName.setError("Please Enter Your Name");

                } else if (etRegisterMobileNo.getText().toString().isEmpty())
                {
                    etRegisterMobileNo.setError("Please Enter Your Mobile Number");

                } else if (etRegisterMobileNo.getText().toString().length() != 10)
                {
                    etRegisterMobileNo.setError("Mobile No. must be 10 characters");

                } else if (etRegisterEmail.getText().toString().isEmpty())
                {
                    etRegisterEmail.setError("Please Enter Your E-mail");

                } else if (!Patterns.EMAIL_ADDRESS.matcher(etRegisterEmail.getText().toString()).matches()) {
                    etRegisterEmail.setError("Invalid E-mail Address");

                } else if (etRegisterUsername.getText().toString().isEmpty())
                {
                    etRegisterUsername.setError("Please Enter Your Username");

                } else if (etRegisterPassword.getText().toString().isEmpty())
                {
                    etRegisterPassword.setError("Please Enter Your Password");

                }

                else
                {

                    boolean success = addUser(strName, strMobileNo, strEmailId, strUsername, strPassword);

                    if (success) {

                        Toast.makeText(RegistrationActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(RegistrationActivity.this, "Username Already Exists", Toast.LENGTH_SHORT).show();
                        etRegisterUsername.setError("Choose a different username");
                        etRegisterUsername.requestFocus();

                    }

                }
            }
        });

        btnRegisterGoToLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i1);
            }
        });


    }

    private boolean addUser(String strName, String strMobileNo,
                         String strEmailId, String strUsername, String strPassword) {

        boolean success = dbHelper.registerUser(strName, strMobileNo, strEmailId, strUsername, strPassword);

        if (success)
        {

           // Toast.makeText(RegistrationActivity.this, "Registration Successful..!", Toast.LENGTH_SHORT).show();

            editor.putString("name",etRegisterName.getText().toString()).commit();
            editor.putString("mobileno",etRegisterMobileNo.getText().toString()).commit();
            editor.putString("emailid",etRegisterEmail.getText().toString()).commit();
            editor.putString("username",etRegisterUsername.getText().toString()).commit();

            Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
            startActivity(intent);


        }

        return success;

    }


}