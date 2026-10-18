package com.example.mycashier.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;

import android.widget.TextView;
import android.widget.Toast;
import static android.app.Activity.RESULT_OK;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.mycashier.CalculatorActivity;
import com.example.mycashier.Common.NetworkChangeListener;
import com.example.mycashier.HomeActivity;
import com.example.mycashier.R;
import com.example.mycashier.SettingsActivity;

import java.util.ArrayList;
import java.util.Locale;


public class HomeFragment extends Fragment {

    ImageSlider imageSlider;

    SearchView searchView;
    ImageView ivMic;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    TextToSpeech textToSpeech;
    GridLayout gridLayout;
    String[] uiName = {"Transactions","Reports","Data",
                       "Calculator","History","Settings"};
    String[] uiDetails = {"Manage Expenses","View Records","Your Data",
                          "Calculator","Past records","Settings"};
    int[] uiImage = {R.drawable.transactions,R.drawable.reports,
                     R.drawable.data,R.drawable.calculator,
                     R.drawable.transactionforis,R.drawable.settings2};

    ArrayList<View> gridItemViews = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageSlider = view.findViewById(R.id.isimageslider);

        ArrayList<SlideModel> slideModelArrayList = new ArrayList<>();

        slideModelArrayList.add(new SlideModel(R.drawable.mycashierlogo_removebg,
                "Welcome to My Cashier", ScaleTypes.CENTER_INSIDE));

        slideModelArrayList.add(new SlideModel(R.drawable.transactionforis,
                "Manage your Expenses", ScaleTypes.CENTER_INSIDE));

        slideModelArrayList.add(new SlideModel(R.drawable.historyforis2,
                "Keep Track  of Your Expenses", ScaleTypes.CENTER_INSIDE));

        imageSlider.setImageList(slideModelArrayList);
        imageSlider.setSlideAnimation(AnimationTypes.DEPTH_SLIDE);


        // /////////////////////////////////////////////////////////////////////////////////////////

        searchView = view.findViewById(R.id.svHomeFragmentSearch);
        searchView.setIconified(false);
        searchView.clearFocus();

        ivMic = view.findViewById(R.id.ivHomeFragmentMic);

        AutoCompleteTextView searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        if (searchAutoComplete != null) {
            searchAutoComplete.setTextColor(Color.BLACK);
            searchAutoComplete.setHintTextColor(Color.GRAY);
        } else {
            Toast.makeText(getContext(), "Could not access SearchView text field", Toast.LENGTH_SHORT).show();
        }


        ivMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());

                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Search here");

               try {
                   startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
               }
               catch (Exception e)
               {
                   Toast.makeText(getActivity(), ""+e.toString(), Toast.LENGTH_SHORT).show();
               }


            }
        });




        gridLayout = view.findViewById(R.id.glHomeFragmentGrid);

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

        for (int i=0; i<uiName.length; i++)
        {

            View view1 = layoutInflater.inflate(R.layout.custom_list,null);

            ImageView image = view1.findViewById(R.id.ivCustomListImage);
            TextView name = view1.findViewById(R.id.tvCustomListOverlapText);
            TextView details = view1.findViewById(R.id.tvCustomListDetails);

            image.setImageResource(uiImage[i]);
            name.setText(uiName[i]);
            details.setText(uiDetails[i]);


            gridItemViews.add(view1); // Store for filtering
            gridLayout.addView(view1);

            int finalI = i;
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String clickedItem = uiName[finalI];
                    //Toast.makeText(getActivity(), clickedItem + " clicked", Toast.LENGTH_SHORT).show();

                    if (clickedItem.equals("History")) {
                        // Ask MainActivity to switch to HistoryFragment (e.g., fragment index 4)
                        if (getActivity() instanceof HomeActivity) {
                            ((HomeActivity) getActivity()).switchToFragment(3); // 4 is HistoryFragment
                        }
                    }else if (clickedItem.equals("Transactions")) {
                        // Ask MainActivity to switch to HistoryFragment (e.g., fragment index 4)
                        if (getActivity() instanceof HomeActivity) {
                            ((HomeActivity) getActivity()).switchToFragment(1); // 4 is HistoryFragment
                        }
                    }else if (clickedItem.equals("Calculator")) {
                         Intent intent = new Intent(getActivity(), CalculatorActivity.class);
                         startActivity(intent);

                    } else if (clickedItem.equals("Settings")) {
                        Intent intent = new Intent(getActivity(), SettingsActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getActivity(), "Feature coming soon!", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // We handle filtering in real-time
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterGridItems(newText);
                return true;
            }
        });


        return view;
    }


    private void filterGridItems(String query) {
        for (int i = 0; i < gridItemViews.size(); i++) {
            View itemView = gridItemViews.get(i);
            TextView nameView = itemView.findViewById(R.id.tvCustomListOverlapText);
            String itemName = nameView.getText().toString().toLowerCase();

            if (itemName.contains(query.toLowerCase())) {
                itemView.setVisibility(View.VISIBLE);
            } else {
                itemView.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK && data!=null)
            {

                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                searchView.setQuery(result.get(0),false);

                textToSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {

                        textToSpeech.setLanguage(Locale.ENGLISH);
                        textToSpeech.speak(result.get(0),TextToSpeech.QUEUE_FLUSH,null,null);
                    }
                });

            }
        }


    }





}