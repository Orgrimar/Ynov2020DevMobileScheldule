package com.example.ynov2020_devmobile_scheldule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "Test Activity : ";

    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private boolean isParent = false;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mStatusTextView = findViewById(R.id.status);
        mDetailTextView = findViewById(R.id.detail);
    }

    public void OnStart() {
        super.onStart();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("role").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, "Parent => " + document.get(user.getUid()));
                        if ((Boolean) document.get(user.getUid()) == true) {
                            isParent = true;
                        }

                        if (isParent == true) {
                            Log.d(TAG, "Parent is connected.");
                            findViewById(R.id.relationButton).setVisibility(View.VISIBLE);
                        } else {
                            Log.d(TAG, "Children is connected.");
                            findViewById(R.id.relationButton).setVisibility(View.GONE);
                        }
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
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

    public void OnRelation(View view) {
        Intent intent = new Intent(TestActivity.this, RelationActivity.class);
        startActivity(intent);
    }
}
