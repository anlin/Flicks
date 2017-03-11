package com.thunder.flicks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thunder.flicks.adapters.MovieArrayAdapter;
import com.thunder.flicks.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {
    String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    ArrayList<Movie> movies;
    MovieArrayAdapter movieArrayAdapter;
    ListView lvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        movies = new ArrayList<Movie>();
        movieArrayAdapter = new MovieArrayAdapter(this, movies);
        lvMovies.setAdapter(movieArrayAdapter);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray moviesJsonResult = null;
                try {
                    moviesJsonResult = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(moviesJsonResult));
                    movieArrayAdapter.notifyDataSetChanged();
                    Log.d("DEBUG", moviesJsonResult.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
