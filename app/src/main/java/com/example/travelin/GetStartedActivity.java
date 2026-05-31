package com.example.travelin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class GetStartedActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        Button startButton = findViewById(R.id.btn_start_journey);
        startButton.setOnClickListener(v ->
                startActivity(new Intent(GetStartedActivity.this, SignInActivity.class)));
    }
}
