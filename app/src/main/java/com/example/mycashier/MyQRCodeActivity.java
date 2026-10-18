package com.example.mycashier;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mycashier.Common.BaseActivity;
import com.example.mycashier.Common.NetworkChangeListener;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MyQRCodeActivity extends BaseActivity {
    ImageView ivQRCode;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ViewGroup rootLayout;
    NetworkChangeListener networkChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qrcode);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor= preferences.edit();

        ivQRCode= findViewById(R.id.ivMyQRCodeQRCode);

        try {

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
          Bitmap bitmap = barcodeEncoder.encodeBitmap(preferences.getString("username",""),
                    BarcodeFormat.QR_CODE,400,400);
          ivQRCode.setImageBitmap(bitmap);


        }
        catch (Exception e)
        {
            Toast.makeText(MyQRCodeActivity.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }


        rootLayout = findViewById(R.id.rootLayout); // Must be after setContentView
        networkChangeListener = new NetworkChangeListener(
                this,
                rootLayout,
                NetworkChangeListener.PopupType.BOTTOM_BANNER,
                R.layout.check_internet_like_airtel
        );
    }



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