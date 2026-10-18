package com.example.mycashier;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CalculatorActivity extends AppCompatActivity {

    private TextView tvResult;
    private String input = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        tvResult = findViewById(R.id.tvResult);

        setNumberClick(R.id.btn0, "0");
        setNumberClick(R.id.btn1, "1");
        setNumberClick(R.id.btn2, "2");
        setNumberClick(R.id.btn3, "3");
        setNumberClick(R.id.btn4, "4");
        setNumberClick(R.id.btn5, "5");
        setNumberClick(R.id.btn6, "6");
        setNumberClick(R.id.btn7, "7");
        setNumberClick(R.id.btn8, "8");
        setNumberClick(R.id.btn9, "9");
        setNumberClick(R.id.btnDot, ".");

        setOperatorClick(R.id.btnPlus, "+");
        setOperatorClick(R.id.btnMinus, "-");
        setOperatorClick(R.id.btnMultiply, "*");
        setOperatorClick(R.id.btnDivide, "/");

        findViewById(R.id.btnClear).setOnClickListener(v -> {
            input = "";
            tvResult.setText("0");
        });

        findViewById(R.id.btnDelete).setOnClickListener(v -> {
            if (!input.isEmpty()) {
                input = input.substring(0, input.length() - 1);
                tvResult.setText(input.isEmpty() ? "0" : input);
            }
        });

        findViewById(R.id.btnEquals).setOnClickListener(v -> {
            try {
                double result = eval(input);
                tvResult.setText(String.valueOf(result));
                input = String.valueOf(result);
            } catch (Exception e) {
                tvResult.setText("Error");
                input = "";
            }
        });
    }

    private void setNumberClick(int id, String value) {
        findViewById(id).setOnClickListener(v -> {
            input += value;
            tvResult.setText(input);
        });
    }

    private void setOperatorClick(int id, String value) {
        findViewById(id).setOnClickListener(v -> {
            if (!input.isEmpty()) {
                input += value;
                tvResult.setText(input);
            }
        });
    }

    // ✅ Using exp4j instead of Rhino
    private double eval(String expr) {
        Expression expression = new ExpressionBuilder(expr).build();
        return expression.evaluate();
    }
}
