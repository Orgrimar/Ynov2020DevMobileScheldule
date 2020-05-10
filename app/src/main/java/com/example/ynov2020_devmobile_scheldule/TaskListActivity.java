package com.example.ynov2020_devmobile_scheldule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.security.keystore.SecureKeyImportUnavailableException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ynov2020_devmobile_scheldule.Models.UserTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class TaskListActivity extends AppCompatActivity {
    private Date date;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    protected static final String COLLECTION_NAME = "UserTask";
    protected static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DocumentReference userData = FirebaseFirestore.getInstance().collection("User").document(user.getUid());
    private ArrayList<UserTask> tabtask;
    private boolean isParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final String Sdate = intent.getExtras().getString("date");
        date = new Date(Sdate);
        userData.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                 @Override
                                                 public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                     if (task.isSuccessful()) {
                                                         DocumentSnapshot document = task.getResult();
                                                         if (document != null) {
                                                             Log.d("DATA", "Données récupérés ! data = " + document.getData());
                                                             setRole(document);
                                                         } else {
                                                             Log.d("DATA", "Aucune donnée présente en base");
                                                         }
                                                     } else {
                                                         Log.d("DATA", "get failed with ", task.getException());
                                                     }
                                                 }
                                             }
        );
        setContentView(R.layout.activity_task_list);
    }

    public void onStart() {
        super.onStart();
        loadDataListItem();
    }

    private void setRole(DocumentSnapshot document){
        isParent = (boolean) document.get("role");
        FloatingActionButton fab = findViewById(R.id.fab);
        ImageButton ib = findViewById(R.id.setting);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskListActivity.this, AddTaskActivity.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
        if(isParent){
            fab.show();
        }else{
            fab.hide();
            ib.setVisibility(View.GONE);
        }
    }

    public void loadDataListItem(){
        //db.collection(COLLECTION_NAME).w
        Timestamp Tdate = new Timestamp(date.getTime());
        Timestamp TendDate = new Timestamp(date.getTime() + 86400000);
        Log.d("DATE", "DATE BEFORE : " + Tdate.toString());
        Log.d("DATE", "DATE AFTER : " + TendDate.toString());
        db.collection(COLLECTION_NAME).whereGreaterThan("date", Tdate).whereLessThan("date", TendDate).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();
                    if (document != null) {
                        Log.d("DATA", "Données récupérés ! ");
                        loadListItem(document);
                    } else {
                        Log.d("DATA", "Aucune donnée présente en base");
                    }
                } else {
                    Log.d("DATA", "get failed with ", task.getException());
                }
            }
        });
    }

    public void loadListItem(QuerySnapshot document){
        recyclerView = (RecyclerView) findViewById(R.id.listTask);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<UserTask> tempData = new ArrayList<>();
        for (QueryDocumentSnapshot UserTask : document) {
            Log.d("DATA", String.valueOf(UserTask.getData()));
            tempData.add(new UserTask((long) UserTask.get("id"), (String) UserTask.get("titre"), new Time((long) UserTask.get("duree")), (String) UserTask.get("desc"), (long) UserTask.get("nbrRappels"), (boolean) UserTask.get("estFinie")));
        }
        mAdapter = new MyAdapter(tempData);

        recyclerView.setAdapter(mAdapter);
    }

    private void getTaskBDD(String pfId){
        db.collection(COLLECTION_NAME).document(toString().valueOf(pfId)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        Log.d("DATA", "Données récupérés ! data = " + document.getData());
                        getUserTask(document);
                    } else {
                        Log.d("DATA", "Aucune donnée présente en base");
                    }
                } else {
                    Log.d("DATA", "get failed with ", task.getException());
                }
            }
        });
    }

    private void getUserTask(DocumentSnapshot document){
        UserTask us = new UserTask((long) document.get("id"), (String) document.get("titre"), new Time((long) document.get("duree")), (String) document.get("desc"), (long) document.get("nbrRappels"), (boolean) document.get("estFinie"));
        setContentView(R.layout.read_task_layout);
        TextView tvTitre = findViewById(R.id.title);
        TextView tvDesc = findViewById(R.id.Desc);
        TextView tvDuree = findViewById(R.id.Duree);
        Button validButton = findViewById(R.id.validButton);
        tvTitre.setText(us.getTitre());
        tvDesc.setText(us.getDesc());
        tvDuree.setText(us.getDuree().toString());
        validButton.setTag(us.getId());
        if(us.getEstFinie())
            validButton.setVisibility(View.GONE);
    }

    public void deleteItem(View view){
        Toast.makeText(this, "DELETE " +view.getTag().toString(), Toast.LENGTH_SHORT).show();
    }

    public void updateItem(View view){
        Toast.makeText(this, "UPDATE " +view.getTag().toString(), Toast.LENGTH_SHORT).show();
    }

    public void readItem(View view){
        Toast.makeText(this, "ID elem = " +view.getTag().toString(), Toast.LENGTH_SHORT).show();
        getTaskBDD(view.getTag().toString());
    }

    public void validTask(View view){
        Toast.makeText(this, "ID elem = " +view.getTag().toString(), Toast.LENGTH_SHORT).show();
        db.collection(COLLECTION_NAME).document(toString().valueOf(view.getTag().toString())).update("estFinie", true);
        setContentView(R.layout.activity_task_list);
        loadDataListItem();
    }

    public void OnConfig(View view) {
        Intent intent = new Intent(TaskListActivity.this, ConfigActivity.class);
        intent.putExtra("date", date);
        startActivity(intent);
    }
}
