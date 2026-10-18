package com.example.mycashier.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import static android.app.Activity.RESULT_OK;



import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.mycashier.R;

import java.util.ArrayList;
import java.util.Locale;


public class SearchFragment extends Fragment {


EditText etSpeechToText;
ImageView ivMic;
private static final int REQUEST_CODE_SPEECH_INPUT = 1;
TextToSpeech textToSpeech;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        Toast.makeText(getActivity(), "Search Fragment", Toast.LENGTH_SHORT).show();

        etSpeechToText = view.findViewById(R.id.etSearchFragmentSpeechtoText);
        ivMic = view.findViewById(R.id.ivSearchFragmentMic);

        ivMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
//                        Locale.getDefault());

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hi-IN");

                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Speak Now");

                try {
                   startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==1)
        {
            if (resultCode == RESULT_OK && data!=null)
            {

                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                etSpeechToText.setText(result.get(0));

                textToSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
   //                    textToSpeech.setLanguage(Locale.ENGLISH);

                        textToSpeech.setLanguage(new Locale("hi", "IN"));

                        textToSpeech.speak(result.get(0),TextToSpeech.QUEUE_FLUSH,null,null);
                    }
                });


            }
        }
    }
}

