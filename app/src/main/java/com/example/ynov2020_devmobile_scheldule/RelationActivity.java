package com.example.ynov2020_devmobile_scheldule;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdaptater;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<String> listChildren = new ArrayList<String>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);

        recyclerView = (RecyclerView) findViewById(R.id.listRelation);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
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
            String child = relationValue.get(i);
            Log.d(TAG, "Enfant : " + child);
            listChildren.add(i, child);
            Log.d(TAG, listChildren.get(i));
            i++;
        }

        mAdaptater = new AdaptaterChildren(listChildren);
        recyclerView.setAdapter(mAdaptater);
    }
}
