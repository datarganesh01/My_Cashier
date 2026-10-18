package com.example.mycashier.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycashier.Database.AppDatabase;
import com.example.mycashier.Database.TransactionDao;
import com.example.mycashier.Model.CategorySum;
import com.example.mycashier.Model.TypeSum;
import com.example.mycashier.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsFragment extends Fragment {

    private BarChart barChart;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        // 🔹 THIS is where fragment_analytics.xml is connected
        return inflater.inflate(R.layout.fragment_analytics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ---- BarChart ----
        barChart = view.findViewById(R.id.barChartIncomeExpense);

        List<TypeSum> summary =
                AppDatabase.getInstance(requireContext())
                        .transactionDao()
                        .getIncomeExpenseSum();

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        int index = 0;
        for (TypeSum item : summary) {
            entries.add(new BarEntry(index, (float) item.total));
            labels.add(item.type); // INCOME / EXPENSE
            index++;
        }

        BarDataSet dataSet = new BarDataSet(entries, "Income vs Expense");
        BarData barData = new BarData(dataSet);

        dataSet.setColors(
                Color.parseColor("#F44336"), // Expense
                Color.parseColor("#4CAF50")  // Income
        );

        barChart.setData(barData);
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisRight().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000);
        barData.setBarWidth(0.5f);
        barChart.getAxisLeft().setDrawGridLines(true);
        barChart.getXAxis().setDrawGridLines(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        barChart.invalidate();

        // ---- PieChart ----
        PieChart pieChart = view.findViewById(R.id.pieChartCategory);
        TransactionDao transactionDao = AppDatabase.getInstance(requireContext()).transactionDao();

        List<CategorySum> allCategories = transactionDao.getCategorySums();
        List<PieEntry> pieEntries = new ArrayList<>();
        float othersTotal = 0f;

        for (int i = 0; i < allCategories.size(); i++) {
            CategorySum cs = allCategories.get(i);
            if (cs.totalAmount <= 0) continue; // ignore zero amounts
            if (i < 5) {
                pieEntries.add(new PieEntry(cs.totalAmount, cs.category));
            } else {
                othersTotal += cs.totalAmount;
            }
        }

        if (othersTotal > 0) {
            pieEntries.add(new PieEntry(othersTotal, "Others"));
        }

// If pieEntries is empty, add dummy value so chart shows
        if (pieEntries.isEmpty()) {
            pieEntries.add(new PieEntry(1f, "No Expenses"));
        }

        PieDataSet dataSetPie = new PieDataSet(pieEntries, "Top Categories");
        dataSetPie.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSetPie.setValueTextSize(12f);

        PieData pieData = new PieData(dataSetPie);
        pieChart.setData(pieData);
        pieChart.setUsePercentValues(true);
        pieChart.setCenterText("Top Expenses");
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(45f);
        pieChart.setTransparentCircleRadius(50f);

// Legend
        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);

// Entry labels
        pieChart.setDrawEntryLabels(true);

        pieChart.invalidate(); // refresh chart


    }

}
