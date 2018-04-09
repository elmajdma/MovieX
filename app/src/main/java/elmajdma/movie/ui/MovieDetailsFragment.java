package elmajdma.movie.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import elmajdma.movie.R;
import elmajdma.movie.adapters.MovieTrailerAdapter;
import elmajdma.movie.data.local.Movie;
import elmajdma.movie.data.model.MovieTrailers;
import elmajdma.movie.data.moviedatabase.MovieContract;
import elmajdma.movie.utils.DbBitmapUtility;
import elmajdma.movie.utils.NonScrollListView;
import elmajdma.movie.viewmodel.MoviesViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends Fragment {

    public final static String POSTER_PATH = "http://image.tmdb.org/t/p/w500/";
    public final static String YOUTUBE_THUMB_PATH = "https://img.youtube.com/vi/";
    public static final String YOUTUBE_TRAILER_KEY = "trailer key";
    private int movieId;
    private Movie movieDetails;
    private MoviesViewModel viewModel;
    private Toolbar mMovieToolbar;
    private ListView mListView;
    private NonScrollListView mNonScrollListView;
    private String movieTitle;
    private ImageView imgPackdrop, imgPoster, imgTrailer, imgHeart;
    private TextView tvTitle, tvReleaseDate, tvOverview, tvReviews, tvVoteAverage;
    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        viewsDecleration(view);
        viewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);
        viewModel.getMovieDetails().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                movieDetails = movie;
                setDetailedFramgmentViewsData(movieDetails);
            }
        });

        viewModel.getMovieTrailers().observe(this, new Observer<List<MovieTrailers>>() {
            @Override
            public void onChanged(@Nullable List<MovieTrailers> movieTrailers) {
                MovieTrailerAdapter movieTrailerAdapter = new MovieTrailerAdapter(getContext(), movieTrailers);
                mNonScrollListView.setAdapter(movieTrailerAdapter);
                mNonScrollListView.setOnItemClickListener((parent, view1, position, id) -> {
                    MovieTrailers mTrailers = movieTrailers.get(position);

                    setYoutubeKey(mTrailers.getKey());
                });
            }
        });

        return view;
    }

    private void setDetailedFramgmentViewsData(Movie movie) {
        isMovieSelected(movie.getId());
        viewModel.setMovieReviews(movie.getId());
        String backPath = POSTER_PATH + movie.getBackdropPath();
        Picasso.with(getContext()).load(backPath).into(imgPackdrop);
        String posterPath = POSTER_PATH + movie.getPosterPath();
        Picasso.with(getContext()).load(posterPath).into(imgPoster);
        movieTitle = movie.getTitle();
        tvTitle.setText(movieTitle);
        tvReleaseDate.setText(movie.getReleaseDate());
        tvVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
        tvOverview.setText(movie.getOverview());
    }

    private void viewsDecleration(View view) {
        mMovieToolbar = view.findViewById(R.id.toolbar_movie);
        imgPackdrop = view.findViewById(R.id.imageview_packdrop);
        imgPoster = view.findViewById(R.id.iamgeview_poster);
        mNonScrollListView = view.findViewById(R.id.lv_trailers);
        tvOverview = view.findViewById(R.id.tv_overview);
        tvReleaseDate = view.findViewById(R.id.tv_release_date);
        tvTitle = view.findViewById(R.id.tv_title);
        tvVoteAverage = view.findViewById(R.id.tv_vote_average);
        imgTrailer = view.findViewById(R.id.imageView_trailer);
        imgHeart = view.findViewById(R.id.imageview_heart);
        imgHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setFavoritelist();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Picasso.with(getContext()).load(R.drawable.heart_black).into(imgHeart);
        imgHeart.setTag(R.drawable.heart_black);
        tvReviews = view.findViewById(R.id.tv_reviews);
        tvReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReviewsFragment();
            }
        });


    }

    private void setFavoritelist() throws IOException {
        switch ((Integer) imgHeart.getTag()) {
            case (R.drawable.heart_black):
                imgHeart.setTag(R.drawable.heart_red);
                Picasso.with(getContext()).load(R.drawable.heart_red).into(imgHeart);
                insertMovieintoFavoirte(movieDetails);
                break;
            case (R.drawable.heart_red):
                imgHeart.setTag(R.drawable.heart_black);
                Picasso.with(getContext()).load(R.drawable.heart_black).into(imgHeart);
                deleteMovieSelected();
                Toast.makeText(getContext(), "Movie Removed From Favorite !!", Toast.LENGTH_LONG).show();
        }
    }

    private void insertMovieintoFavoirte(Movie movie) throws IOException {
        Bitmap bitmap = ((BitmapDrawable) imgPoster.getDrawable()).getBitmap();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getId());
        contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_REVIEW, movie.getOverview());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_DATE, movie.getReleaseDate());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_RATE, String.valueOf(movie.getVoteAverage()));
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER, DbBitmapUtility.getBytes(bitmap));
        Uri uri = getActivity().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
        if (uri != null) {
            Toast.makeText(getContext(), "Movie saved in Favorite List", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "An Error Occurred While Saving Movie !!", Toast.LENGTH_LONG).show();
        }
    }

    /* private void isMovieSelected(Movie movie) {
         Uri uri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI,
                 movieId);
         Cursor mCursor = getActivity().getContentResolver().query(
                 uri,
                 null,
                 MovieContract.MovieEntry.COLUMN_MOVIE_ID,
                 new String[]{String.valueOf(movie.getId())},
                 null);
         if (mCursor != null && mCursor.moveToFirst()){
             imgHeart.setTag(R.drawable.heart_red);
             Picasso.with(getContext()).load(R.drawable.heart_red).into(imgHeart);

             *//*do {
            imgHeart.setTag(R.drawable.heart_red);
            Picasso.with(getContext()).load(R.drawable.heart_red).into(imgHeart);
        } while (mCursor.moveToNext());*//*
    }
    else{
            imgHeart.setTag(R.drawable.heart_black);
           Picasso.with(getContext()).load(R.drawable.heart_black).into(imgHeart);
       }

    }*/
    private void isMovieSelected(int movieId) {
        Uri uri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI,
                movieId);
        Cursor mCursor = getActivity().getContentResolver().query(
                uri,
                null,
                MovieContract.MovieEntry.COLUMN_MOVIE_ID,
                new String[]{String.valueOf(movieId)},
                null);
        if (mCursor != null && mCursor.moveToFirst()) {
            imgHeart.setTag(R.drawable.heart_red);
            Picasso.with(getContext()).load(R.drawable.heart_red).into(imgHeart);
        } else {
            imgHeart.setTag(R.drawable.heart_black);
            Picasso.with(getContext()).load(R.drawable.heart_black).into(imgHeart);
        }
    }

    private void deleteMovieSelected() {
        Uri uri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI,
                movieId);
        int deltedNum = getActivity().getContentResolver().delete(
                uri,
                MovieContract.MovieEntry.COLUMN_MOVIE_ID,
                new String[]{String.valueOf(movieId)}
        );
        if (deltedNum > -1) {
            Toast.makeText(getContext(), "Movie Removed From Favorite List", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "An Error Occurred While Remove Movie !!", Toast.LENGTH_LONG).show();
        }
    }


    private void setYoutubeKey(String key) {

        Intent intent = new Intent(getActivity(), YoutubeActivity.class);
        intent.putExtra(YOUTUBE_TRAILER_KEY, key);
        startActivity(intent);
    }

    private void setReviewsFragment() {
        Fragment fragment = new ReviewsFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_loader, fragment);
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
