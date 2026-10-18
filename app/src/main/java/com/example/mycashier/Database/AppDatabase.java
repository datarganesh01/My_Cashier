package com.example.mycashier.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mycashier.Model.TransactionModel;

@Database(
        entities = {TransactionModel.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    // DAO reference
    public abstract TransactionDao transactionDao();

    // Singleton instance
    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "mycashier_db"
                    )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // for learning (we’ll improve later)
                    .build();
        }
        return INSTANCE;
    }
}
