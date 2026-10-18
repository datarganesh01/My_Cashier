package com.example.mycashier;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity {
    LinearLayout llMain;
    ImageView ivMainLogo;
    TextView tvMainTitle,tvMainSlogan;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        llMain=findViewById(R.id.llmain);
        ivMainLogo=findViewById(R.id.ivmainlogo);
        tvMainTitle=findViewById(R.id.tvmaintitle);
        tvMainSlogan=findViewById(R.id.tvmainslogan);

        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getIntent().getExtras()!=null)
                { Intent i1 = new Intent(SplashActivity.this,MyProfileActivity.class);
                    startActivity(i1);
                }
                else {
                    Intent i1 = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(i1);
                    finish();}

            }
        },3000);




        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SplashActivity.this, "Fetching FCM token Failed", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();


                        Toast.makeText(SplashActivity.this, token, Toast.LENGTH_SHORT).show();
                        editor.putString("Token",token).commit();
                    }
                });
    }
}