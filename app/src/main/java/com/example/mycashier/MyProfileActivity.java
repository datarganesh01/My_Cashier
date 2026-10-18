package com.example.mycashier;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mycashier.Common.BaseActivity;
import com.example.mycashier.Database.DBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

public class MyProfileActivity extends BaseActivity {

    TextView tvToken,tvName,tvEmailID,tvMobileNo,tvUsername;
    ImageView ivEdit,ivProfilePhoto,ivSelectProfilePhoto;
    Button btnDeleteAccount,btnSwitchAccount,btnSignOut;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    DBHelper dbHelper;
    Uri imagepath;
    Bitmap bitmap;

    String oldUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_profile);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        dbHelper = new DBHelper(this);

        tvToken = findViewById(R.id.tvMyProfileToken);
        ivEdit = findViewById(R.id.ivMyProfileEdit);
        ivProfilePhoto = findViewById(R.id.ivMyProfileProfilePhoto);
        ivSelectProfilePhoto = findViewById(R.id.ivMyProfileSelectProfilePhoto);

        tvName = findViewById(R.id.tvMyProfileName);
        tvMobileNo = findViewById(R.id.tvMyProfileMobileNo);
        tvEmailID = findViewById(R.id.tvMyProfileEmailId);
        tvUsername = findViewById(R.id.tvMyProfileUsername);

        btnSwitchAccount = findViewById(R.id.btnMyProfileSwitchAccount);
        btnSignOut = findViewById(R.id.btnMyProfileSignOut);
        btnDeleteAccount = findViewById(R.id.btnMyProfileDeleteAccount);


//        tvName.setText("Name :-    "+ preferences.getString("name",""));
//        tvMobileNo.setText("Mobile No :- "+ preferences.getString("mobileno",""));
//        tvEmailID.setText("Email ID :-  "+ preferences.getString("emailid",""));
//        tvUsername.setText("Username :- "+ preferences.getString("username",""));

        oldUsername = preferences.getString("username","");


        ivSelectProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showImageChooser();
            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyProfileActivity.this,EditProfileActivity.class);
                startActivity(intent);
            }
        });

        btnSwitchAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyProfileActivity.this, "Please add another Account to switch", Toast.LENGTH_SHORT).show();
            }
        });
        
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dbHelper.deleteUser(oldUsername))
                {

                    Toast.makeText(MyProfileActivity.this, "Account Deleted Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MyProfileActivity.this,RegistrationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    editor.putBoolean("islogin",false).commit();
                    startActivity(intent);



                }
                else {
                    Toast.makeText(MyProfileActivity.this, "Unable to delete Account", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder adb = new AlertDialog.Builder(MyProfileActivity.this);
                adb.setTitle("Sign Out");
                adb.setMessage("Are you sure want to Sign Out ?");

                adb.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

                adb.setNegativeButton("Sign Out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        editor.putBoolean("islogin", false);  // Only mark login as false
                        editor.apply();
                        
                        Intent intent = new Intent(MyProfileActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
            }
        });




        tvToken.setText(preferences.getString("Token"," "));

        FirebaseInstallations.getInstance().getId()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String fid = task.getResult();
                        Toast.makeText(this, "FID: " + fid, Toast.LENGTH_LONG).show();
                    }
                });


    }

    @Override
    protected void onResume() {
        super.onResume();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        tvName.setText(preferences.getString("name",""));
        tvMobileNo.setText( preferences.getString("mobileno",""));
        tvEmailID.setText(preferences.getString("emailid",""));
        tvUsername.setText(preferences.getString("username",""));
    }

    private void showImageChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Photo"),1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 999 && resultCode == RESULT_OK &&  data != null)
        {
            imagepath = Uri.parse(data.getData().toString());

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);

                Bitmap circularBitmap = getCircularBitmap(bitmap);
                ivProfilePhoto.setImageBitmap(circularBitmap);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public Bitmap getCircularBitmap(Bitmap bitmap) {
        int width = Math.min(bitmap.getWidth(), bitmap.getHeight());

        Bitmap output = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, width);

        float radius = width / 2f;

        paint.setAntiAlias(true);
        canvas.drawCircle(radius, radius, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, null, rect, paint);

        return output;
    }

}