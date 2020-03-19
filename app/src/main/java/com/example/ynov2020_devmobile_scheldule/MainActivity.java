//Documention authentification par mail
//https://github.com/firebase/quickstart-android/blob/4b18921c3a4aa0fd3dcaaa053359c9452e430d1a/auth/app/src/main/java/com/google/firebase/quickstart/auth/java/EmailPasswordActivity.java#L132-L159
package com.example.ynov2020_devmobile_scheldule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private TextView mStatusTextView;
    private TextView mDetailTextView;

    private EditText mEmailField;
    private EditText mPasswordField;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void OnClick(View view) {
        final EditText mail = (EditText) findViewById(R.id.mailText);
        final EditText password = (EditText) findViewById(R.id.passwordText);
        final CheckBox isParent = (CheckBox) findViewById(R.id.isParentCheckBox);

        mAuth.signInWithEmailAndPassword(mail.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                final String TAG = "SUCCESS";
                Log.d(TAG, "signInWithEmail : success");
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
            } else {
                final String TAG = "FAILED";
                Log.w(TAG, "signInWithEmail : failure", task.getException());
                Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                updateUI(null);
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        //hideProgressBar();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.emailPasswordButtons).setVisibility(View.GONE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.GONE);
            findViewById(R.id.signedInButtons).setVisibility(View.VISIBLE);

            if (user.isEmailVerified()) {
                findViewById(R.id.verifyEmailButton).setVisibility(View.GONE);
            } else {
                findViewById(R.id.verifyEmailButton).setVisibility(View.VISIBLE);
            }
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.emailPasswordButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.VISIBLE);
            findViewById(R.id.signedInButtons).setVisibility(View.GONE);
        }
    }
}