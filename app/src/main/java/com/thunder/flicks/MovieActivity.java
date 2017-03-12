package com.thunder.flicks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thunder.flicks.adapters.MovieArrayAdapter;
import com.thunder.flicks.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieActivity extends AppCompatActivity {
    String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    ArrayList<Movie> movies;
    MovieArrayAdapter movieArrayAdapter;

    @BindView(R.id.lvMovies)
    ListView lvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        movies = new ArrayList<Movie>();
        movieArrayAdapter = new MovieArrayAdapter(this, movies);
        lvMovies.setAdapter(movieArrayAdapter);

        //AsyncHttpClient client = new AsyncHttpClient();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONArray moviesJsonResult = null;
                try {
                    JSONObject responseJson = new JSONObject(response.body().string());
                    moviesJsonResult = responseJson.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(moviesJsonResult));
                    Log.d("DEBUG", moviesJsonResult.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Adapter on UI Tread to update the data
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        movieArrayAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        setupListViewListner();
    }

    // setup listview listner
    private void setupListViewListner (){
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movies.get(position);
                Intent intent = new Intent(MovieActivity.this, MovieDetailActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }
        });
    }
}
