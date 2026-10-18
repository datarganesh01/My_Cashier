package com.example.mycashier;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mycashier.Common.BaseActivity;

public class ContactUs_ViaEmailActivity extends BaseActivity {
    
    EditText etEmailFirstName, etEmailLastName,etEmailEmailID,etEmailSubject,etEmailMessage;
    AppCompatButton btnSendEmail;
    TextView tvClickHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_us_via_email);
        
        etEmailFirstName = findViewById(R.id.etContactUsViaEmailFirstName);
        etEmailLastName = findViewById(R.id.etContactUsViaEmailLastName);
        etEmailEmailID = findViewById(R.id.etContactUsViaEmailEmailid);
        etEmailSubject = findViewById(R.id.etContactUsViaEmailSubject);
        etEmailMessage = findViewById(R.id.etContactUsViaEmailMessage);
        
        btnSendEmail = findViewById(R.id.btnContactUsViaEmailSendEmail);
        
        tvClickHere = findViewById(R.id.tvContactUsViaEmailClickHere);
        
        
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String strEmailEmailID = etEmailEmailID.getText().toString();
                String strEmailSubject = etEmailSubject.getText().toString();
                String strEmailMessage = etEmailMessage.getText().toString();


                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{strEmailEmailID});
                intent.putExtra(Intent.EXTRA_SUBJECT,strEmailSubject);
                intent.putExtra(Intent.EXTRA_TEXT,strEmailMessage);
                
                try {
                    
                    startActivity(Intent.createChooser(intent,"Choose Email App"));
                    
                } catch (Exception e) {

                    Toast.makeText(ContactUs_ViaEmailActivity.this, "Unable to Send", Toast.LENGTH_SHORT).show();                }
            }
        });

        tvClickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ContactUs_ViaEmailActivity.this, ContactUsActivity.class);
                startActivity(intent);

            }
        });

    }
}