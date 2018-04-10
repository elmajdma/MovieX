package elmajdma.movie.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
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
    private static final String BUNDLE_RECYCLEBR_LAYOUT = "recycler_layout";
    private Parcelable savedRecyclerLayoutState;
    private RecyclerView reviewsRecyclerView;
    private MovieReviewRecyclerviewAdapter movieReviewRecyclerviewAdapter;
    private MoviesViewModel viewModel;
    private TextView tvNoReviews;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        //restore recycler view at same position
        if (savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLEBR_LAYOUT);
            reviewsRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLEBR_LAYOUT, reviewsRecyclerView.getLayoutManager().onSaveInstanceState());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);
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
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        reviewsRecyclerView.setHasFixedSize(true);
    }
    private void setReviewRecyclerView(List<MovieReviews> movieReviewslist) {
        movieReviewRecyclerviewAdapter =
                new MovieReviewRecyclerviewAdapter(movieReviewslist, getActivity());

        reviewsRecyclerView.setAdapter(movieReviewRecyclerviewAdapter);
        if (savedRecyclerLayoutState != null) {
            reviewsRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }

    }
}
