package com.example.travelin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        updateUserHeader();

        RecyclerView tripsRecyclerView = findViewById(R.id.recycler_trips);
        tripsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tripsRecyclerView.setNestedScrollingEnabled(true);
        tripsRecyclerView.setAdapter(new TripAdapter(createTrips(), trip -> {
            Intent intent = new Intent(this, TripDetailActivity.class);
            intent.putExtra(TripDetailActivity.EXTRA_TRIP_NAME, trip.getName());
            intent.putExtra(TripDetailActivity.EXTRA_TRIP_DATES, trip.getDates());
            intent.putExtra(TripDetailActivity.EXTRA_TRIP_IMAGE, trip.getImageResId());
            startActivity(intent);
        }));

        FloatingActionButton addTripButton = findViewById(R.id.fab_add_trip);
        addTripButton.setOnClickListener(v ->
                Toast.makeText(this, "Add trip clicked", Toast.LENGTH_SHORT).show());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_memories) {
                Toast.makeText(this, "Memories clicked", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_alerts) {
                Toast.makeText(this, "Alerts clicked", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_settings) {
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            }
            return false;
        });
    }

    private void updateUserHeader() {
        TextView greetingText = findViewById(R.id.txt_user_greeting);
        TextView initialsText = findViewById(R.id.txt_user_initials);

        String name = getConnectedUserName();
        greetingText.setText("Hi, " + name);
        initialsText.setText(getInitials(name));
    }

    private String getConnectedUserName() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return "Traveler";
        }

        String displayName = user.getDisplayName();
        if (!TextUtils.isEmpty(displayName)) {
            return displayName.trim();
        }

        String email = user.getEmail();
        if (!TextUtils.isEmpty(email) && email.contains("@")) {
            return email.substring(0, email.indexOf("@")).trim();
        }

        return "Traveler";
    }

    private String getInitials(String name) {
        if (TextUtils.isEmpty(name)) {
            return "T";
        }

        String[] parts = name.trim().split("\\s+");
        StringBuilder initials = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                initials.append(Character.toUpperCase(part.charAt(0)));
            }
            if (initials.length() == 2) {
                break;
            }
        }

        return initials.length() == 0 ? "T" : initials.toString();
    }

    private List<Trip> createTrips() {
        List<Trip> trips = new ArrayList<>();
        trips.add(new Trip("UPCOMING", "Maldives Paradise", "Jun 15 - Jun 22, 2024", "5 locations", R.drawable.travel_beach_bg));
        trips.add(new Trip(null, "Paris & Brussels", "Aug 5 - Aug 18, 2024", "8 locations", R.drawable.travel_balloons_bg));
        trips.add(new Trip(null, "Swiss Alps Adventure", "Sep 10 - Sep 20, 2024", "4 locations", R.drawable.travel_beach_bg));
        trips.add(new Trip("PAST TRIPS", "Venice Romance", "Mar 12 - Mar 19, 2024", "3 locations", R.drawable.travel_balloons_bg));
        return trips;
    }
}
