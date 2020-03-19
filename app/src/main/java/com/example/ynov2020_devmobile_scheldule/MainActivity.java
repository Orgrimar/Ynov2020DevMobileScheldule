package com.example.ynov2020_devmobile_scheldule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = findViewById(R.id.listTask);
        ArrayAdapter<Task> tab =
                new ArrayAdapter<>(lv.getContext(), R.layout.task_layout, R.id.title);
        for(int i = 0 ; i < 40 ; i ++){
            tab.add(new Task());
        }
        lv.setAdapter(tab);
    }
}
