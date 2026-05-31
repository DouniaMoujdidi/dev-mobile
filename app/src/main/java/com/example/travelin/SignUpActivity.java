package com.example.travelin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class SignUpActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button signUpButton = findViewById(R.id.btn_sign_up);
        Button signInButton = findViewById(R.id.btn_go_sign_in);

        signUpButton.setOnClickListener(v ->
                Toast.makeText(this, "Sign up clicked", Toast.LENGTH_SHORT).show());
        signInButton.setOnClickListener(v ->
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class)));

        setSocialToast(R.id.btn_google, "Google clicked");
        setSocialToast(R.id.btn_facebook, "Facebook clicked");
    }

    private void setSocialToast(int viewId, String message) {
        findViewById(viewId).setOnClickListener(v ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }
}
