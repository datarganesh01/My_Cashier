package com.example.mycashier;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mycashier.Common.BaseActivity;
import com.example.mycashier.Database.DBHelper;


public class EditProfileActivity extends BaseActivity {

    EditText etName,etMobileNo,etEmailID,etUsername,etPassword,etConfirmPassword;
    Button btnUpdate;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    DBHelper dbHelper;
    String oldUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);

        etName = findViewById(R.id.etEditProfileName);
        etMobileNo = findViewById(R.id.etEditProfileMobileNo);
        etEmailID = findViewById(R.id.etEditProfileEmailID);
        etUsername = findViewById(R.id.etEditProfileUsername);
        etPassword = findViewById(R.id.etEditProfilePassword);
        etConfirmPassword = findViewById(R.id.etEditProfileConfirmPassword);
        btnUpdate = findViewById(R.id.btnEditProfileUpdate);

        preferences = PreferenceManager.getDefaultSharedPreferences(EditProfileActivity.this);
        editor = preferences.edit();

        dbHelper = new DBHelper(this);

        etName.setText(preferences.getString("name","Name not found"));
        etMobileNo.setText(preferences.getString("mobileno","Mobile No. not found"));
        etEmailID.setText(preferences.getString("emailid","Email ID not found"));
        etUsername.setText(preferences.getString("username","Username not found"));
        oldUsername = preferences.getString("username","");



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = etName.getText().toString();
                String strMobileno = etMobileNo.getText().toString();
                String strEmailid = etEmailID.getText().toString();
                String strUsername = etUsername.getText().toString();


//                editor.putString("name",etName.getText().toString()).commit();
//                editor.putString("mobileno",etMobileNo.getText().toString()).commit();
//                editor.putString("emailid",etEmailID.getText().toString()).commit();
//                editor.putString("username",etUsername.getText().toString()).commit();
//
//                Toast.makeText(EditProfileActivity.this, "Profile Updated Successfully",
//                        Toast.LENGTH_SHORT).show();
//                finish();


                if (dbHelper.updateUser(oldUsername,strName,strMobileno,strEmailid,strUsername))
                {
                    Toast.makeText(EditProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(EditProfileActivity.this,MyProfileActivity.class);

                    editor.putString("name",strName).commit();
                    editor.putString("mobileno",strMobileno).commit();
                    editor.putString("emailid",strEmailid).commit();
                    editor.putString("username",strUsername).commit();

                    startActivity(intent);
                }
                else {
                    Toast.makeText(EditProfileActivity.this, "Profile Updating Failed", Toast.LENGTH_SHORT).show();
                }




            }
        });





    }
}