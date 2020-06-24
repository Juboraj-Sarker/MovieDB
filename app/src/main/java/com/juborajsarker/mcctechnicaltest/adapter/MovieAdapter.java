package com.juborajsarker.mcctechnicaltest.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.juborajsarker.mcctechnicaltest.R;
import com.juborajsarker.mcctechnicaltest.activity.DetailsActivity;
import com.juborajsarker.mcctechnicaltest.api.Api;
import com.juborajsarker.mcctechnicaltest.model.Thumbnail;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder >{

    private Context context;
    private List<Thumbnail> thumbnailList;
    private RecyclerView recyclerView;

    public MovieAdapter(Context context, List<Thumbnail> thumbnailList, RecyclerView recyclerView) {
        this.context = context;
        this.thumbnailList = thumbnailList;
        this.recyclerView = recyclerView;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView ivMovie;

        public MyViewHolder(@NonNull View view) {
            super(view);

            ivMovie = (ImageView) view.findViewById(R.id.iv_movie);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_movie, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Thumbnail thumbnail = thumbnailList.get(position);
        final String thumbnailUrl = thumbnail.getThumbnailUrl();
        final String movieId = thumbnail.getMovieId();
        String movieTitle = thumbnail.getMovieTitle();

        String finalUrl = Api.BASE_URL_THUMBNAIL + thumbnailUrl;
        Picasso.with(context)
                .load(finalUrl)
                .centerCrop()
                .resize(200, 300)
                .error(R.drawable.ic_error)
                .placeholder(R.drawable.ic_refresh)
                .into(holder.ivMovie)
        ;

        Log.d("finalUrl", finalUrl);

        holder.ivMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("movieId", movieId);
                intent.putExtra("posterId", thumbnailUrl);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return thumbnailList.size();
    }


}
