package elmajdma.movie.data.moviedatabase;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by majd on 25-Mar-18.
 */

public class MovieContract {
    /**
     * The Content Authority is a name for the entire content provider, similar to the relationship
     * between a domain name and its website. A convenient string to use for content authority is
     * the package name for the app, since it is guaranteed to be unique on the device.
     */
    public static final String CONTENT_AUTHORITY = "elmajdma.movie.data.moviedatabase";

    /**
     * The content authority is used to create the base of all URIs which apps will use to
     * contact this content provider.
     */
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);



    /**
     * Create one class for each table that handles all information regarding the table schema and
     * the URIs related to it.
     */
    public static final class MovieEntry implements BaseColumns {
        /**
         * A list of possible paths that will be appended to the base URI for each of the different
         * tables.
         */
        public static final String TABLE_MOVE = "movie";
        // Content URI represents the base location for the table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_MOVE).build();

        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE =
                //  "vnd.android.cursor.dir/" + CONTENT_URI  + "/" + PATH_MOVIE;
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_MOVE;
        public static final String CONTENT_ITEM_TYPE =
                //"vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_MOVIE;
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_MOVE;

        // Define the table schema
        public static final String COLUMN_TITLE = "movieTitle";
        public static final String COLUMN_MOVIE_ID ="movieId";
        public static final String COLUMN_MOVIE_POSTER = "moviePoster";
        public static final String COLUMN_MOVIE_DATE = "movieDate";
        public static final String COLUMN_MOVIE_RATE = "movieRate";
        public static final String COLUMN_MOVIE_REVIEW = "movieReview";
        // Define a function to build a URI to find a specific movie by it's identifier
        // for building URIs on insertion
        public static Uri buildMoviessUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}


