package com.example.travelin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button signInButton = findViewById(R.id.btn_sign_in);
        Button signUpButton = findViewById(R.id.btn_go_sign_up);
        TextView forgotPassword = findViewById(R.id.txt_forgot_password);
        TextView title = findViewById(R.id.txt_sign_in_title);

        SpannableString titleText = new SpannableString("Let’s Travel you in.");
        titleText.setSpan(new ForegroundColorSpan(Color.parseColor("#007A8C")), 6, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setText(titleText);

        signInButton.setOnClickListener(v ->
                Toast.makeText(this, "Sign in clicked", Toast.LENGTH_SHORT).show());
        signUpButton.setOnClickListener(v ->
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class)));
        forgotPassword.setOnClickListener(v ->
                Toast.makeText(this, "Forgot password clicked", Toast.LENGTH_SHORT).show());

        setSocialToast(R.id.btn_google, "Google clicked");
        setSocialToast(R.id.btn_facebook, "Facebook clicked");
    }

    private void setSocialToast(int viewId, String message) {
        findViewById(viewId).setOnClickListener(v ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }
}
