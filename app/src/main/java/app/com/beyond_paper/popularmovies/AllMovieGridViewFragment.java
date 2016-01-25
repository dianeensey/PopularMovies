package app.com.beyond_paper.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import static app.com.beyond_paper.popularmovies.Constants.TMDB_POSTER_URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class AllMovieGridViewFragment extends Fragment {
    public MovieAdapter mMovieAdapter;
    /*
     *Dummy data new MovieList(id, poster, name, synopsis, user_rating, release_date, on_video)
     * Some code used from https://github.com/udacity/android-custom-arrayadapter
     */
    public MovieList[] mMovieLists={
            new MovieList("140607", "/fYzpM9GmpBlIC893fNjoWCwE24H.jpg", "Star Wars: The Force Awakens", "The Description", 7.85, "2015-12-18", "false"),
            new MovieList("135397", "/jcUXVtJ6s0NG0EaxllQCAUtqdr0.jpg", "Jurassic World", "The Description", 6.7, "2015-06-12", "false"),
            new MovieList("140607", "/fYzpM9GmpBlIC893fNjoWCwE24H.jpg", "Star Wars: The Force Awakens", "The Description", 7.85, "2015-12-18", "false"),
            new MovieList("135397", "/jcUXVtJ6s0NG0EaxllQCAUtqdr0.jpg", "Jurassic World", "The Description", 6.7, "2015-06-12", "false"),
            new MovieList("140607", "/fYzpM9GmpBlIC893fNjoWCwE24H.jpg", "Star Wars: The Force Awakens", "The Description", 7.85, "2015-12-18", "false"),
            new MovieList("135397", "/jcUXVtJ6s0NG0EaxllQCAUtqdr0.jpg", "Jurassic World", "The Description", 6.7, "2015-06-12", "false"),
            new MovieList("140607", "/fYzpM9GmpBlIC893fNjoWCwE24H.jpg", "Star Wars: The Force Awakens", "The Description", 7.85, "2015-12-18", "false"),
            new MovieList("135397", "/jcUXVtJ6s0NG0EaxllQCAUtqdr0.jpg", "Jurassic World", "The Description", 6.7, "2015-06-12", "false"),
            new MovieList("140607", "/fYzpM9GmpBlIC893fNjoWCwE24H.jpg", "Star Wars: The Force Awakens", "The Description", 7.85, "2015-12-18", "false"),
            new MovieList("135397", "/jcUXVtJ6s0NG0EaxllQCAUtqdr0.jpg", "Jurassic World", "The Description", 6.7, "2015-06-12", "false"),
            new MovieList("140607", "/fYzpM9GmpBlIC893fNjoWCwE24H.jpg", "Star Wars: The Force Awakens", "The Description", 7.85, "2015-12-18", "false"),
            new MovieList("135397", "/jcUXVtJ6s0NG0EaxllQCAUtqdr0.jpg", "Jurassic World", "The Description", 6.7, "2015-06-12", "false"),
            new MovieList("140607", "/fYzpM9GmpBlIC893fNjoWCwE24H.jpg", "Star Wars: The Force Awakens", "The Description", 7.85, "2015-12-18", "false"),
            new MovieList("135397", "/jcUXVtJ6s0NG0EaxllQCAUtqdr0.jpg", "Jurassic World", "The Description", 6.7, "2015-06-12", "false"),
            new MovieList("140607", "/fYzpM9GmpBlIC893fNjoWCwE24H.jpg", "Star Wars: The Force Awakens", "The Description", 7.85, "2015-12-18", "false"),
            new MovieList("135397", "/jcUXVtJ6s0NG0EaxllQCAUtqdr0.jpg", "Jurassic World", "The Description", 6.7, "2015-06-12", "false"),
            new MovieList("140607", "/fYzpM9GmpBlIC893fNjoWCwE24H.jpg", "Star Wars: The Force Awakens", "The Description", 7.85, "2015-12-18", "false"),
            new MovieList("135397", "/jcUXVtJ6s0NG0EaxllQCAUtqdr0.jpg", "Jurassic World", "The Description", 6.7, "2015-06-12", "false"),
            new MovieList("140607", "/fYzpM9GmpBlIC893fNjoWCwE24H.jpg", "Star Wars: The Force Awakens", "The Description", 7.85, "2015-12-18", "false"),
            new MovieList("135397", "/jcUXVtJ6s0NG0EaxllQCAUtqdr0.jpg", "Jurassic World", "The Description", 6.7, "2015-06-12", "false"),
    };
    private ArrayList<MovieList> movieList;


    public AllMovieGridViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null || !savedInstanceState.containsKey("movie")){
            movieList = new ArrayList<>(Arrays.asList(mMovieLists));
        }else{
            movieList = savedInstanceState.getParcelableArrayList("movie");
        }
        //Allow fragment to handle menu events
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movie", movieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_gridview, container, false);

        mMovieAdapter = new MovieAdapter(getActivity(), Arrays.asList(mMovieLists));

        //Get a reference to the GridView and attach the adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setAdapter(mMovieAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieList movie = mMovieAdapter.getItem(position);
                //Toast.makeText(getActivity(), forecast, Toast.LENGTH_SHORT).show();
                Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putParcelable("movie",movie);
                detailIntent.putExtras(mBundle);
                startActivity(detailIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.moviefragment, menu);
    }

    /*
     * function to get the sort preference
     * and update the gridView via the FetchMovieTask
     */
    private void updateMovies(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort = prefs.getString(getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_default));

        FetchMovieTask movieTask = new FetchMovieTask();
        movieTask.execute(sort);
    }

    @Override
    public void onStart(){
        super.onStart();
        updateMovies();
    }

    /*
    * Async gets weather data from OpenWeatherMap.org
     */
    public class FetchMovieTask extends AsyncTask<String, Void, MovieList[]> {
        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        /**
         * Connect data to the Adapter
         */
        protected void onPostExecute(MovieList[] result) {
            if(result != null){
                    mMovieAdapter.notifyDataSetChanged();
            }
        }

        /**
         * Take the String representing the movie details in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         * <p/>
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private MovieList[] getMovieDataFromJson(String movieJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String TMDB_LIST = "results";
            final String TMDB_ID = "id";
            final String TMDB_POSTER = "poster_path";
            final String TMDB_NAME = "original_title";
            final String TMDB_SYNOPSIS = "overview";
            final String TMDB_USER_RATING = "vote_average";
            final String TMDB_RELEASE_DATE = "release_date";
            final String TMDB_ON_VIDEO = "video";

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(TMDB_LIST);

            for (int i = 0; i < movieArray.length(); i++) {
                // Get the JSON object representing the movie
                JSONObject movie = movieArray.getJSONObject(i);
                String id = movie.getString(TMDB_ID);
                String poster = TMDB_POSTER_URL + movie.getString(TMDB_POSTER);
                String name = movie.getString(TMDB_NAME);
                String synopsis = movie.getString(TMDB_SYNOPSIS);
                Double user_rating = movie.getDouble(TMDB_USER_RATING);
                String release_date = movie.getString(TMDB_RELEASE_DATE);
                String on_video = movie.getString(TMDB_ON_VIDEO);

                //for logging purposes
                //String mLog = id+" "+poster+" "+name+" "+synopsis+" "+user_rating+" "+release_date+" "+on_video;
                //Log.v(LOG_TAG,mLog);

                mMovieLists[i] = (new MovieList(id, poster, name, synopsis, user_rating, release_date, on_video));
            }

            return mMovieLists;

        }

        @Override
        protected MovieList[] doInBackground(String... params) {
            // If there's no zip code, there's nothing to look up.  Verify size of params.
            if (params.length == 0) {
                return null;
            }
            GetTMDBData mGetTMDBData = new GetTMDBData();
            String moviesJsonStr = mGetTMDBData.returnData(params);

            try {
                return getMovieDataFromJson(moviesJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error getting movie data from JSON", e);
            }


            return null;
        }


    }

}
