package app.com.beyond_paper.popularmovies;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static app.com.beyond_paper.popularmovies.Constants.TMDB_API_KEY;

/**
 * Class to get data from themoviedb.com API
 * params[0] = TMDB sort parameter
 * params[1] = TMDB movie id
 * sourced from the Sunshine App
 */
public class GetTMDBData {
    private final String LOG_TAG = GetTMDBData.class.getSimpleName();

    public String returnData(String[] params){
        //Will contain the raw JSON response as string.
        String moviesJsonStr = null;

        //Verify there is something to look up
        if(params.length == 0){return null;}

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            //Construct the URL for The Movie Database query
            //Possible queries at https://www.themoviedb.org/documentation/api/discover
            final String SCHEME = "https";
            final String AUTH = "api.themoviedb.org";
            final String VERS = "3";
            final String ACTION = "discover";
            final String TYPE = "movie";
            final String SORT_PARAM = "sort_by";
            final String API_PARAM = "api_key";

            Uri.Builder builder = new Uri.Builder();
            //If param[0] is null and there is a param[1] we are looking
            //up a single movie
            if(params[0] == null && params[1] != null){
                builder.scheme(SCHEME)
                        .authority(AUTH)
                        .appendPath(VERS)
                        .appendPath(TYPE)
                        .appendPath(params[1])
                        .appendQueryParameter(API_PARAM, TMDB_API_KEY)
                        .build();
            }else {
                builder.scheme(SCHEME)
                        .authority(AUTH)
                        .appendPath(VERS)
                        .appendPath(ACTION)
                        .appendPath(TYPE)
                        .appendQueryParameter(API_PARAM, TMDB_API_KEY)
                        .appendQueryParameter(SORT_PARAM, params[0])
                        .build();
            }
            URL url = new URL(builder.toString());
            //Create the request to The Movie Database and open the connection
            //Taken from Sunshine app
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            moviesJsonStr = buffer.toString();

            return moviesJsonStr;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return null;
    }
}
