package elmajdma.movie.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import elmajdma.movie.R;
import elmajdma.movie.data.model.MovieTrailers;

public class MovieTrailerAdapter extends ArrayAdapter<MovieTrailers> {
    public final static String YOUTUBE_THUMB_PATH = "https://img.youtube.com/vi/";
    private static LayoutInflater inflater = null;
    private Context mContext;
    private List<MovieTrailers> movieTrailersList;


    public MovieTrailerAdapter(@NonNull Context context, @NonNull List<MovieTrailers> objects) {
        super(context, R.layout.movie_trailers_item, objects);
        this.mContext = context;
        this.movieTrailersList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get data from position
        MovieTrailers movieTrailers = getItem(position);
        ViewHolder viewHolder;
        final View result;


        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_trailers_item, parent, false);
            viewHolder.tvTrailer = convertView.findViewById(R.id.textView_watch_trailer);
            viewHolder.imgTrialer = convertView.findViewById(R.id.imageView_trailer);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTrailer.setText(movieTrailers.getName());

        String thumPath = YOUTUBE_THUMB_PATH + movieTrailers.getKey() + "/hqdefault.jpg";
        //Log.i("Thumbainl_image_url",thumPath);
        Picasso.with(getContext()).load(thumPath).into(viewHolder.imgTrialer);

        //Picasso.with(getContext()).load(movieTrailers.getKey()).into(viewHolder.imgTrialer);


        return convertView;
    }

   /* @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        MovieTrailers movieTrailers=(MovieTrailers) object;

        switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;
    }*/

    private static class ViewHolder {
        public ImageView imgTrialer;
        public TextView tvTrailer;

    }
}
