package com.example.ynov2020_devmobile_scheldule;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdaptaterChildren extends RecyclerView.Adapter<AdaptaterChildren.MyViewHolder> {

    private ArrayList<String> mUid;
    CollectionReference dbUser = FirebaseFirestore.getInstance().collection("User");

    public AdaptaterChildren(ArrayList myUid) {
        mUid = myUid;
    }

    public AdaptaterChildren.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.children_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        dbUser.document(mUid.get(position)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        holder.tvTitle.setText((String) document.get("pseudo"));
                        holder.layout.setTag(document.get("pseudo"));
                        holder.delButton.setTag(document.get("pseudo"));
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }

    public int getItemCount() {
        return mUid.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public Button delButton;
        public ConstraintLayout layout;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.Title);
            delButton = view.findViewById(R.id.delButton);
            layout = view.findViewById(R.id.layout);
        }
    }
}
