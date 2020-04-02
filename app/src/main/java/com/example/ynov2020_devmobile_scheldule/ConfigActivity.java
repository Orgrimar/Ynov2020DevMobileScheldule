package com.example.ynov2020_devmobile_scheldule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigActivity extends AppCompatActivity {

    private static final String TAG = "Config Activity : ";

    private EditText mNameUser;
    private EditText mEmailUser;

    private CheckBox mUserRole;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        mNameUser = findViewById(R.id.userText);
        mEmailUser = findViewById(R.id.mailText);
        mUserRole = findViewById(R.id.isParentCheckBox);
    }

    public void onStart() {
        super.onStart();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = null;
        String email = null;

        if (user != null) {
            Log.d(TAG, user.getUid());

            for (UserInfo profile : user.getProviderData()) {
                name = profile.getDisplayName();
                email = profile.getEmail();
            }

            mNameUser.setText(name);
            mEmailUser.setText(email);

            db.collection("role").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.get(user.getUid()));
                            mUserRole.setChecked((Boolean) document.get(user.getUid()));
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
        } else {
            Log.w(TAG, "Pas d'utilisateur connecté.");
        }
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

    public void updateRole(View view) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DocumentReference docRefRole = db.collection("role").document("ZNRy4uTqZZirxwQsozmA");
        boolean statusCheckBox = mUserRole.isChecked();

        Log.d(TAG, String.valueOf(mUserRole.isChecked()));

        if (statusCheckBox == true) {
            statusCheckBox = false;
        } else {
            statusCheckBox = true;
        }

        docRefRole.update(user.getUid(), statusCheckBox).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully updated!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error updating document", e);
            }
        });

        db.collection("role").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.get(user.getUid()));
                        mUserRole.setChecked((Boolean) document.get(user.getUid()));
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
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
