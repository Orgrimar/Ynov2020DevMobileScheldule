package com.example.ynov2020_devmobile_scheldule;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ynov2020_devmobile_scheldule.Models.UserTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

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
// use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.listTask);

       // recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<UserTask> tabtask = new ArrayList<>();
        for (int i = 0 ; i < 40 ; i++){
            tabtask.add(new UserTask("Tâche " + i));
        }
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(tabtask);
        recyclerView.OnItemTouchListener(new MyAdapter.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClicked(int position) {
                return false;
            }
        });
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View vue, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, vue, menuInfo);
        menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "Supprimer cet élément");
        menu.add(Menu.NONE, Menu.FIRST +1, Menu.NONE, "Retour");
    }
}
