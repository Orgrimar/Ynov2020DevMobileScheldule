package com.example.ynov2020_devmobile_scheldule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ynov2020_devmobile_scheldule.API.UserTaskHelper;
import com.example.ynov2020_devmobile_scheldule.Models.UserTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<UserTask> tabtask;

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
        recyclerView = (RecyclerView) findViewById(R.id.listTask);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        tabtask = UserTaskHelper.getAllToArrayList();
       /*
        tabtask = new ArrayList<>();
        for (int i = 0 ; i < 40 ; i++){
            tabtask.add(new UserTask("Tâche " + i));
        }
        */
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(tabtask);

        recyclerView.setAdapter(mAdapter);

    }

    public void deleteItem(View view){
        Toast.makeText(this, "DELETE", Toast.LENGTH_SHORT).show();
    }

    public void updateItem(View view){
        Toast.makeText(this, "UPDATE", Toast.LENGTH_SHORT).show();
    }

    public void readItem(View view){

        Toast.makeText(this, view.getTag().toString(), Toast.LENGTH_SHORT).show();
    }
}
