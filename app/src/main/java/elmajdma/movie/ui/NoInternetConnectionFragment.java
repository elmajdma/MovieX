package elmajdma.movie.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import elmajdma.movie.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoInternetConnectionFragment extends Fragment {


    public NoInternetConnectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_no_internet_connection, container, false);
        TextView tvNointernet = view.findViewById(R.id.tv_no_iternet_connection);
        tvNointernet.setText(R.string.no_internet);

        return view;
    }

}
