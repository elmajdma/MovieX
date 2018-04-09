package elmajdma.movie.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import elmajdma.movie.data.local.Movie;
import elmajdma.movie.data.model.MovieReviews;
import elmajdma.movie.data.model.MovieTrailers;
import elmajdma.movie.data.remote.RetrofitClient;

/**
 * Created by majd on 20-Feb-18.
 */

public class MoviesViewModel extends ViewModel {
    private LiveData<Movie> selectedMovieDetails = new MutableLiveData<Movie>();
    private LiveData<List<MovieTrailers>> selectedMovieTrialers = new MutableLiveData<List<MovieTrailers>>();
    private LiveData<List<MovieReviews>> selectedMovieReviews = new MutableLiveData<>();

    public MoviesViewModel() {

    }

    public LiveData<List<Movie>> getmoviesLiveData(String category) {
        return LiveDataReactiveStreams.fromPublisher(RetrofitClient.getMovieModel(category));
    }

    public LiveData<Movie> getMovieDetails() {
        return selectedMovieDetails;
    }

    public void setMovieDetails(int movieId) {
        selectedMovieDetails = LiveDataReactiveStreams.fromPublisher(RetrofitClient.getMovieDetailsX(movieId));
    }

    public void setMovieTrialer(int movieId) {
        selectedMovieTrialers = LiveDataReactiveStreams.fromPublisher(RetrofitClient.getMovieTrailers(movieId));
    }

    public LiveData<List<MovieTrailers>> getMovieTrailers() {
        return selectedMovieTrialers;
    }


    public void setMovieReviews(int movieId) {
        selectedMovieReviews = LiveDataReactiveStreams.fromPublisher(RetrofitClient.getMovieReviews(movieId));
    }

    public LiveData<List<MovieReviews>> getSelectedMovieReviews() {
        return selectedMovieReviews;
    }
}


