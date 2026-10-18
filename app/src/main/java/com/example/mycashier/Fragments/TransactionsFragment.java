package com.example.mycashier.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycashier.AddTransactionActivity;
import com.example.mycashier.Adapter.TransactionAdapter;
import com.example.mycashier.Database.AppDatabase;
import com.example.mycashier.Model.TransactionModel;
import com.example.mycashier.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import com.example.mycashier.Adapter.OnTransactionChangeListener;


public class TransactionsFragment extends Fragment
        implements OnTransactionChangeListener {

    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private FloatingActionButton fabAdd;
    private List<TransactionModel> transactionList;

    private TextView tvIncome, tvExpense, tvBalance;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        recyclerView = view.findViewById(R.id.rvTransactions);
        fabAdd = view.findViewById(R.id.fabAddTransaction);

        tvIncome = view.findViewById(R.id.tvIncome);
        tvExpense = view.findViewById(R.id.tvExpense);
        tvBalance = view.findViewById(R.id.tvBalance);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        transactionList = new ArrayList<>();
        adapter = new TransactionAdapter(
                transactionList,
                requireContext(),
                this   // 👈 fragment acts as listener
        );
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), AddTransactionActivity.class))
        );

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTransactions(); // 🔥 reload + recalc
    }

    private void loadTransactions() {
        Executors.newSingleThreadExecutor().execute(() -> {

            List<TransactionModel> dbList = AppDatabase
                    .getInstance(requireContext())
                    .transactionDao()
                    .getAllTransactions();

            requireActivity().runOnUiThread(() -> {
                transactionList.clear();
                transactionList.addAll(dbList);
                adapter.notifyDataSetChanged();

                updateSummary(); // ✅ THIS FIXES YOUR PROBLEM
            });
        });
    }

    private void updateSummary() {
        double income = 0, expense = 0;

        for (TransactionModel t : transactionList) {
            if ("income".equals(t.getType())) {
                income += t.getAmount();
            } else {
                expense += t.getAmount();
            }
        }

        double balance = income - expense;

        tvIncome.setText("Income: ₹" + income);
        tvExpense.setText("Expense: ₹" + expense);
        tvBalance.setText("Balance: ₹" + balance);
    }

    @Override
    public void onTransactionChanged() {
        updateSummary();
    }
}
