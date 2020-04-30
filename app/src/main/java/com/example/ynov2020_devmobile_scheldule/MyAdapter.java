package com.example.ynov2020_devmobile_scheldule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ynov2020_devmobile_scheldule.Models.UserTask;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;

import io.opencensus.tags.TagMetadata;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<UserTask> mDataset;

    public MyAdapter(ArrayList myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvTitle.setText(mDataset.get(position).getTitre());
        holder.layout.setTag(mDataset.get(position).getId());
        holder.tvDuree.setText(mDataset.get(position).getDuree().toString());
        holder.buttonUpdate.setTag(mDataset.get(position).getId());
        holder.tvDesc.setText(mDataset.get(position).getDesc());
        holder.buttonDelete.setTag(mDataset.get(position).getId());
        if(mDataset.get(position).getEstFinie())
            holder.layout.setBackgroundResource(R.color.taskValidated);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layout;
        public TextView tvTitle;
        public TextView tvDuree;
        public TextView tvDesc;
        public ImageButton buttonUpdate;
        public ImageButton buttonDelete;
        public MyViewHolder(@NonNull View view) {
            super(view);
            layout = view.findViewById(R.id.layout);
            tvTitle = view.findViewById(R.id.Title);
            tvDuree = view.findViewById(R.id.Duree);
            tvDesc = view.findViewById(R.id.Desc);
            buttonUpdate = view.findViewById(R.id.deleteButton);
            buttonDelete = view.findViewById(R.id.updateButton);
        }
    }
}