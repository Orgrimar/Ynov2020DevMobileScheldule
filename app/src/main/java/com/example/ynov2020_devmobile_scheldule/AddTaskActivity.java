package com.example.ynov2020_devmobile_scheldule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ynov2020_devmobile_scheldule.Models.UserTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddTaskActivity extends AppCompatActivity {
    protected static final String COLLECTION_NAME = "UserTask";
    protected static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int lastId;
    private Date date;
    private String child;

    String idChild;
    EditText nameChildren;

    CollectionReference dbUser = FirebaseFirestore.getInstance().collection("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        date = (Date) intent.getExtras().get("date");
        setContentView(R.layout.activity_add_task);
        child = (String) intent.getExtras().get("enfant");
    }

    public void onStart() {
        super.onStart();

        //nameChildren = findViewById(R.id.childField);
        //nameChildren.setText(child);
    }

    public void sendData() {
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

        EditText etHeure = findViewById(R.id.hour);
        String sHeure = etHeure.getText().toString();
        int heure = Integer.valueOf(sHeure);
        EditText etMin = findViewById(R.id.minute);
        int minute = Integer.valueOf(etMin.getText().toString());

        CalendarView vCalendar = findViewById(R.id.calendarView2);
        Date dateSelected = new Date(vCalendar.getDate());
        dateSelected.setHours(heure);
        dateSelected.setMinutes(minute);

        CheckBox hebdo = findViewById(R.id.checkHebdo);

        Map<String, Object> userTaskData = new HashMap<>();
        userTaskData.put("id", lastId + 1 );
        userTaskData.put("titre", title);
        userTaskData.put("duree", LDuree);
        userTaskData.put("desc", desc );
        userTaskData.put("nbrRappels", Integer.parseInt(rappel));
        userTaskData.put("date", dateSelected);
        userTaskData.put("recurrence", hebdo.isChecked());
        userTaskData.put("estFinie", false );
/*
        dbUser.whereEqualTo("pseudo", child).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Log.d(String.valueOf(this), doc.getId() + " => " + doc.get("pseudo"));

                        idChild = doc.getId();
                    }
                } else {
                    Log.d(String.valueOf(this), "Error getting documents : ", task.getException());
                }
            }
        });

        userTaskData.put("idUserChild", idChild);
*/
        //Ajout BDD
        Log.d("DATA", "Données prêtes ! ");
        db.collection(COLLECTION_NAME).document(String.valueOf(lastId + 1)).set(userTaskData).addOnSuccessListener(new OnSuccessListener < Void > () {
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
        intent.putExtra("date", date.toString());
        startActivity(intent);;

    }

    public void getLastId(View view){
        TaskListActivity.db.collection(TaskListActivity.COLLECTION_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();
                    if (document != null) {
                        Log.d("DATA", "Données récupérées ! ");
                        lastId = document.size();
                        sendData();
                    } else {
                        Log.d("DATA", "Aucune donnée présente en base");
                    }
                } else {
                    Log.d("DATA", "get failed with ", task.getException());
                }
            }
        });
    }
/*
    public void OnRelation(View view) {
        Intent intent = new Intent(AddTaskActivity.this, RelationActivity.class);
        intent.putExtra("date", date.toString());
        startActivity(intent);
    }

 */
}
