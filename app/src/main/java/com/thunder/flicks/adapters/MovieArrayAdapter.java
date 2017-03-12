package com.thunder.flicks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thunder.flicks.R;
import com.thunder.flicks.models.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.thunder.flicks.R.id.ivMovieImage;
import static com.thunder.flicks.R.id.tvOverview;
import static com.thunder.flicks.R.id.tvTitle;

/**
 * Created by anlinsquall on 7/3/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    static class ViewHolder{
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvOverview)
        TextView tvOverview;
        @BindView(R.id.ivMovieImage)
        ImageView ivMovieImage;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private static class ImageOnlyViewHolder{
        ImageView ivMovieImage;
    }

    public MovieArrayAdapter(Context context, ArrayList<Movie> movies){
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public int getViewTypeCount() {
        return Movie.MovieType.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).movieType.ordinal();
    }

    // TODO: Inflate different ViewHolder based on popularity instead of hiding
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get the movie data
        Movie movie = getItem(position);


        ViewHolder viewHolder;

        // Check if the view can be reused
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder = new ViewHolder(convertView);
            // Cache the view holder
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Clear image view from Convert View
        viewHolder.ivMovieImage.setImageResource(0);

        // populate data
        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());
        int orientation = getContext().getResources().getConfiguration().orientation;
        Picasso.with(getContext()).load(movie.getPosterPath())
                .transform(new RoundedCornersTransformation(10,10))
                .placeholder(R.drawable.port_placeholder).into(viewHolder.ivMovieImage);

        if (getContext().getResources().getConfiguration().orientation==
                Configuration.ORIENTATION_PORTRAIT) {
            // Check if the current position for popular movie
            if (getItemViewType(position) == Movie.MovieType.POPULAR.ordinal()) {
                viewHolder.tvTitle.setVisibility(View.GONE);
                viewHolder.tvOverview.setVisibility(View.GONE);
                Picasso.with(getContext()).load(movie.getBackdropPath())
                        .transform(new RoundedCornersTransformation(10,10))
                        .placeholder(R.drawable.land_placeholder).into(viewHolder.ivMovieImage);
                viewHolder.ivMovieImage.getLayoutParams().width =
                        ViewGroup.LayoutParams.MATCH_PARENT;
            }
        }else{
            // Check if the current position for popular movie
            if (getItemViewType(position) == Movie.MovieType.POPULAR.ordinal()) {
                Picasso.with(getContext()).load(movie.getBackdropPath())
                        .transform(new RoundedCornersTransformation(10,10))
                        .placeholder(R.drawable.land_placeholder).into(viewHolder.ivMovieImage);
            }
        }

        return convertView;
    }


}
