package elmajdma.movie.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import elmajdma.movie.R;
import elmajdma.movie.data.moviedatabase.MovieContract;
import elmajdma.movie.utils.DbBitmapUtility;

public class MovieFavoriteRecyclerViewAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Cursor mCursor;
    private Context mContext;


    public MovieFavoriteRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View movieFavoirteView = inflater.inflate(R.layout.favoirte_item, parent, false);
        return new MovieFavoriteViewHolder(movieFavoirteView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieFavoriteRecyclerViewAdapter.MovieFavoriteViewHolder moviesViewHolder = (MovieFavoriteRecyclerViewAdapter.MovieFavoriteViewHolder) holder;
        configureViewHolderMovieReview(moviesViewHolder, position);
    }

    @Override
    public int getItemCount() {
        // condition ? passed : failed
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    private void configureViewHolderMovieReview(MovieFavoriteViewHolder movieFavoriteViewHolder, int position) {
        // Indices for the  movie_id, and movie_title column
        int movieIdIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
        int movieTitleIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE);
        int moviePosterIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER);
        int movieRateIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_RATE);
        int movieDateIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_DATE);
        int movieReviewIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_REVIEW);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        String movieId = mCursor.getString(movieIdIndex);
        String movieTitle = mCursor.getString(movieTitleIndex);
        movieFavoriteViewHolder.favoirteMovieTitle.setText(movieTitle);
        byte[] imgByte = mCursor.getBlob(moviePosterIndex);
        Bitmap bitmap = DbBitmapUtility.getImage(imgByte);
        movieFavoriteViewHolder.favoirteMoviePoster.setImageBitmap(bitmap);
        movieFavoriteViewHolder.favoriteMovieDate.setText(mCursor.getString(movieDateIndex));
        movieFavoriteViewHolder.favoriteMovieRate.setText(mCursor.getString(movieRateIndex));
        movieFavoriteViewHolder.favoriteMovieReview.setText(mCursor.getString(movieReviewIndex));


    }

    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    public class MovieFavoriteViewHolder extends RecyclerView.ViewHolder {

        public final TextView favoirteMovieTitle, favoriteMovieRate, favoriteMovieDate, favoriteMovieReview;
        public final ImageView favoirteMoviePoster;


        public MovieFavoriteViewHolder(View itemView) {
            super(itemView);

            favoirteMovieTitle = itemView.findViewById(R.id.tv_favoirte_movie_title);
            favoriteMovieRate = itemView.findViewById(R.id.tv_user_rate_favorite_movie);
            favoriteMovieDate = itemView.findViewById(R.id.tv_released_date_favorite_movie);
            favoriteMovieReview = itemView.findViewById(R.id.tv_overview_favorite_movie);
            favoirteMoviePoster = itemView.findViewById(R.id.imageView_poster_favorite);

        }

    }
}
