package de.mse.musicplayer.layoutClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.mse.musicplayer.ListAdministration.Playlist;
import de.mse.musicplayer.R;

import static android.content.ContentValues.TAG;

public class PlaylistAdapter extends ArrayAdapter<Playlist>{
    private Context mContext;
    private ArrayList <Playlist> playlists;

    public PlaylistAdapter(@NonNull Context context, ArrayList<Playlist> list){
        super(context, 0, list);
        mContext = context;
        playlists = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.listview_listitem, parent, false);
        }
        TextView playlistTitle = listItem.findViewById(R.id.lv_playlisttitle);

        Playlist playlist = playlists.get(position);
        Log.d(TAG, "getView: " + playlist.getPlaylistName());
        playlistTitle.setText(playlist.getPlaylistName());

        return listItem;
    }
}
