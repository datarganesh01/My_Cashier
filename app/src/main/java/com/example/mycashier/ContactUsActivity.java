package com.example.mycashier;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mycashier.Common.BaseActivity;

public class ContactUsActivity extends BaseActivity {

    EditText etFirstName,etLastName,etMobileNo,etSMSMessage;
    AppCompatButton btnSendSMS;
    TextView tvClickHereViaEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_us);

        etFirstName = findViewById(R.id.etContactUsFirstName);
        etLastName = findViewById(R.id.etContactUsLastName);
        etMobileNo = findViewById(R.id.etContactUsMobileNo);
        etSMSMessage = findViewById(R.id.etContactUsMessage);

        btnSendSMS = findViewById(R.id.btnContactUsSendSMS);

        tvClickHereViaEmail = findViewById(R.id.tvContactUsClickHere);


        if (ActivityCompat.checkSelfPermission(ContactUsActivity.this,Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(ContactUsActivity.this, new String[]{
                    Manifest.permission.SEND_SMS
            },111);
        }


        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                try {
                    String strSMSMobileNo = etMobileNo.getText().toString();
                    String strSMSMessage = etSMSMessage.getText().toString();

                    SmsManager smsManager = SmsManager.getDefault();

                    smsManager.sendTextMessage(strSMSMobileNo,null,
                            strSMSMessage,null,null);

                    Toast.makeText(ContactUsActivity.this, "SMS Successfully send ", Toast.LENGTH_SHORT).show();
                    etFirstName.setText("");
                    etLastName.setText("");
                    etMobileNo.setText("");
                    etSMSMessage.setText("");
                    
                } catch (Exception e) {
                    Toast.makeText(ContactUsActivity.this, "Unable To Send SMS", Toast.LENGTH_SHORT).show();
                }

                
                
            }
        });

        tvClickHereViaEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ContactUsActivity.this, ContactUs_ViaEmailActivity.class);
                startActivity(intent);

            }
        });
    }
}