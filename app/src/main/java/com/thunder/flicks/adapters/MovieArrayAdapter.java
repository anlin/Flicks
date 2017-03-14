package com.thunder.flicks.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.thunder.flicks.QuickPlayActivity;
import com.thunder.flicks.R;
import com.thunder.flicks.models.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

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
        @BindView(R.id.ivPlay)
        ImageView ivPlay;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        //Get the movie data
        final Movie movie = getItem(position);


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
                viewHolder.ivPlay.setVisibility(ImageView.VISIBLE);
                applyItemBackgroundColour(movie.getBackdropPath(), convertView);
                viewHolder.ivMovieImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), QuickPlayActivity.class);
                        intent.putExtra("id", movie.getId());
                        getContext().startActivity(intent);
                    }
                });
            }
        }else{
            // Check if the current position for popular movie
            if (getItemViewType(position) == Movie.MovieType.POPULAR.ordinal()) {
                Picasso.with(getContext()).load(movie.getBackdropPath())
                        .transform(new RoundedCornersTransformation(10,10))
                        .placeholder(R.drawable.land_placeholder).into(viewHolder.ivMovieImage);
                viewHolder.ivMovieImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), QuickPlayActivity.class);
                        intent.putExtra("id", movie.getId());
                        getContext().startActivity(intent);
                    }
                });
            }
        }

        return convertView;
    }

    // To apply list item background colour based on swatches from backdrop image
    private void applyItemBackgroundColour(String imageUrl, final View view){
        Picasso.with(getContext())
                .load(imageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Palette.from(bitmap)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
                                        Palette.Swatch textSwatch = palette.getLightVibrantSwatch();
                                        if(textSwatch!=null)
                                            view.setBackgroundColor(textSwatch.getRgb());
                                    }
                                });
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }


}
