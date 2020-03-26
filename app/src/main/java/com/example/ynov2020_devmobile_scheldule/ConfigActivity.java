package com.example.ynov2020_devmobile_scheldule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.List;

public class ConfigActivity extends AppCompatActivity {

    private static final String TAG = "Config Activity : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
    }

    public void checkCurrentUser(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(TAG, user.getUid());
        } else {
            Log.w(TAG, "Pas d'utilisateur connecté.");
        }
    }

    public void updateProfile(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final EditText name = (EditText) findViewById(R.id.userText) ;

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name.getText().toString()).build();

        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "User profile updated.");
                }
            }
        });
    }

    public void updateEmail(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final EditText mail = (EditText) findViewById(R.id.mailText);

        user.updateEmail(mail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "User email address updated.");
                }
            }
        });
    }

    public void updatePassword(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = null;
        final EditText password = (EditText) findViewById(R.id.passwordText);
        final EditText confirm = (EditText) findViewById(R.id.passwordConfirm);

        if (password == confirm){
            newPassword = password.getText().toString();
        }

        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "User password updated.");
                }
            }
        });
    }

    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ConfigActivity.this, AuthentificationActivity.class);
        startActivity(intent);
    }
}
