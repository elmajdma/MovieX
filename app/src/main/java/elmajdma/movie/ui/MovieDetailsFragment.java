package elmajdma.movie.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import elmajdma.movie.R;
import elmajdma.movie.data.local.Movie;
import elmajdma.movie.viewmodel.MoviesViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends Fragment {
    public final static String POSTER_PATH = "http://image.tmdb.org/t/p/w500/";
    private MoviesViewModel viewModel;
    private Toolbar mMovieToolbar;
    private ImageView imgPackdrop, imgPoster;
    private TextView tvTitle, tvReleaseDate, tvOverview, tvVoteAverage;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_movie_details, container, false);
        viewsDecleration(view);

        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);
        viewModel.getMovieDetails().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                String backPath = POSTER_PATH + movie.getBackdropPath();
                Picasso.with(getContext()).load(backPath).into(imgPackdrop);
                String posterPath = POSTER_PATH + movie.getPosterPath();
                Picasso.with(getContext()).load(posterPath).into(imgPoster);
                tvTitle.setText(movie.getTitle());
                tvReleaseDate.setText(movie.getReleaseDate());
                tvVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
                tvOverview.setText(movie.getOverview());
            }
        });

    }

    private void viewsDecleration(View view) {
        mMovieToolbar = view.findViewById(R.id.toolbar_movie);
        mMovieToolbar = view.findViewById(R.id.toolbar_movie);
        imgPackdrop = view.findViewById(R.id.imageview_packdrop);
        imgPoster = view.findViewById(R.id.iamgeview_poster);
        tvOverview = view.findViewById(R.id.tv_overview);
        tvReleaseDate = view.findViewById(R.id.tv_release_date);
        tvTitle = view.findViewById(R.id.tv_title);
        tvVoteAverage = view.findViewById(R.id.tv_vote_average);

    }

}
