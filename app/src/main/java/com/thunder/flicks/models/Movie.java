package com.thunder.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by anlinsquall on 7/3/17.
 */

public class Movie {
    String posterPath;
    String overview;
    String title;
    String backdropPath;

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w1000/%s",backdropPath);
    }

    public String getOverview() {
        return overview;
    }

    public String getTitle() {
        return title;
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.overview = jsonObject.getString("overview");
        this.title = jsonObject.getString("title");
        this.backdropPath = jsonObject.getString("backdrop_path");
    }

    public static ArrayList<Movie> fromJSONArray (JSONArray array){
        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int i = 0; i < array.length(); i++){
            try {
                results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
