package de.mse.musicplayer.layoutClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import de.mse.musicplayer.ListAdministration.Song;
import de.mse.musicplayer.R;

public class EditPlaylistAdapter extends ArrayAdapter<Song> {
    private static final String TAG = "EditPlaylistAdapter";
    private Context mContext;
    private ArrayList <Song> audioList; //this list represents all Songs in the storage
    private ArrayList <Song> playlist; //this list represents the desired playlist

    public EditPlaylistAdapter(@NonNull Context context, ArrayList<Song> audioList) {
        super(context, 0, audioList);
        this.mContext = context;
        this.audioList = audioList;
        this.playlist = new ArrayList<>();
    }

    public ArrayList<Song> getPlaylist() {
        return playlist;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.listview_listitem_editplaylist, parent, false);
        }
        TextView songTitle = listItem.findViewById(R.id.textview_songdetails);
        final CheckBox checkBox = listItem.findViewById(R.id.checkbox_editplaylist);

        final Song song = audioList.get(position);
        songTitle.setText(song.toString());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkBox.isChecked()){
                    //remove song from playlist / unchecking
                    playlist.remove(audioList.get(position));
                    Log.d(TAG, "getView: Removing " + song.toString() + " Playlist length is " + playlist.size());
                } else {
                    //add song to playlist / checking
                    playlist.add(audioList.get(position));
                    Log.d(TAG, "getView: Adding " + song.toString() + " Playlist length is " + playlist.size());
                }
            }
        });
        return listItem;
    }
}
