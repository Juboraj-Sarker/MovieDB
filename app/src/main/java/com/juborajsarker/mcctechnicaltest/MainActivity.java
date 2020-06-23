package com.juborajsarker.mcctechnicaltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import com.juborajsarker.mcctechnicaltest.adapter.MovieAdapter;
import com.juborajsarker.mcctechnicaltest.api.Api;
import com.juborajsarker.mcctechnicaltest.model.Thumbnail;
import com.juborajsarker.mcctechnicaltest.model.movie.Movies;
import com.juborajsarker.mcctechnicaltest.model.movie.Result;
import com.juborajsarker.mcctechnicaltest.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvMovie;
    List<Thumbnail> thumbnailList = new ArrayList<>();
    List<Result> movieList = new ArrayList<>();
    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        rvMovie = (RecyclerView) findViewById(R.id.rv_movie);

        String restUrl = "popular?api_key=" + Api.API_KEY;
        fetchMovieListFromApi(restUrl);
    }

    private void fetchMovieListFromApi(String restUrl) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL_MOVIE)
                .addConverterFactory(GsonConverterFactory.create()).build();

        Api api = retrofit.create(Api.class);
        Call<Movies> call = api.getPopularMovies(restUrl);
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {

                if (response.isSuccessful()){

                    Movies movies = response.body();
                    if (movies != null){

                         movieList = movies.getResults();
                        for (int i=0; i < movieList.size(); i++){

                            Thumbnail thumbnail = new Thumbnail(movieList.get(i).getPosterPath(), String.valueOf(movieList.get(i).getId()), movieList.get(i).getTitle());
                            thumbnailList.add(thumbnail);

                            if (i+1 == movieList.size()){
                                // Check the full list loaded successfully
                                loadThumbnailFromApi(thumbnailList);
                            }

                        }

                    }


                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Something went wrong. Please try again later!", Toast.LENGTH_SHORT).show();
                Log.d("ERROR_FOUND", t.getMessage().toString());
            }
        });
    }

    private void loadThumbnailFromApi(List<Thumbnail> thumbnailList) {

        adapter = new MovieAdapter(MainActivity.this, thumbnailList, rvMovie);
        RecyclerView.LayoutManager layoutManagerBeforeMeal = new GridLayoutManager(MainActivity.this, 2);
        rvMovie.setLayoutManager(layoutManagerBeforeMeal);
        rvMovie.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(2), true));
        rvMovie.setItemAnimator(new DefaultItemAnimator());

        rvMovie.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}