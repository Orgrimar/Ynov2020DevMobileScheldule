package com.example.ynov2020_devmobile_scheldule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ynov2020_devmobile_scheldule.Models.UserTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddTaskActivity extends AppCompatActivity {
    protected static final String COLLECTION_NAME = "UserTask";
    protected static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int lastId;
    private String date;
    private Date trueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        trueDate = new Date(date);
        setContentView(R.layout.activity_add_task);
    }

    public void sendData(View view) {
        getLastId();
        EditText etTitle = findViewById(R.id.TitleNew);
        String title = etTitle.getText().toString();

        EditText etDuree = findViewById(R.id.Duree);
        String Sduree = etDuree.getText().toString();
        Long LDuree = Long.valueOf((String) Sduree);
        Time duree = new Time(LDuree);
        EditText etDesc = findViewById(R.id.Desc);
        String desc = etDesc.getText().toString();

        EditText etRappels = findViewById(R.id.rappels);
        String rappel = etRappels.getText().toString();



        UserTask newUserTask = new UserTask(lastId +1 , title, duree, desc, Integer.parseInt(rappel));
        Map<String, Object> userTaskData = new HashMap<>();
        userTaskData.put("id", lastId + 1 );
        userTaskData.put("titre", title);
        userTaskData.put("duree", new Timestamp(duree));
        userTaskData.put("desc", desc );
        userTaskData.put("nbrRappels", Integer.parseInt(rappel));
        //userTaskData.put("recurrence", new Date[]{trueDate});
        userTaskData.put("estFinie", false );

        //Ajout BDD
        Log.d("DATA", "Données prêtes ! ");
        db.collection(COLLECTION_NAME).document(String.valueOf(newUserTask.getId() + 2)).set(userTaskData).addOnSuccessListener(new OnSuccessListener < Void > () {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddTaskActivity.this, "Task Registered",
                        Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddTaskActivity.this, "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
        Log.d("DATA", "Nouvelle tâche ajoutée ! ");
        Intent intent = new Intent(AddTaskActivity.this, TaskListActivity.class);
        startActivity(intent);;

    }

    public void getLastId(){
        TaskListActivity.db.collection(TaskListActivity.COLLECTION_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();
                    if (document != null) {
                        Log.d("DATA", "Données récupérées ! ");
                        lastId = document.size();
                    } else {
                        Log.d("DATA", "Aucune donnée présente en base");
                    }
                } else {
                    Log.d("DATA", "get failed with ", task.getException());
                }
            }
        });
    }
}
