package elmajdma.movie.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by majd on 05-Mar-18.
 */

public class CalcFittedColumn {
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        Log.i("dp", String.valueOf(displayMetrics.density));
        Log.i("dpWidth", String.valueOf(dpWidth));
        float dpImage=(185*160)/displayMetrics.densityDpi;
        Log.i("dpdensityDpi", String.valueOf(displayMetrics.densityDpi));
        Log.i("dpImage", String.valueOf(dpImage));
        //float dpWidth = 185 / displayMetrics.density;
       // dp = px / (dpi / 160)

        return (int) (dpWidth/dpImage);
    }
}
