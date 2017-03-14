package com.thunder.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by anlinsquall on 7/3/17.
 */

public class Movie implements Serializable {
    String posterPath;
    String overview;
    String title;
    String backdropPath;
    Double voteAverage;
    String releaseDate;
    String id;
    String videoKey;
    public MovieType movieType;

    public enum MovieType{
        NON_POPULAR, POPULAR
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w1000/%s",backdropPath);
    }

    public String getVideoKey() {
        return String.format("https://image.tmdb.org/t/p/w1000/%s",backdropPath);
    }

    public String getOverview() {
        return overview;
    }

    public String getTitle() {
        return title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getId() {
        return id;
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.overview = jsonObject.getString("overview");
        this.title = jsonObject.getString("title");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.voteAverage = jsonObject.getDouble("vote_average");
        this.movieType = this.voteAverage < 5.0 ? MovieType.NON_POPULAR : MovieType.POPULAR;
        this.releaseDate = jsonObject.getString("release_date");
        this.id = jsonObject.getString("id");
    }

    public Movie(String posterPath, String overview, String title, String backdropPath,
                 Double voteAverage, String releaseDate, String id) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.title = title;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.id = id;
        this.movieType = this.voteAverage < 5.0 ? MovieType.NON_POPULAR : MovieType.POPULAR;
    }

    public static ArrayList<Movie> fromJSONArray (JSONArray array){
        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int i = 0; i < array.length(); i++){
            try {
                JSONObject jsonObject = array.getJSONObject(i);
                String posterPath = jsonObject.getString("poster_path");
                String overview = jsonObject.getString("overview");
                String title = jsonObject.getString("title");
                String backdropPath = jsonObject.getString("backdrop_path");
                Double voteAverage = jsonObject.getDouble("vote_average");
                String releaseDate = jsonObject.getString("release_date");
                String id = jsonObject.getString("id");
                Movie movie = new Movie(posterPath, overview, title, backdropPath, voteAverage,
                        releaseDate, id);
                results.add(movie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
