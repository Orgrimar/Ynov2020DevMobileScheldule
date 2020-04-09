package com.example.ynov2020_devmobile_scheldule.API;

import com.example.ynov2020_devmobile_scheldule.Models.UserTask;
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
import java.util.Objects;

public class UserTaskHelper {
    private static final String COLLECTION_NAME = "UserTask";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getTasksCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static ArrayList getAllToArrayList(){
        ArrayList<UserTask> data = new ArrayList<>();
        for (QueryDocumentSnapshot UserTask : Objects.requireNonNull(UserTaskHelper.getTasksCollection().get().getResult())) {
            data.add(UserTask.toObject(UserTask.class));
        }
        return data;
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
