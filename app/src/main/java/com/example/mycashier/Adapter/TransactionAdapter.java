package com.example.mycashier.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycashier.Database.AppDatabase;
import com.example.mycashier.Database.TransactionDao;
import com.example.mycashier.Model.TransactionModel;
import com.example.mycashier.R;

import java.util.List;
import java.util.concurrent.Executors;
import com.example.mycashier.Adapter.OnTransactionChangeListener;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<TransactionModel> list;
    private Context context;
    private TransactionDao transactionDao;
    private OnTransactionChangeListener listener;


    public TransactionAdapter(
            List<TransactionModel> list,
            Context context,
            OnTransactionChangeListener listener) {

        this.list = list;
        this.context = context;
        this.listener = listener;

        transactionDao = AppDatabase
                .getInstance(context)
                .transactionDao();
    }


    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionModel transaction = list.get(position);
        holder.tvCategory.setText(transaction.getCategory());
        holder.tvNote.setText(transaction.getNote());
        holder.tvAmount.setText("₹" + transaction.getAmount());

        if (transaction.getType().equals("income")) {
            holder.tvAmount.setTextColor(Color.parseColor("#388E3C")); // green
        } else {
            holder.tvAmount.setTextColor(Color.parseColor("#D32F2F")); // red
        }

        holder.itemView.setOnLongClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setTitle("Delete Transaction")
                    .setMessage("Are you sure you want to delete this transaction?")
                    .setPositiveButton("Delete", (dialog, which) -> {

                        Executors.newSingleThreadExecutor().execute(() -> {
                            transactionDao.deleteTransaction(list.get(position));
                            list.remove(position);

                            ((Activity) context).runOnUiThread(() -> {
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, list.size());

                                if (listener != null) {
                                    listener.onTransactionChanged(); // 🔥 THIS LINE
                                }
                            });
                        });

                    })
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvNote, tvAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvNote = itemView.findViewById(R.id.tvNote);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
