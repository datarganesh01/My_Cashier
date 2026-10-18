package com.example.mycashier.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mycashier.Adapter.HistoryAdapter;
import com.example.mycashier.Model.HistoryModel;
import com.example.mycashier.R;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {

    //Task 18

    RecyclerView recyclerView;
    List<HistoryModel> historyModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        Toast.makeText(getActivity(), "History Fragment", Toast.LENGTH_SHORT).show();


        recyclerView = view.findViewById(R.id.rvHistory);

        historyModels = new ArrayList<>();

        historyModels.add(new HistoryModel(1,"Ganesh Datar","31 Jul 2025 11:11 AM",
                "Received","Rs. 1000/-" ));

        historyModels.add(new HistoryModel(2,"Soham Kshirsagar","01 Aug 2025 12:04 AM",
                "Received","Rs. 1500/-" ));

        historyModels.add(new HistoryModel(3,"Bhushan Datar","20 Jul 2025 09:52 PM",
                "Received","Rs. 500/-" ));

        historyModels.add(new HistoryModel(4,"Karan Bari","10 May 2025 07:11 PM",
                "Received","Rs. 800/-" ));

        historyModels.add(new HistoryModel(4,"Prem Kothalkar","15 Feb 2025 05:10 AM",
                "Received","Rs. 100/-" ));


        HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(),historyModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(historyAdapter);




        return view;
    }
}