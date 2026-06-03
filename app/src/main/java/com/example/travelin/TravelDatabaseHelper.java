package com.example.travelin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TravelDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "travelin.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_TRIPS = "trips";
    public static final String TABLE_STEPS = "steps";
    public static final String TABLE_STEP_PHOTOS = "step_photos";

    public TravelDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_TRIPS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "start_date TEXT, " +
                "end_date TEXT, " +
                "hotel_phone TEXT, " +
                "cover_image_res INTEGER NOT NULL, " +
                "created_at INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_STEPS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "trip_id INTEGER NOT NULL, " +
                "location_name TEXT NOT NULL, " +
                "description TEXT, " +
                "date TEXT, " +
                "time TEXT, " +
                "latitude REAL, " +
                "longitude REAL, " +
                "created_at INTEGER NOT NULL, " +
                "FOREIGN KEY(trip_id) REFERENCES trips(id) ON DELETE CASCADE)");

        db.execSQL("CREATE TABLE " + TABLE_STEP_PHOTOS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "step_id INTEGER NOT NULL, " +
                "photo_uri TEXT NOT NULL, " +
                "created_at INTEGER NOT NULL, " +
                "FOREIGN KEY(step_id) REFERENCES steps(id) ON DELETE CASCADE)");

        seedTrips(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STEP_PHOTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STEPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);
        onCreate(db);
    }

    public long insertTrip(String name, String startDate, String endDate, String hotelPhone) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("start_date", startDate);
        values.put("end_date", endDate);
        values.put("hotel_phone", hotelPhone);
        values.put("cover_image_res", R.drawable.travel_beach_bg);
        values.put("created_at", System.currentTimeMillis());
        return getWritableDatabase().insert(TABLE_TRIPS, null, values);
    }

    public long insertStep(long tripId, String locationName, String description, String date, String time) {
        ContentValues values = new ContentValues();
        values.put("trip_id", tripId);
        values.put("location_name", locationName);
        values.put("description", description);
        values.put("date", date);
        values.put("time", time);
        values.putNull("latitude");
        values.putNull("longitude");
        values.put("created_at", System.currentTimeMillis());
        return getWritableDatabase().insert(TABLE_STEPS, null, values);
    }

    public void insertStepPhoto(long stepId, String photoUri) {
        ContentValues values = new ContentValues();
        values.put("step_id", stepId);
        values.put("photo_uri", photoUri);
        values.put("created_at", System.currentTimeMillis());
        getWritableDatabase().insert(TABLE_STEP_PHOTOS, null, values);
    }

    public List<Trip> getTrips() {
        List<Trip> trips = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT t.id, t.name, t.start_date, t.end_date, t.cover_image_res, COUNT(s.id) AS step_count " +
                        "FROM trips t LEFT JOIN steps s ON s.trip_id = t.id " +
                        "GROUP BY t.id ORDER BY t.start_date", null);
        try {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(0);
                String name = cursor.getString(1);
                String startDate = cursor.getString(2);
                String endDate = cursor.getString(3);
                int cover = cursor.getInt(4);
                int stepCount = cursor.getInt(5);
                trips.add(new Trip(id, null, name, formatDates(startDate, endDate),
                        stepCount + (stepCount == 1 ? " step" : " steps"), cover));
            }
        } finally {
            cursor.close();
        }
        return trips;
    }

    public List<TripStep> getStepsForTrip(long tripId) {
        List<TripStep> steps = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT id, location_name, description, date, time FROM steps WHERE trip_id = ? ORDER BY date, time",
                new String[]{String.valueOf(tripId)});
        try {
            while (cursor.moveToNext()) {
                steps.add(new TripStep(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)));
            }
        } finally {
            cursor.close();
        }
        return steps;
    }

    public String getHotelPhone(long tripId) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT hotel_phone FROM trips WHERE id = ?",
                new String[]{String.valueOf(tripId)});
        try {
            if (cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return "";
        } finally {
            cursor.close();
        }
    }

    private void seedTrips(SQLiteDatabase db) {
        insertSeedTrip(db, "Maldives Paradise", "15/06/2024", "22/06/2024", "+9601234567", R.drawable.travel_beach_bg);
        insertSeedTrip(db, "Paris & Brussels", "05/08/2024", "18/08/2024", "+33123456789", R.drawable.travel_balloons_bg);
    }

    private void insertSeedTrip(SQLiteDatabase db, String name, String start, String end, String phone, int cover) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("start_date", start);
        values.put("end_date", end);
        values.put("hotel_phone", phone);
        values.put("cover_image_res", cover);
        values.put("created_at", System.currentTimeMillis());
        db.insert(TABLE_TRIPS, null, values);
    }

    private String formatDates(String startDate, String endDate) {
        if (startDate == null || startDate.trim().isEmpty()) {
            return "";
        }
        if (endDate == null || endDate.trim().isEmpty()) {
            return startDate;
        }
        return startDate + " - " + endDate;
    }
}
