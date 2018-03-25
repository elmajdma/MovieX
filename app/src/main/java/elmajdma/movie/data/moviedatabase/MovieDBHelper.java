package elmajdma.movie.data.moviedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by majd on 25-Mar-18.
 */

public class MovieDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movieList.db";


    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        addMovieTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void addMovieTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                        MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY, " +
                        MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL" +
                        ");"
        );
    }
}
