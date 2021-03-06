package elmajdma.movie.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import elmajdma.movie.R;
import elmajdma.movie.adapters.MovieRecylcerViewAdapter;
import elmajdma.movie.data.local.Movie;
import elmajdma.movie.utils.CalcFittedColumn;
import elmajdma.movie.utils.InternetCheck;
import elmajdma.movie.viewmodel.MoviesViewModel;

import static elmajdma.movie.ui.MainActivity.MENU_SELECTED;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesListFragment extends Fragment {
    private static final String TOP_RATED = "top_rated";
    private static final String POPULAR = "popular";

    private static final String BUNDLE_RECYCLEBR_LAYOUT = "recycler_layout";
    private Parcelable savedRecyclerLayoutState;

    //private final static String MENU_SELECTED = "selected";
    public final static String MOVIE_ID="movie_id";
    private List<Movie> moviesList;
    private String selectedCategory = null;

    private RecyclerView moviesRecyclerView;
    private MovieRecylcerViewAdapter movieRecylcerViewAdapter;
    private MoviesViewModel viewModel;
    private InternetCheck internetCheck;
    private ContentValues mContentValues = new ContentValues();



    public MoviesListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        //restore recycler view at same position
        if (savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLEBR_LAYOUT);
            moviesRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLEBR_LAYOUT, moviesRecyclerView.getLayoutManager().onSaveInstanceState());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);
        Bundle bundle=this.getArguments();
        getMovieData(bundle.getString(MENU_SELECTED));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);
        initViews(view);


        return view;
    }
    private void initViews(View view) {
        moviesRecyclerView = view.findViewById(R.id.recyclerview_movies);
        GridLayoutManager layoutManager;
        int mNoOfColumns = CalcFittedColumn.calculateNoOfColumns(getActivity());
        layoutManager = new GridLayoutManager(getActivity(), mNoOfColumns);
        moviesRecyclerView.setLayoutManager(layoutManager);
        moviesRecyclerView.setHasFixedSize(true);
    }


    public void getMovieData(String movieCategory){
        viewModel.getmoviesLiveData(movieCategory).observe
                (this, movies -> setUpMoviesRecyclerView(movies,movieCategory));
    }

    private void setUpMoviesRecyclerView(List<Movie> movieList, String category) {
        MovieRecylcerViewAdapter.MovieAdapterOnClickHandler mListener=new MovieRecylcerViewAdapter.MovieAdapterOnClickHandler() {
            @Override
            public void onMovieClick(int position, View v) {
                Movie movie=movieList.get(position);
               viewModel.setMovieDetails(movie.getId());
                viewModel.setMovieTrialer(movie.getId());
                //mContentValues.put("")
                setDetailedFragment();
            }
        };
        switch (category){
            case POPULAR:
                movieRecylcerViewAdapter = new MovieRecylcerViewAdapter(getContext(), movieList, MovieRecylcerViewAdapter.MOVIE_POPULAR_VIEW,mListener);
                moviesRecyclerView.setAdapter(movieRecylcerViewAdapter);
                break;
            case TOP_RATED:
                movieRecylcerViewAdapter = new MovieRecylcerViewAdapter(getContext(), movieList, MovieRecylcerViewAdapter.MOVIE_TOP_RATED_VIEW,mListener);
                moviesRecyclerView.setAdapter(movieRecylcerViewAdapter);
                break;
        }



    }
    private void setDetailedFragment(){
        Fragment fragment = new MovieDetailsFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_loader, fragment);
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
