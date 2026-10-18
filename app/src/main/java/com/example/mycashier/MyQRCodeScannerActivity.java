package com.example.mycashier;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mycashier.Common.BaseActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MyQRCodeScannerActivity extends BaseActivity {

    AppCompatButton btnTaptoScan;
    TextView tvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_qrcode_scanner);

        btnTaptoScan = findViewById(R.id.btnMYQRCodeScannerTaptoScan);
        tvData = findViewById(R.id.tvMyQRCodeScannerData);

        btnTaptoScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator intentIntegrator = new IntentIntegrator(MyQRCodeScannerActivity.this);
                intentIntegrator.setPrompt("Scan any QR Code");
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.initiateScan();
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setTorchEnabled(true);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result !=null && result.getContents()!=null)
        {
            tvData.setText(result.getContents());
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}