package elmajdma.movie.data.moviedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by majd on 25-Mar-18.
 */

public class MovieDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "movieList.db";


    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        addMovieTable(db);
    }



    private void addMovieTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + MovieContract.MovieEntry.TABLE_MOVE + " (" +
                        // MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                        MovieContract.MovieEntry.COLUMN_MOVIE_POSTER + " BLOB NOT NULL," +
                        MovieContract.MovieEntry.COLUMN_MOVIE_DATE + " TEXT NOT NULL," +
                        MovieContract.MovieEntry.COLUMN_MOVIE_RATE + " TEXT NOT NULL," +
                        MovieContract.MovieEntry.COLUMN_MOVIE_REVIEW + " TEXT NOT NULL," +
                        " UNIQUE (" + MovieContract.MovieEntry.COLUMN_TITLE + ") ON CONFLICT REPLACE);"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_MOVE);
        onCreate(sqLiteDatabase);
    }
}
