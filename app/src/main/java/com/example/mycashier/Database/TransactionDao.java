package com.example.mycashier.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mycashier.Model.CategorySum;
import com.example.mycashier.Model.TransactionModel;
import com.example.mycashier.Model.TypeSum;

import java.util.List;

@Dao
public interface TransactionDao {

    // 1️⃣ Insert transaction
    @Insert
    void insertTransaction(TransactionModel transaction);

    // 2️⃣ Get all transactions (latest first)
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    List<TransactionModel> getAllTransactions();



    // 3️⃣ Delete ONE transaction
    @Delete
    void deleteTransaction(TransactionModel transaction);

    // 4️⃣ Delete ALL transactions (optional)
    @Query("DELETE FROM transactions")
    void deleteAllTransactions();

    @Query("SELECT type, SUM(amount) AS total FROM transactions GROUP BY type")
    List<TypeSum> getIncomeExpenseSum();

    //pie chart
    @Query("SELECT category, SUM(amount) as totalAmount " +
            "FROM transactions " +
            "WHERE type = 'Expense' " +
            "GROUP BY category " +
            "ORDER BY totalAmount DESC")
    List<CategorySum> getCategorySums();



}
