package com.edson.personalfinance.sqlite.database.model;

public class Revenu {
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DATETIME = "dateTime";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_VALUE = "value";

    public static final String TABLE_NAME = "revenu";

    private int id;
    private String category;
    private String dateTime;
    private String description;
    private double value;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_VALUE + " DOUBLE, " +
            COLUMN_DATETIME + " DATETIME," +
            COLUMN_CATEGORY + " VARCHAR(20)," +
            COLUMN_DESCRIPTION + " TEXT" +
            ")";

    public Revenu() {
    }

    public Revenu(int id, String category, String dateTime, String description, double value) {
        this.id = id;
        this.category = category;
        this.dateTime = dateTime;
        this.description = description;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
