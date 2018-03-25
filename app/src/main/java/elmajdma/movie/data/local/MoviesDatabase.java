package elmajdma.movie.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * Created by majd on 20-Feb-18.
 */
@Database(entities = {Movie.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class MoviesDatabase extends RoomDatabase{
    private static MoviesDatabase INSTANCE;
    public abstract MovieDao movieDao();

    public static MoviesDatabase getMoviesDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MoviesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MoviesDatabase.class, "moviesDB")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
