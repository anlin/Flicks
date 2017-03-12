package com.thunder.flicks;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by anlinsquall on 12/3/17.
 */

public class QuickPlayActivity extends YouTubeBaseActivity {
    static String url = "https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    YouTubePlayer mYouTubePlayer;
    String key = "";

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_quick_play);
        final String id = getIntent().getStringExtra("id");

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize("Your API KEY",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        mYouTubePlayer = youTubePlayer;
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(String.format(url, id))
                                .build();
                        Log.d("DEBUG", String.format(url,id));
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
                                    key = moviesJsonResult.getJSONObject(0).getString("key");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                mYouTubePlayer.cueVideo(key);
                            }
                        });

                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                        Toast.makeText(QuickPlayActivity.this, youTubeInitializationResult.toString(), Toast.LENGTH_LONG);
                    }
                });
    }
}
