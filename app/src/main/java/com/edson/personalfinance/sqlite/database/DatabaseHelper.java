package com.edson.personalfinance.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.edson.personalfinance.sqlite.database.model.Depense;
import com.edson.personalfinance.sqlite.database.model.Revenu;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "personal_finance";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Depense.CREATE_TABLE);
        db.execSQL(Revenu.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Depense.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Revenu.TABLE_NAME);
        onCreate(db);
    }

    public String insertDepense(@NotNull Depense depense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String result = "";

        values.put(Depense.COLUMN_CATEGORY, depense.getCategory());//insert category
        values.put(Depense.COLUMN_DATETIME, depense.getDateTime());//insert datetime
        values.put(Depense.COLUMN_DESCRIPTION, depense.getDescription());//insert description
        values.put(Depense.COLUMN_VALUE, depense.getValue());//insert value
        long id = db.insert(Depense.TABLE_NAME, null, values);//insert all values in table
        if (id == -1) {
            result = "Une erreur s’est produite !";
        } else {
            result = "Depense inséré !";
        }
        db.close();
        return result;
    }

    public int updateDepense(@NotNull Depense depense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Depense.COLUMN_CATEGORY, depense.getCategory());
        values.put(Depense.COLUMN_DATETIME, depense.getDateTime());
        values.put(Depense.COLUMN_DESCRIPTION, depense.getDescription());
        values.put(Depense.COLUMN_VALUE, depense.getValue());

        return db.update(Depense.TABLE_NAME, values, Depense.COLUMN_ID + " = ?",
                new String[]{String.valueOf(depense.getId())});
    }

    public List<Depense> getAllDepenses() {
        List<Depense> depenses = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Depense.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Depense depense = new Depense();
                depense.setId(cursor.getInt(cursor.getColumnIndex(Depense.COLUMN_ID)));
                depense.setCategory(cursor.getString(cursor.getColumnIndex(Depense.COLUMN_CATEGORY)));
                depense.setDateTime(cursor.getString(cursor.getColumnIndex(Depense.COLUMN_DATETIME)));
                depense.setDescription(cursor.getString(cursor.getColumnIndex(Depense.COLUMN_DESCRIPTION)));
                depense.setValue(cursor.getDouble(cursor.getColumnIndex(Depense.COLUMN_VALUE)));

                depenses.add(depense);
            } while (cursor.moveToNext());
        }

        db.close();

        return depenses;
    }

    public String insertRevenu(@NotNull Revenu revenu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String result = "";

        values.put(Revenu.COLUMN_CATEGORY, revenu.getCategory());//insert category
        values.put(Revenu.COLUMN_DATETIME, revenu.getDateTime());//insert datetime
        values.put(Revenu.COLUMN_DESCRIPTION, revenu.getDescription());//insert description
        values.put(Revenu.COLUMN_VALUE, revenu.getValue());//insert value
        long id = db.insert(Revenu.TABLE_NAME, null, values);//insert all values in table
        if (id == -1) {
            result = "Une erreur s’est produite !";
        } else {
            result = "Revenu inséré !";
        }
        db.close();
        return result;
    }

    public int updateRevenu(@NotNull Revenu revenu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Revenu.COLUMN_CATEGORY, revenu.getCategory());
        values.put(Revenu.COLUMN_DATETIME, revenu.getDateTime());
        values.put(Revenu.COLUMN_DESCRIPTION, revenu.getDescription());
        values.put(Revenu.COLUMN_VALUE, revenu.getValue());

        return db.update(Revenu.TABLE_NAME, values, Revenu.COLUMN_ID + " = ?",
                new String[]{String.valueOf(revenu.getId())});
    }

    public List<Revenu> getAllRevenus() {
        List<Revenu> revenus = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Revenu.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Revenu revenu = new Revenu();
                revenu.setId(cursor.getInt(cursor.getColumnIndex(Revenu.COLUMN_ID)));
                revenu.setCategory(cursor.getString(cursor.getColumnIndex(Revenu.COLUMN_CATEGORY)));
                revenu.setDateTime(cursor.getString(cursor.getColumnIndex(Revenu.COLUMN_DATETIME)));
                revenu.setDescription(cursor.getString(cursor.getColumnIndex(Revenu.COLUMN_DESCRIPTION)));
                revenu.setValue(cursor.getDouble(cursor.getColumnIndex(Revenu.COLUMN_VALUE)));

                revenus.add(revenu);
            } while (cursor.moveToNext());
        }

        db.close();

        return revenus;
    }
}
