package elmajdma.movie.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import elmajdma.movie.R;
import elmajdma.movie.adapters.MovieReviewRecyclerviewAdapter;
import elmajdma.movie.data.model.MovieReviews;
import elmajdma.movie.viewmodel.MoviesViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {
    private RecyclerView reviewsRecyclerView;
    private MovieReviewRecyclerviewAdapter movieReviewRecyclerviewAdapter;
    private MoviesViewModel viewModel;
    private TextView tvNoReviews;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);
        //Bundle bundle=this.getArguments();
        //getMovieData(bundle.getString(MENU_SELECTED));
        viewModel.getSelectedMovieReviews().observe(this, new Observer<List<MovieReviews>>() {
            @Override
            public void onChanged(@Nullable List<MovieReviews> movieReviews) {
                if (movieReviews.isEmpty()) {
                    reviewsRecyclerView.setVisibility(View.GONE);
                    tvNoReviews.setVisibility(View.VISIBLE);
                    tvNoReviews.setText(R.string.no_reviews);

                } else {
                    reviewsRecyclerView.setVisibility(View.VISIBLE);
                    tvNoReviews.setVisibility(View.INVISIBLE);
                    setReviewRecyclerView(movieReviews);

                }


            }
        });
    }

    public void getMovieData(String movieCategory) {
        viewModel.getSelectedMovieReviews().observe(this, new Observer<List<MovieReviews>>() {
            @Override
            public void onChanged(@Nullable List<MovieReviews> movieReviews) {
                setReviewRecyclerView(movieReviews);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        tvNoReviews = view.findViewById(R.id.tv_no_reviews);

        reviewsRecyclerView = view.findViewById(R.id.recyclerview_reviews);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewsRecyclerView.setHasFixedSize(true);
    }

    private void setReviewRecyclerView(List<MovieReviews> movieReviewslist) {
        //Toast.makeText(getContext(),movieReviewslist.toString(),Toast.LENGTH_LONG).show();
        movieReviewRecyclerviewAdapter =
                new MovieReviewRecyclerviewAdapter(movieReviewslist, getContext());
        reviewsRecyclerView.setAdapter(movieReviewRecyclerviewAdapter);
    }
}
