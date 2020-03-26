package com.example.ynov2020_devmobile_scheldule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "Test Activity : ";

    private TextView mStatusTextView;
    private TextView mDetailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mStatusTextView = findViewById(R.id.status);
        mDetailTextView = findViewById(R.id.detail);
    }

    public void OnClick(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(TAG, user.getUid());
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,user.getEmail(), user.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
        } else {
            Log.w(TAG, "Pas d'utilisateur connecté.");
        }
    }

    public void OnConfig(View view) {
        Intent intent = new Intent(TestActivity.this, ConfigActivity.class);
        startActivity(intent);
    }
}
