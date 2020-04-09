package com.example.ynov2020_devmobile_scheldule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ynov2020_devmobile_scheldule.Models.UserTask;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;

import io.opencensus.tags.TagMetadata;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        public void onItemClicked(int position);
    }

    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(int position);
    }

    private ArrayList<UserTask> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
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
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tvTitle.setText(mDataset.get(position).getTitre());
        //Remplacer par l'ID de la tâche
        //holder.tvTitle.setTag(mDataset.get(position).getTitre());
        holder.tvDuree.setText(mDataset.get(position).getDuree().toString());
        holder.tvDesc.setText(mDataset.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvDuree;
        public TextView tvDesc;
        public MyViewHolder(@NonNull View view) {
            super(view);
            tvTitle = view.findViewById(R.id.Title);
            tvDuree = view.findViewById(R.id.Duree);
            tvDesc = view.findViewById(R.id.Desc);
        }
    }
}