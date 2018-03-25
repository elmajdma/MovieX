package elmajdma.movie.data.datamanager;
import android.app.Application;

import java.util.List;
import elmajdma.movie.data.local.Movie;
import elmajdma.movie.data.local.MovieDao;
import elmajdma.movie.data.local.MoviesDatabase;
import elmajdma.movie.data.remote.RetrofitClient;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by majd on 24-Feb-18.
 */

public class MoviesRepository {
   /* private MovieDao mDao;
    public Observable<List<Movie>> getMovieList() {
        return Observable.concatArray(getDbMovieList(), getApiMovieList())
                .doOnNext(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {

                    }
                });
    }

    @Override
    public Observable<List<Movie>> getDbMovieList() {
        return Observable.fromArray(mDao.getMovies());
    }

    public MoviesRepository(Application application) {
        MoviesDatabase mMoviesDatabase=MoviesDatabase.getMoviesDatabase(application);
        mDao=mMoviesDatabase.movieDao();
        //mDao=MoviesDatabase.getMoviesDatabase(context.getApplicationContext()).movieDao();
       // getMovieList();
    }

    @Override
    public Flowable<List<Movie>> getApiMovieList() {
        return RetrofitClient.getMovieModel("popular");
    }
    public void setDatabaseData(Observable<List<Movie>> movies){
        movies.doOnNext(new Consumer<List<Movie>>() {
            @Override
            public void accept(List<Movie> movies) throws Exception {
                mDao.saveAll(movies);
            }
        });
    }*/
}

