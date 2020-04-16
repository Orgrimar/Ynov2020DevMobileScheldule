package com.example.ynov2020_devmobile_scheldule;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RelationActivity extends AppCompatActivity {

    final static String TAG = "RelationActivity";

    private RecyclerView mListRelation;
    ArrayAdapter<String> listChildren;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);

        mListRelation = (RecyclerView) findViewById(R.id.listRelation);
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
                        LoadListChildren(document);
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

    public void LoadListChildren(DocumentSnapshot src) {
        int i = 0;

        ArrayList<String> relationValue = (ArrayList<String>) src.get(user.getUid());
        while (i<relationValue.size()) {
            Log.d(TAG, relationValue.get(i));
            listChildren.add(relationValue.get(i));
            i++;
        }
        Log.d(TAG, listChildren.getItem(1));
        //mListRelation.setAdapter(listChildren);
    }
}
