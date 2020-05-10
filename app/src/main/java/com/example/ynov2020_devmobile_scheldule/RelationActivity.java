package com.example.ynov2020_devmobile_scheldule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Map;

public class RelationActivity extends AppCompatActivity {

    final static String TAG = "RelationActivity";

    private TextView mMailNewChild;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdaptater;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<String> listChildren = new ArrayList<String>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    Map<String, Object> relationValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);

        recyclerView = (RecyclerView) findViewById(R.id.listRelation);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mMailNewChild = findViewById(R.id.mailNewChild);
    }

    public void onStart() {
        super.onStart();

        db.collection("UserRelation").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        int i = 1;

                        Log.d(TAG, "User id : " + user.getUid());
                        Log.d(TAG, "DocumentSnapshot data : " + document.getData());

                        while (i<document.getData().size()) {
                            Log.d(TAG, "Data : " + document.get(String.valueOf(i)));
                            listChildren.add((String) document.get(String.valueOf(i)));
                            i++;
                        }

                        mAdaptater = new AdaptaterChildren(listChildren);
                        recyclerView.setAdapter(mAdaptater);
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
        Intent intent = new Intent(RelationActivity.this, AddChildrenActivity.class);
        startActivity(intent);
    }

    public void OnSelectChildren(View view) {
        String child = view.getTag().toString();
        Intent intent = new Intent(RelationActivity.this, AddTaskActivity.class);
        intent.putExtra("enfant", child);
        startActivity(intent);
    }
}
