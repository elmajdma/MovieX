package elmajdma.movie.data.remote;

import java.util.List;

import elmajdma.movie.data.local.Movie;
import elmajdma.movie.data.model.MovieReviews;
import elmajdma.movie.data.model.MovieTrailers;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by majd on 20-Feb-18.
 */

public interface MoviesApiService {
    @GET("/3/movie/{category}")
    Flowable<List<Movie>> movies(@Path("category") String categoryMovie,
                                 @Query("api_key") String keyApi);


        @GET("/3/movie/{movieId}")
    Flowable<Movie> movieDetailsX(@Path("movieId") int movieId,
                                        @Query("api_key") String keyApi);

    @GET("/3/movie/{movieId}/videos")
    Flowable<List<MovieTrailers>> getMovieTrialers(@Path("movieId") int movieId,
                                  @Query("api_key") String keyApi);

    @GET("/3/movie/{movieId}/reviews")
    Flowable<List<MovieReviews>> getMovieReviews(@Path("movieId") int movieId,
                                                 @Query("api_key") String keyApi);

}
