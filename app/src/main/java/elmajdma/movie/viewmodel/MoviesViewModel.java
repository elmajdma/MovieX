package elmajdma.movie.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import elmajdma.movie.data.local.Movie;
import elmajdma.movie.data.remote.RetrofitClient;

/**
 * Created by majd on 20-Feb-18.
 */

public class MoviesViewModel extends ViewModel {
    private LiveData<Movie> selectedMovieDetails = new MutableLiveData<Movie>();

    public MoviesViewModel() {

    }
       public LiveData<List<Movie>>getmoviesLiveData(String category){
        return LiveDataReactiveStreams.fromPublisher(RetrofitClient.getMovieModel(category));
        }


    public void setMovieDetails(int movieId){
         selectedMovieDetails=LiveDataReactiveStreams.fromPublisher(RetrofitClient.getMovieDetailsX(movieId));
    }

    public LiveData<Movie> getMovieDetails(){
        return selectedMovieDetails;
    }

    }

