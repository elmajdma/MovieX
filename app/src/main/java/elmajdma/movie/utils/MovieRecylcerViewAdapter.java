package elmajdma.movie.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import elmajdma.movie.R;
import elmajdma.movie.data.local.Movie;
import elmajdma.movie.ui.MainActivity;
import elmajdma.movie.ui.MovieDetailsActivity;

import static elmajdma.movie.ui.MainActivity.MOVIE_ID;

/**
 * Created by majd on 03-Mar-18.
 */

public class MovieRecylcerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static String POSTER_PATH="http://image.tmdb.org/t/p/w185/";
    public final static int MOVIE_POPULAR_VIEW= 1;
    public final static int MOVIE_TOP_RATED_VIEW=2;
    private  MovieAdapterOnClickHandler mClickHandler;
    private Context context;
    private List<Movie> movieList=new ArrayList<>();
    private int viewRequsted;

    /**
     * The interface that receives onClick messages.
     */
    public interface MovieAdapterOnClickHandler {
        void onMovieClick(int position, View v);

    }
    public MovieRecylcerViewAdapter(Context context, List<Movie> movieList, int viewRequsted,MovieAdapterOnClickHandler mClickHandler) {

        this.context = context;
        this.movieList = movieList;
        this.viewRequsted = viewRequsted;
        this.mClickHandler=mClickHandler;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
          case MOVIE_TOP_RATED_VIEW:
                View movie_top_rated = inflater.inflate(R.layout.movie_card, parent, false);
                viewHolder = new MoviesViewHolder(movie_top_rated,mClickHandler);
                break;
           case MOVIE_POPULAR_VIEW:
                View movie_popular = inflater.inflate(R.layout.movie_card_popular, parent, false);
                viewHolder = new MoviesPopularViewHolder(movie_popular,mClickHandler);
                break;

            default:
                View movie_defult = inflater.inflate(R.layout.movie_card, parent, false);
                viewHolder = new MoviesViewHolder(movie_defult,mClickHandler);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {

            case MOVIE_TOP_RATED_VIEW:
                MoviesViewHolder moviesViewHolder = (MoviesViewHolder) holder;
                configureViewHoldermovies(moviesViewHolder, position);
                break;


            case MOVIE_POPULAR_VIEW:
                MoviesPopularViewHolder moviesPopularViewHolder= (MoviesPopularViewHolder) holder;
                configureViewHoldermoviesPopular(moviesPopularViewHolder, position);
                break;
        }

    }

    @Override
    public int getItemCount() {
        // condition ? passed : failed
        return (movieList != null) ? movieList.size(): 0;
        //return this.movieList.size();
    }


    @Override
    public int getItemViewType(int position) {

        switch (viewRequsted) {
            case MOVIE_TOP_RATED_VIEW:
                return MOVIE_TOP_RATED_VIEW;

            case MOVIE_POPULAR_VIEW:
                return MOVIE_POPULAR_VIEW;
        }
        return viewRequsted;
    }
    private void configureViewHoldermovies(MoviesViewHolder moviesViewHolder, int position) {
        Movie movie = movieList.get(position);
        if (movie != null) {
            String voteAverage = String.valueOf(movie.getVoteAverage());
            moviesViewHolder.ratingText.setText(voteAverage);
            String photoPath = POSTER_PATH + movie.getPosterPath();
            Picasso.with(context).load(photoPath).into(moviesViewHolder.moviePoster);
            Log.i("picassoMajd", photoPath);
        }
    }
    private void configureViewHoldermoviesPopular(MoviesPopularViewHolder moviesPopularViewHolder, int position) {
        Movie movie =movieList.get(position);
        if (movie != null) {
            String popularity= String.valueOf(movie.getPopularity());
            //moviesPopularViewHolder.tvRatingPopular.setText(popularity);
            String photoPath= POSTER_PATH+movie.getPosterPath();
            Picasso.with(context).load(photoPath).into(moviesPopularViewHolder.moviePosterPopular);
            //Picasso.with(context).load(R.drawable.popular).into(moviesPopularViewHolder.imgMoviePopularLogo);
            Log.i("picassoMajd",photoPath);
        }
    }
    public class MoviesViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        private MovieAdapterOnClickHandler mListener;
        public final TextView ratingText;
        public final ImageView moviePoster;
        public final CardView movieCardview;


        public MoviesViewHolder(View itemView, MovieAdapterOnClickHandler listener) {
            super(itemView);
            mListener = listener;
            ratingText = itemView.findViewById(R.id.tv_rating_movie);
            moviePoster = itemView.findViewById(R.id.image_movie_poster);
            movieCardview = itemView.findViewById(R.id.cardview_movie);
            movieCardview.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            mListener.onMovieClick(getAdapterPosition(), v);
        }
    }

        public class MoviesPopularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private MovieAdapterOnClickHandler mListener;
            //public final TextView tvRatingPopular;
            public final ImageView moviePosterPopular;// imgMoviePopularLogo;
            public final CardView movieCardviewPopular;


           public MoviesPopularViewHolder(View itemView, MovieAdapterOnClickHandler listener) {
                super(itemView);
                mListener = listener;
               // imgMoviePopularLogo = itemView.findViewById(R.id.imageView_heart_popular);
                //tvRatingPopular = itemView.findViewById(R.id.tv_rating_movie_popular);
                moviePosterPopular = itemView.findViewById(R.id.image_movie_poster_popular);
                movieCardviewPopular = itemView.findViewById(R.id.cardview_movie_popluar);

                movieCardviewPopular.setOnClickListener(this);
            }


            @Override
            public void onClick(View v) {
                mListener.onMovieClick(getAdapterPosition(), v);
            }
        }
    }

