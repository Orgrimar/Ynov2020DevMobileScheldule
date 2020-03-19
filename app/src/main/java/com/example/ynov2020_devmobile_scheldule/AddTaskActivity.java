package com.example.ynov2020_devmobile_scheldule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
    }

    protected void sendData(View view){
        EditText etTitle = findViewById(R.id.TitleNew);
        EditText etDuree = findViewById(R.id.Duree);
        EditText etDesc = findViewById(R.id.Desc);
        EditText etRappels = findViewById(R.id.rappels);
        CalendarView cv = findViewById(R.id.calendarView2);
        //Task newTask = new Task(etTitle.getText().toString(), etDuree.getText().toString(), etDesc.getText().toString(), );
        Task newTask = new Task("Tâche ajoutée");
        Intent intent = new Intent(AddTaskActivity.this, TaskListActivity.class);
      //  intent.putExtra("Task", (Parcelable) newTask);
        startActivity(intent);
    }
}
