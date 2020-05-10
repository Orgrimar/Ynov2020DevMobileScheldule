package com.example.ynov2020_devmobile_scheldule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

public class AddChildrenActivity extends AppCompatActivity {

    final static String TAG = "AddChildrenActivity";
private Date date;
    private EditText etChildName;

    private int relationDocSize;

    CollectionReference dbUser = FirebaseFirestore.getInstance().collection("User");
    CollectionReference dbUserRelation = FirebaseFirestore.getInstance().collection("UserRelation");
    FirebaseAuth user = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_children);
        date = (Date) getIntent().getExtras().get("date");

        etChildName = findViewById(R.id.childNameField);
    }

    protected void onStart() {
        super.onStart();

        dbUserRelation.document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        relationDocSize = document.getData().size();
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void addChild(View view) {
        final DocumentReference parentDoc = FirebaseFirestore.getInstance().collection("UserRelation").document(user.getUid());

        dbUser.whereEqualTo("pseudo", etChildName.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Log.d(TAG, doc.getId() + " => " + doc.get("pseudo"));

                        parentDoc.update(String.valueOf(relationDocSize), doc.getId()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot succefully updated!");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });
                    }
                } else {
                    Log.d(TAG, "Error getting documents : ", task.getException());
                }
            }
        });

        Intent intent = new Intent(AddChildrenActivity.this, RelationActivity.class);
        intent.putExtra("date", date.toString());
        startActivity(intent);
    }
}
