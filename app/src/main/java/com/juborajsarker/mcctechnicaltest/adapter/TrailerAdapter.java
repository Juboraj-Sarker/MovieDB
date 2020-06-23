package com.juborajsarker.mcctechnicaltest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.juborajsarker.mcctechnicaltest.R;
import com.juborajsarker.mcctechnicaltest.model.Thumbnail;
import com.juborajsarker.mcctechnicaltest.model.trailer.Result;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder>{
    private Context context;
    private List<Result> trailerList;
    private RecyclerView recyclerView;

    public TrailerAdapter(Context context, List<Result> trailerList, RecyclerView recyclerView) {
        this.context = context;
        this.trailerList = trailerList;
        this.recyclerView = recyclerView;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvTrailer;
        public MyViewHolder(@NonNull View view) {
            super(view);

            tvTrailer = view.findViewById(R.id.tv_trailer);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_trailer, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Result trailer = trailerList.get(position);
        String name = trailer.getName();
        String type = trailer.getType();
        String key = trailer.getKey();
        String site = trailer.getSite();

        holder.tvTrailer.setText(name);
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }
}
