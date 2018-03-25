package elmajdma.movie.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import elmajdma.movie.R;
import elmajdma.movie.data.local.Movie;
import elmajdma.movie.data.model.MovieReviews;

/**
 * Created by majd on 19-Mar-18.
 */

public class MovieReviewRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MovieReviews> movieReviewslist=new ArrayList<>();
    private Context context;





    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View movieReviesView = inflater.inflate(R.layout.movie_review, parent, false);
        RecyclerView.ViewHolder viewHolder=new MovieReviewViewHolder(movieReviesView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieReviewViewHolder moviesViewHolder = (MovieReviewViewHolder) holder;
        configureViewHolderMovieReview(moviesViewHolder, position);
    }

    @Override
    public int getItemCount() {
        // condition ? passed : failed
        return (movieReviewslist!= null) ? movieReviewslist.size(): 0;
    }

    private void configureViewHolderMovieReview(MovieReviewViewHolder movieReviewViewHolder, int position) {
        MovieReviews movieReviews = movieReviewslist.get(position);
        if (movieReviews != null) {
            movieReviewViewHolder.reviewAuthor.setText(movieReviews.getAuthor());
            movieReviewViewHolder.reviewContent.setText(movieReviews.getContent());

        }
    }

     public class MovieReviewViewHolder extends RecyclerView.ViewHolder  {

        public final TextView reviewAuthor;
        public final TextView reviewContent;


        public MovieReviewViewHolder(View itemView) {
            super(itemView);

            reviewAuthor = itemView.findViewById(R.id.tv_review_author);
            reviewContent = itemView.findViewById(R.id.tv_review_content);
        }

    }
}
