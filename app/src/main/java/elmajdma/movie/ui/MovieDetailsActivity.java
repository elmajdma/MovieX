package elmajdma.movie.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import elmajdma.movie.R;
import elmajdma.movie.data.local.Movie;
import elmajdma.movie.viewmodel.MoviesViewModel;

import static elmajdma.movie.ui.MainActivity.MOVIE_ID;


public class MovieDetailsActivity extends AppCompatActivity {
    public final static String POSTER_PATH = "http://image.tmdb.org/t/p/w500/";
    private MoviesViewModel viewModel;
    private Toolbar mMovieToolbar;
    private ImageView imgPackdrop, imgPoster;
    private TextView tvTitle, tvReleaseDate, tvOverview, tvVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        viewsDecleration();

        setSupportActionBar(mMovieToolbar);
        mMovieToolbar.hideOverflowMenu();

        viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        movieDetailsbyId();
    }

    private void movieDetailsbyId() {
        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            return;
        }
        int movieId = extras.getInt(MOVIE_ID);
        if (movieId != 0) {
            viewModel.getMovieDetails().observe(this, new Observer<Movie>() {
                @Override
                public void onChanged(@Nullable Movie movie) {
                    String backPath = POSTER_PATH + movie.getBackdropPath();
                    Picasso.with(getApplicationContext()).load(backPath).into(imgPackdrop);
                    String posterPath = POSTER_PATH + movie.getPosterPath();
                    Picasso.with(getApplicationContext()).load(posterPath).into(imgPoster);
                    tvTitle.setText(movie.getTitle());
                    tvReleaseDate.setText(movie.getReleaseDate());
                    tvVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
                    tvOverview.setText(movie.getOverview());
                }
            });
        }
    }

    private void viewsDecleration() {
        mMovieToolbar = findViewById(R.id.toolbar_movie);
        mMovieToolbar = findViewById(R.id.toolbar_movie);
        imgPackdrop = findViewById(R.id.imageview_packdrop);
        imgPoster = findViewById(R.id.iamgeview_poster);
        tvOverview = findViewById(R.id.tv_overview);
        tvReleaseDate = findViewById(R.id.tv_release_date);
        tvTitle = findViewById(R.id.tv_title);
        tvVoteAverage = findViewById(R.id.tv_vote_average);

    }
}
