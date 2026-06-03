package com.example.travelin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddStepActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_step);

        ImageButton backButton = findViewById(R.id.btn_add_step_back);
        backButton.setOnClickListener(v -> finish());

        findViewById(R.id.map_picker_card).setOnClickListener(v ->
                Toast.makeText(this, "Select location on map", Toast.LENGTH_SHORT).show());

        findViewById(R.id.photo_upload_card).setOnClickListener(v ->
                Toast.makeText(this, "Upload photos", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btn_save_step).setOnClickListener(v ->
                Toast.makeText(this, "Step saved", Toast.LENGTH_SHORT).show());
    }
}
