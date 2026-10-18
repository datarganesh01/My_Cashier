package com.example.mycashier;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class TextDetectionActivity extends AppCompatActivity {

    ImageView ivCaptureImage;
    AppCompatButton btnCapture;
    TextView tvExtractedText;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_detection);

        ivCaptureImage = findViewById(R.id.ivTextDetectionImage);
        btnCapture = findViewById(R.id.btnTextDetectionCapture);
        tvExtractedText = findViewById(R.id.tvTextDetectionExtractedText);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},999);
        }

//        btnCapture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,999);
//            }
//        });


        btnCapture.setOnClickListener(v ->
        {
            String[] options = {"Camera", "Gallery"};

            new androidx.appcompat.app.AlertDialog.Builder(TextDetectionActivity.this)
                    .setTitle("Select Image")
                    .setItems(options, (dialog, which) -> {
                        if (which == 0) {
                            // Camera
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 999);
                        } else {
                            // Gallery
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 1000);
                        }
                    })
                    .show();
        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode==999 && resultCode == RESULT_OK && data!=null)
//        {
//            bitmap = (Bitmap) data.getExtras().get("data");
//            ivCaptureImage.setImageBitmap(bitmap);
//
//            runTextRecognition(bitmap);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 999 && data != null)
            {
                //Camera
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ivCaptureImage.setImageBitmap(bitmap);
                runTextRecognition(bitmap);


            }

            else if (requestCode == 1000 && data != null)
            {
                //Gallery
                Uri selectedImage = data.getData();

                ivCaptureImage.setImageURI(selectedImage);

                try {
                    InputImage image = InputImage.fromFilePath(this, selectedImage);

                    TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

                    recognizer.process(image)
                            .addOnSuccessListener(this::processTextRecognitionResult)
                            .addOnFailureListener(e -> Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


    private void runTextRecognition(Bitmap bitmap) {

        InputImage image = InputImage.fromBitmap(bitmap,0);

        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        recognizer.process(image).addOnSuccessListener(this::processTextRecognitionResult).addOnFailureListener
                (e -> Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show());


    }

    private void processTextRecognitionResult(Text text)
    {
        StringBuilder stringBuilder = new StringBuilder();

        for (Text.TextBlock block : text.getTextBlocks())
        {
            stringBuilder.append(block.getText()).append("\n");

        }

        tvExtractedText.setText(stringBuilder.toString());

    }
}