package com.example.travelin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TripDetailActivity extends Activity {
    public static final String EXTRA_TRIP_NAME = "extra_trip_name";
    public static final String EXTRA_TRIP_DATES = "extra_trip_dates";
    public static final String EXTRA_TRIP_IMAGE = "extra_trip_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        String tripName = getIntent().getStringExtra(EXTRA_TRIP_NAME);
        String tripDates = getIntent().getStringExtra(EXTRA_TRIP_DATES);
        int imageRes = getIntent().getIntExtra(EXTRA_TRIP_IMAGE, R.drawable.travel_beach_bg);

        ImageView heroImage = findViewById(R.id.img_detail_hero);
        TextView titleText = findViewById(R.id.txt_detail_title);
        TextView datesText = findViewById(R.id.txt_detail_dates);

        heroImage.setImageResource(imageRes);
        titleText.setText(tripName == null ? "Maldives Paradise" : tripName);
        datesText.setText(tripDates == null ? "Jun 15 - Jun 22, 2024" : tripDates);

        ImageButton backButton = findViewById(R.id.btn_detail_back);
        backButton.setOnClickListener(v -> finish());

        findViewById(R.id.btn_add_step).setOnClickListener(v ->
                startActivity(new Intent(this, AddStepActivity.class)));
        findViewById(R.id.btn_share_trip).setOnClickListener(v ->
                Toast.makeText(this, "Share trip clicked", Toast.LENGTH_SHORT).show());
        findViewById(R.id.btn_alert_trip).setOnClickListener(v ->
                Toast.makeText(this, "Alert clicked", Toast.LENGTH_SHORT).show());
        findViewById(R.id.card_call_hotel).setOnClickListener(v ->
                Toast.makeText(this, "Calling hotel...", Toast.LENGTH_SHORT).show());
        findViewById(R.id.card_share_sms).setOnClickListener(v ->
                Toast.makeText(this, "Sharing via SMS...", Toast.LENGTH_SHORT).show());
    }
}
