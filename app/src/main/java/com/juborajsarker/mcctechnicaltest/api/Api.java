package com.juborajsarker.mcctechnicaltest.api;

import com.juborajsarker.mcctechnicaltest.model.details.Details;
import com.juborajsarker.mcctechnicaltest.model.movie.Movies;
import com.juborajsarker.mcctechnicaltest.model.trailer.Trailer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {

    String API_KEY = "f67eb4618b835130ec605da7bf25c679";

    String BASE_URL_MOVIE = "http://api.themoviedb.org/3/movie/";    // http://api.themoviedb.org/3/movie/popular?api_key=f67eb4618b835130ec605da7bf25c679
    String BASE_URL_THUMBNAIL = "https://image.tmdb.org/t/p/w185/";   // http://image.tmdb.org/t/p/w185/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg
    String BASE_URL_DETAILS = "https://api.themoviedb.org/3/movie/"; // https://api.themoviedb.org/3/movie/419704?api_key=f67eb4618b835130ec605da7bf25c679
    String BASE_URL_TRAILER = "https://api.themoviedb.org/3/movie/"; // https://api.themoviedb.org/3/movie/419704/videos?api_key=f67eb4618b835130ec605da7bf25c679

    @GET
    Call<Movies> getPopularMovies(@Url String restUrl);

    @GET
    Call<Details> getDetailsOfMovie(@Url String restUrl);

    @GET
    Call<Trailer> getTrailer(@Url String restUrl);

}
