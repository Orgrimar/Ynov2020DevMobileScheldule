package com.example.ynov2020_devmobile_scheldule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class RelationActivity extends AppCompatActivity {

    final static String TAG = "RelationActivity";

    private ListView mListRelation;
    ArrayAdapter<String> listChildren;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);

        mListRelation = (ListView) findViewById(R.id.listRelation);
        listChildren = new ArrayAdapter<String>(mListRelation.getContext(), R.layout.activity_relation, R.id.listRelation);
    }

    public void onStart() {
        super.onStart();

        db.collection("relation").document("SNmS3WIso62qAVcqAh5S").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data : " + document.get(user.getUid()));
                        ArrayList<String> relationValue = (ArrayList<String>) document.get(user.getUid());
                        for (int i = 0; i == relationValue.size(); i++) {
                            Log.d(TAG, relationValue.get(i));
                            listChildren.add(relationValue.get(i));
                        }
                        mListRelation.setAdapter(listChildren);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void OnAddChildren(View view) {
        Log.d(TAG, user.getUid());
    }

    public void OnDeleteChildren(View view) {
        Log.d(TAG, user.getUid());
    }
}
