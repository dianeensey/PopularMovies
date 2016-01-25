package app.com.beyond_paper.popularmovies;

import android.content.Intent;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import static app.com.beyond_paper.popularmovies.Constants.PAR_KEY;
import static app.com.beyond_paper.popularmovies.Constants.TMDB_POSTER_URL;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class DetailActivity extends ActionBarActivity {
    String movieString;
    private MovieList movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, new DetailFragment())
                    .commit();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(PAR_KEY, movieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
     * Placeholder fragment containing the detail view
     */
    public class DetailFragment extends Fragment {
        private String LOG_TAG = DetailFragment.class.getSimpleName();

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Intent intent = getActivity().getIntent();

            if(savedInstanceState != null && savedInstanceState.containsKey(PAR_KEY)){
                movieList = savedInstanceState.getParcelable(PAR_KEY);
                movieString = movieList.toString();

            }else{
                movieList = intent.getParcelableExtra(PAR_KEY);
                movieString = movieList.toString();
            }
            Bundle mBundle = new Bundle();
            mBundle.putParcelable("movie",movieList);
            onSaveInstanceState(mBundle);

            //Data is in string array in order: 0 = "id", 1 = "poster_path"
            //2 = "title"; 3= "overview"; 4= "vote_average"; 5= "release_date";
            //6 = "video";
            String[] movieData = movieString.split("\\|");
            Log.v(LOG_TAG, String.valueOf(movieData));

            if(movieData != null) {
                Resources res = getResources();
                //Assign the text strings to the layout components
                ((TextView) rootView.findViewById(R.id.movieDetailTitle)).setText(movieData[2]);
                ((TextView) rootView.findViewById(R.id.movieDetailReleaseDate)).setText(String.format(res.getString(R.string.detailReleased),movieData[5]));
                ((TextView) rootView.findViewById(R.id.movieDetailUserRating)).setText(String.format(res.getString(R.string.detailUserRating), movieData[4]));
                ((TextView) rootView.findViewById(R.id.movieDetail)).setText(movieData[3]);
                ImageView imageView = new ImageView(getActivity());
                //Posters are 185w x 278h
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setPadding(8, 8, 8, 8);
                final String url = TMDB_POSTER_URL + movieData[1];
                Picasso.with(getActivity()).load(url).into(((ImageView) rootView.findViewById(R.id.movieDetailPoster)));
            }else{
                Log.e(LOG_TAG, "No movie data found.");
            }


            return rootView;
        }
    }
}
