package com.example.ynov2020_devmobile_scheldule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class TaskListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final String date = intent.getStringExtra("date");
        setContentView(R.layout.activity_task_list);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskListActivity.this, AddTaskActivity.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
        fab.hide();
        if(true){
            fab.show();
        }
        ListView lv = findViewById(R.id.listTask);
        ArrayAdapter<Task> tab =
                new ArrayAdapter<>(lv.getContext(), R.layout.task_layout, R.id.title);
        for(int i = 0 ; i < 40 ; i ++){
            //Remplacer par l'ajout de tâches qui sont en BDD
            tab.add(new Task("Tâche "+ i));
        }
        lv.setAdapter(tab);
    }
}
