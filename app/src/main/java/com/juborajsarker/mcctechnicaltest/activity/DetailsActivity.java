package com.juborajsarker.mcctechnicaltest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import android.app.ActionBar;
import android.content.res.Resources;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.juborajsarker.mcctechnicaltest.MainActivity;
import com.juborajsarker.mcctechnicaltest.R;
import com.juborajsarker.mcctechnicaltest.adapter.MovieAdapter;
import com.juborajsarker.mcctechnicaltest.adapter.TrailerAdapter;
import com.juborajsarker.mcctechnicaltest.api.Api;
import com.juborajsarker.mcctechnicaltest.model.details.Details;
import com.juborajsarker.mcctechnicaltest.model.movie.Movies;
import com.juborajsarker.mcctechnicaltest.model.trailer.Result;
import com.juborajsarker.mcctechnicaltest.model.trailer.Trailer;
import com.juborajsarker.mcctechnicaltest.utils.GridSpacingItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {


    TextView tvMovieName, tvYear, tvLength, tvRating, tvDetails;
    ImageView ivMovie;
    RecyclerView rvTrailer;
    List<Result> trailerList = new ArrayList<>();
    TrailerAdapter adapter;
    List<Result> myTrailerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        init();
    }

    private void init() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }

        tvMovieName = (TextView) findViewById(R.id.tv_movie_name);
        tvYear = (TextView) findViewById(R.id.tv_year);
        tvLength = (TextView) findViewById(R.id.tv_length);
        tvRating = (TextView) findViewById(R.id.tv_rating);
        tvDetails = (TextView) findViewById(R.id.tv_details);
        ivMovie = (ImageView) findViewById(R.id.iv_movie);
        rvTrailer = (RecyclerView) findViewById(R.id.rv_trailer);

        String movieId = getIntent().getStringExtra("movieId");
        String posterId = getIntent().getStringExtra("posterId");

        loadDetailsFromApi(movieId);
        loadImageFromAPI(posterId);
        loadTrailerFromApi(movieId);
    }




    private void loadDetailsFromApi(String movieId) {

        String restUrl = movieId + "?api_key=" + Api.API_KEY;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL_DETAILS)
                .addConverterFactory(GsonConverterFactory.create()).build();

        Api api = retrofit.create(Api.class);
        Call<Details> call = api.getDetailsOfMovie(restUrl);
        call.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(Call<Details> call, Response<Details> response) {
                if (response.isSuccessful()){

                    Details details = response.body();
                    if (details != null){

                        String releaseDate = details.getReleaseDate();
                        String yearOnly = releaseDate.substring(0, 4);
                        tvMovieName.setText(details.getTitle());
                        tvYear.setText(yearOnly);
                        tvLength.setText(details.getRuntime() + " min");
                        tvRating.setText(details.getVoteAverage() + " / 10");
                        tvDetails.setText(details.getOverview());
                    }
                }
            }

            @Override
            public void onFailure(Call<Details> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "Something went wrong. Please try again later!", Toast.LENGTH_SHORT).show();
                Log.d("ERROR_FOUND", t.getMessage().toString());
            }
        });
    }


    private void loadImageFromAPI(String posterId) {

        String finalUrl = Api.BASE_URL_THUMBNAIL + posterId;
        Picasso.with(DetailsActivity.this)
                .load(finalUrl)
                .centerCrop()
                .resize(200, 300)
                .error(R.drawable.ic_error)
                .placeholder(R.drawable.ic_refresh)
                .into(ivMovie)
        ;
    }



    private void loadTrailerFromApi(String movieId) {

        String restUrl = movieId + "/videos?api_key=" + Api.API_KEY;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL_TRAILER)
                .addConverterFactory(GsonConverterFactory.create()).build();

        Api api = retrofit.create(Api.class);
        Call<Trailer> call = api.getTrailer(restUrl);
        call.enqueue(new Callback<Trailer>() {
            @Override
            public void onResponse(Call<Trailer> call, Response<Trailer> response) {
                if (response.isSuccessful()){

                   Trailer trailer = response.body();
                   trailerList = trailer.getResults();

                   for (int i=0; i<trailerList.size(); i++){
                       Result singleTrailer = trailerList.get(i);
                       if (singleTrailer.getType().equals("Trailer")){
                           myTrailerList.add(singleTrailer);
                       }
                   }
                   setRecyclerView(myTrailerList);

                }
            }

            @Override
            public void onFailure(Call<Trailer> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "Something went wrong. Please try again later!", Toast.LENGTH_SHORT).show();
                Log.d("ERROR_FOUND", t.getMessage().toString());
            }
        });
    }

    private void setRecyclerView(List<Result> trailerList) {

        adapter = new TrailerAdapter(DetailsActivity.this, trailerList, rvTrailer);
        RecyclerView.LayoutManager layoutManagerBeforeMeal = new GridLayoutManager(DetailsActivity.this, 1);
        rvTrailer.setLayoutManager(layoutManagerBeforeMeal);
        rvTrailer.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(2), true));
        rvTrailer.setItemAnimator(new DefaultItemAnimator());

        rvTrailer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:{

                super.onBackPressed();

            }default:{

                return super.onOptionsItemSelected(item);
            }



        }

    }
}