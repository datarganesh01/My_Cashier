package com.example.mycashier;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.mycashier.Database.AppDatabase;
import com.example.mycashier.Model.TransactionModel;
import com.example.mycashier.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {

    private EditText etAmount, etCategory, etNote, etDate;
    private RadioGroup rgType;
    private AppCompatButton btnSave;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        etAmount = findViewById(R.id.etAmount);
        etCategory = findViewById(R.id.etCategory);
        etNote = findViewById(R.id.etNote);
        etDate = findViewById(R.id.etDate);
        rgType = findViewById(R.id.rgType);
        btnSave = findViewById(R.id.btnSave);

        calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        etDate.setText(sdf.format(calendar.getTime()));

        etDate.setOnClickListener(v -> {
            new DatePickerDialog(
                    AddTransactionActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        etDate.setText(sdf.format(calendar.getTime()));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        btnSave.setOnClickListener(v -> {

            String amountStr = etAmount.getText().toString().trim();
            String category = etCategory.getText().toString().trim();
            String note = etNote.getText().toString().trim();

            if (amountStr.isEmpty() || category.isEmpty()) {
                Toast.makeText(this, "Please fill Amount and Category", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount = Double.parseDouble(amountStr);
            String type = (rgType.getCheckedRadioButtonId() == R.id.rbIncome)
                    ? "income"
                    : "expense";

            // ✅ Create Room Entity (NO UUID)
            TransactionModel transaction = new TransactionModel(
                    type,
                    category,
                    note,
                    amount,
                    calendar.getTimeInMillis()
            );

            // ✅ INSERT INTO ROOM DATABASE
            AppDatabase.getInstance(this)
                    .transactionDao()
                    .insertTransaction(transaction);

            Toast.makeText(this, "Transaction Saved", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
