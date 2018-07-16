package de.mse.musicplayer.ListAdministration;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable{
    private String artist;
    private String title;
    private String url;

    public Song (String artist, String title, String url){
        this.artist = artist;
        this.title = title;
        this.url = url;
    }

    private Song(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        this.artist = data[0];
        this.title = data[1];
        this.url = data[2];
    }

    @Override
    public String toString() {
        return artist + " - " + title;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        //Must be included, not needed.
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.artist, this.title, this.url});
    }

    public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>(){
        @Override
        public Song createFromParcel(Parcel source) {
            return new Song(source); //using Constructor: Song(Parcel in)
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

}
