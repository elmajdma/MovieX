package elmajdma.movie.ui;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import elmajdma.movie.R;
import elmajdma.movie.adapters.MovieFavoriteRecyclerViewAdapter;
import elmajdma.movie.data.moviedatabase.MovieContract;


public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = FavoriteFragment.class.getSimpleName();
    private static final int TASK_LOADER_ID = 0;
    private RecyclerView favoirteRecyclerView;
    private Toolbar mMovieToolbar;
    private MovieFavoriteRecyclerViewAdapter movieFavoriteRecyclerViewAdapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(TASK_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        initViews(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initViews(View view) {

        favoirteRecyclerView = view.findViewById(R.id.recyclerview_favorite_movies);
        favoirteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoirteRecyclerView.setHasFixedSize(true);
        favoirteRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        movieFavoriteRecyclerViewAdapter = new MovieFavoriteRecyclerViewAdapter(getActivity());
        favoirteRecyclerView.setAdapter(movieFavoriteRecyclerViewAdapter);
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<Cursor>(getContext()) {


            // Initialize a Cursor, this will hold all the task data
            Cursor mTaskData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mTaskData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {

                // Will implement to load data

                try {
                    return getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            MovieContract.MovieEntry.COLUMN_TITLE);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }


        };

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        movieFavoriteRecyclerViewAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        movieFavoriteRecyclerViewAdapter.swapCursor(null);
    }

    @Override
    public void onResume() {
        super.onResume();

        // re-queries for all tasks
        getActivity().getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }
}
