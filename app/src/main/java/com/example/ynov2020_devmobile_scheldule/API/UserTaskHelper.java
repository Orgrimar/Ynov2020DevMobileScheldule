package com.example.ynov2020_devmobile_scheldule.API;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ynov2020_devmobile_scheldule.Models.UserTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class UserTaskHelper {
    private static final String COLLECTION_NAME = "UserTask";
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getTasksCollection(){
        return db.collection(COLLECTION_NAME);
    }

    public static ArrayList getAllToArrayList(){
        final ArrayList<UserTask> dataAL = new ArrayList<>();
        getTasksCollection().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();
                    if (document != null) {
                        Log.d("DATA", "Données récupérés ! ");
                        for (QueryDocumentSnapshot UserTask : document) {
                            Log.d("DATA", String.valueOf(UserTask.getData()));
                            //dataAL.add(new UserTask(Integer.parseInt((String) UserTask.getData().get("id")), (String) UserTask.getData().get("id")));
                        }
                    } else {
                        Log.d("DATA", "Aucune donnée présente en base");
                    }
                } else {
                    Log.d("DATA", "get failed with ", task.getException());
                }
            }
        });

        return dataAL;
    }

    // --- CREATE ---
/*
    public static Task<Void> createUserTask(String pfTitre, Time pfDuree, String pfDesc, int pfNbrRappels) {
        UserTask userTaskToCreate = new UserTask(pfTitre, pfDuree, pfDesc, pfNbrRappels);
        return UserTaskHelper.getTasksCollection().document().set(userTaskToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getUserTask(String id){
        return UserTaskHelper.getTasksCollection().document(id).get();
    }

    public static Task<DocumentSnapshot> getUserTaskbyDay(String id, Date date){
        return UserTaskHelper.getTasksCollection().document().get();
    }

    // --- UPDATE ---

    public static Task<Void> updateUserTaskname(String Taskname, String uid) {
        return UserTaskHelper.getTasksCollection().document(uid).update("Taskname", Taskname);
    }

    // --- DELETE ---

    public static Task<Void> deleteUserTask(String id) {
        return UserTaskHelper.getTasksCollection().document(id).delete();
    }*/
}
