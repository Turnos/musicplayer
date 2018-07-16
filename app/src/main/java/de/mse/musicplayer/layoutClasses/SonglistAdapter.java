package de.mse.musicplayer.layoutClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.mse.musicplayer.ListAdministration.Song;
import de.mse.musicplayer.R;

public class SonglistAdapter extends ArrayAdapter<Song> {

    private Context context;
    private ArrayList<Song> songlist;

    public SonglistAdapter(Context context, ArrayList<Song> songlist){
        super(context,0,  songlist);
        this.context = context;
        this.songlist = songlist;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.listview_songlist, parent, false);
        }
        TextView textView = listItem.findViewById(R.id.textview_song);
        Song song = songlist.get(position);
        String entryTitle = song.getArtist() + " - " + song.getTitle();
        textView.setText(entryTitle);
        return listItem;
    }
}
