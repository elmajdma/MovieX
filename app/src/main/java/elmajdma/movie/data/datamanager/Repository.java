package elmajdma.movie.data.datamanager;

import java.util.List;

import elmajdma.movie.data.local.Movie;
import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by majd on 20-Feb-18.
 */

public interface Repository {

    //Observable<List<Movie>> movies();
    //Observable<List<Movie>> movies();

    Observable<List<Movie>> getDbMovieList();
    Flowable<List<Movie>> getApiMovieList();
}
