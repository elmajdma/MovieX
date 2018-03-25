package elmajdma.movie.data.remote;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import elmajdma.movie.data.local.Movie;
import elmajdma.movie.data.model.MovieReviews;
import elmajdma.movie.data.model.MovieTrailers;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by majd on 20-Feb-18.
 */

public class RetrofitClient {
    public static final String BASE_URL = "https://api.themoviedb.org";
    private static final String API_KEY = "fc4207116cc3ae37fff9e6101af5098f";

    public static Flowable<List<Movie>> getMovieModel(String categeory) {
        final Gson gson =
                new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .registerTypeAdapterFactory(new MovieTypeAdapterFactory())
                        .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        MoviesApiService moviesApiService = retrofit.create(MoviesApiService.class);
        return moviesApiService.movies(categeory, API_KEY);
    }


    public static Flowable<Movie> getMovieDetailsX(int movieId) {
        final Gson gson =
                new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        MoviesApiService moviesApiService = retrofit.create(MoviesApiService.class);
        return moviesApiService.movieDetailsX(movieId, API_KEY);
    }


    public static Flowable<List<MovieTrailers>> getMovieTrailers(int movieId) {
        final Gson gson =
                new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .registerTypeAdapterFactory(new MovieTypeAdapterFactory())
                        .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        MoviesApiService moviesApiService = retrofit.create(MoviesApiService.class);
        return moviesApiService.getMovieTrialers(movieId,API_KEY);
    }

    public static Flowable<List<MovieReviews>> getMovieReviews(int movieId) {
        final Gson gson =
                new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .registerTypeAdapterFactory(new MovieTypeAdapterFactory())
                        .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        MoviesApiService moviesApiService = retrofit.create(MoviesApiService.class);
        return moviesApiService.getMovieReviews(movieId,API_KEY);
    }
}

