package com.example.mycashier.Model;

import androidx.room.ColumnInfo;

public class CategorySum {
    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "totalAmount")
    public float totalAmount;
}
