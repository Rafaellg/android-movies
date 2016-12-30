package com.rafaelguimas.movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rafaelguimas.movies.R;
import com.rafaelguimas.movies.activity.MovieDetailActivity;
import com.rafaelguimas.movies.activity.MovieListActivity;
import com.rafaelguimas.movies.fragment.MovieDetailFragment;
import com.rafaelguimas.movies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rafael on 29/12/2016.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private final List<Movie> movieList;
    private final boolean isTwoPanel;
    private final Context context;

    public MovieListAdapter(Context context, List<Movie> movieList, boolean isTwoPanel) {
        this.context = context;
        this.movieList = movieList;
        this.isTwoPanel = isTwoPanel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvTitle.setText(movieList.get(position).getTitle());
        holder.tvYear.setText(movieList.get(position).getYear());

        Picasso.with(context)
                .load(movieList.get(position).getPoster())
                .placeholder(R.drawable.img_movie_placeholder)
                .into(holder.imgPoster);

        final int finalPosition = position;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTwoPanel) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(MovieDetailFragment.ARG_MOVIE, movieList.get(finalPosition));
                    MovieDetailFragment fragment = new MovieDetailFragment();
                    fragment.setArguments(arguments);
                    ((MovieListActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.movie_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    intent.putExtra(MovieDetailFragment.ARG_MOVIE, movieList.get(finalPosition));

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    /**
     * ViewHolder responsavel por mapear os elementos visuais do item
     * */
    class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        ImageView imgPoster;
        TextView tvTitle;
        TextView tvYear;

        ViewHolder(View view) {
            super(view);
            mView = view;
            imgPoster = (ImageView) view.findViewById(R.id.img_thumbnail);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvYear = (TextView) view.findViewById(R.id.tv_year);
        }
    }
}