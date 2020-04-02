package com.example.ynov2020_devmobile_scheldule.API;

import com.example.ynov2020_devmobile_scheldule.Models.UserTask;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;

public class UserTaskHelper {
    private static final String COLLECTION_NAME = "usertasks";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getTasksCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createUserTask(String pfTitre, Time pfDuree, String pfDesc, int pfNbrRappels) {
        UserTask userTaskToCreate = new UserTask(pfTitre, pfDuree, pfDesc, pfNbrRappels);
        return UserTaskHelper.getTasksCollection().document().set(userTaskToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getUserTask(String uid){
        return UserTaskHelper.getTasksCollection().document(uid).get();
    }

    // --- UPDATE ---

    public static Task<Void> updateUserTaskname(String Taskname, String uid) {
        return UserTaskHelper.getTasksCollection().document(uid).update("Taskname", Taskname);
    }

    // --- DELETE ---

    public static Task<Void> deleteUserTask(String uid) {
        return UserTaskHelper.getTasksCollection().document(uid).delete();
    }
}
