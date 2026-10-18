package com.example.mycashier.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycashier.Model.HistoryModel;
import com.example.mycashier.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;
    List<HistoryModel> historyModelList;

    public HistoryAdapter(Context context, List<HistoryModel> historyModelList) {
        this.context = context;
        this.historyModelList = historyModelList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvHistoryName,tvHistoryTime,tvHistoryType,tvHistoryAmount;
        AppCompatButton btnViewDetails;

        public ViewHolder(View view)
        {
            super(view);

            tvHistoryName = view.findViewById(R.id.tvhistoryitemsname);
            tvHistoryTime = view.findViewById(R.id.tvhistoryitemstime);
            tvHistoryType = view.findViewById(R.id.tvhistoryitemstype);
            tvHistoryAmount = view.findViewById(R.id.tvhistoryitemsamount);

            btnViewDetails = view.findViewById(R.id.btnhistoryitemsviewdetails);



        }
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.history_items,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder viewHolder, int position) {

        HistoryModel historyModel = historyModelList.get(position);

        viewHolder.tvHistoryName.setText(historyModel.getHistoryName());
        viewHolder.tvHistoryTime.setText(historyModel.getHistoryTime());
        viewHolder.tvHistoryType.setText(historyModel.getHistoryType());
        viewHolder.tvHistoryAmount.setText(historyModel.getHistoryAmount());

        viewHolder.btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Details Viewed", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return historyModelList.size();
    }
}
