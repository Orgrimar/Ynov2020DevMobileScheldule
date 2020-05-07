package com.example.ynov2020_devmobile_scheldule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AuthentificationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Authentification Activity";

    private TextView mStatusTextView;
    private TextView mDetailTextView;

    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mPseudo;

    private CheckBox mUserRole;

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private boolean isParent = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenfication);

        // Views
        mStatusTextView = findViewById(R.id.status);
        mDetailTextView = findViewById(R.id.detail);
        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);
        mPseudo = findViewById(R.id.fieldPseudo);

        mUserRole = findViewById(R.id.checkParent);

        // Buttons
        findViewById(R.id.emailSignInButton).setOnClickListener(this);
        findViewById(R.id.emailCreateAccountButton).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @SuppressLint("LongLogTag")
    private void createAccount(String email, String password, @NonNull String name, final boolean isParent) {
        final Map<String, Object> userData = new HashMap<>();
        userData.put("pseudo", name);
        userData.put("role", isParent);

        final Map<String, Object> userRelationData = new HashMap<>();
        userRelationData.put("0", 0);

        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                    db.collection("User").document(user.getUid()).set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });

                    if (isParent){
                        db.collection("UserRelation").document(user.getUid()).set(userRelationData).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
                    }

                    updateUI(user);
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(AuthentificationActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    @SuppressLint("LongLogTag")
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(AuthentificationActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

                if (!task.isSuccessful()) {
                    mStatusTextView.setText(R.string.auth_failed);
                }
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void updateUI(final FirebaseUser user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,user.getEmail(), user.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.emailPasswordButtons).setVisibility(View.GONE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.GONE);

            Intent intent = new Intent(AuthentificationActivity.this, TestActivity.class);
            startActivity(intent);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.emailPasswordButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.emailCreateAccountButton) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString(), mPseudo.getText().toString(), mUserRole.isChecked());
        } else if (i == R.id.emailSignInButton) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
    }

    public void onDestroy() {
        mAuth.signOut();
        updateUI(null);
        super.onDestroy();
    }
}
