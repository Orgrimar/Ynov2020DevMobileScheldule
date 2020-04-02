package com.example.ynov2020_devmobile_scheldule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.ynov2020_devmobile_scheldule.Models.UserTask;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
    }

    protected void sendData(View view) throws ParseException {
        EditText etTitle = findViewById(R.id.TitleNew);
        String title = etTitle.getText().toString();

        EditText etDuree = findViewById(R.id.Duree);
        String duree = etDuree.getText().toString();
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date date = sdf.parse(duree);
        if(date == null){
            throw new ParseException("date is NULL", 1);
        }
        EditText etDesc = findViewById(R.id.Desc);
        String desc = etDesc.getText().toString();

        EditText etRappels = findViewById(R.id.rappels);
        String rappel = etRappels.getText().toString();

        UserTask newUserTask = new UserTask(title, (Time)date, desc, Integer.parseInt(rappel));
        //Ajout BDD
        Intent intent = new Intent(AddTaskActivity.this, TaskListActivity.class);
        startActivity(intent);
    }
}
