package com.example.ynov2020_devmobile_scheldule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptaterChildren extends RecyclerView.Adapter<AdaptaterChildren.MyViewHolder> {

    private ArrayList<String> mUid;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTitle.setText(mUid.get(position));
        holder.layout.setTag(position);
    }

    public int getItemCount() {
        return mUid.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ConstraintLayout layout;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.Title);
            layout = view.findViewById(R.id.layout);
        }
    }
}
