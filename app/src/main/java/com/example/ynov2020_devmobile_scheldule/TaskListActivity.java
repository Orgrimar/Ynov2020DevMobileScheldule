package com.example.ynov2020_devmobile_scheldule;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> a, View v, int position, long id)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(TaskListActivity.this);
                builder.setTitle("Title");
                builder.setMessage("Message");

                builder.setPositiveButton("Supprimer la tâche", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        //Suppression de l'item
                    }
                });

                builder.setNegativeButton("Modifier la tâche", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        //Annuler
                    }
                });

                builder.show();
                return true;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View vue, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, vue, menuInfo);
        menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "Supprimer cet élément");
        menu.add(Menu.NONE, Menu.FIRST +1, Menu.NONE, "Retour");
    }
}
