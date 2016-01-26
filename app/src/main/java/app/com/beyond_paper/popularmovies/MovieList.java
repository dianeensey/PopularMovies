package app.com.beyond_paper.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class to hold the Movie Data
 * Based on https://github.com/udacity/android-custom-arrayadapter
 * Created by poornima-udacity on 6/26/15.
 */
public class MovieList implements Parcelable {
    public final static Parcelable.Creator<MovieList> CREATOR = new Parcelable.Creator<MovieList>(){
        @Override
        public MovieList createFromParcel(Parcel parcel){
                return new MovieList(parcel);
            }
        @Override
        public MovieList[] newArray(int i){
                return new MovieList[i];
            }
    };
    String id;
    String poster;
    String name;
    String synopsis;
    Double user_rating;
    String release_date;
    String on_video;

    public MovieList(String id, String poster, String name, String synopsis, Double user_rating, String release_date, String on_video)
    {
        this.id = id;
        this.poster = poster;
        this.name= name;
        this.synopsis = synopsis;
        this.user_rating = user_rating;
        this.release_date = release_date;
        this.on_video = on_video;
    }

    private MovieList(Parcel in){
        id = in.readString();
        poster = in.readString();
        name = in.readString();
        synopsis = in.readString();
        user_rating = in.readDouble();
        release_date = in.readString();
        on_video = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString(){return id+"|"+poster+"|"+name+"|"+synopsis+"|"+user_rating+"|"+release_date+"|"+on_video;}

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(poster);
        parcel.writeString(name);
        parcel.writeString(synopsis);
        parcel.writeDouble(user_rating);
        parcel.writeString(release_date);
        parcel.writeString(on_video);
    }
}