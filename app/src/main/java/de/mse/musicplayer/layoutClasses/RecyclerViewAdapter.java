package de.mse.musicplayer.layoutClasses;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import de.mse.musicplayer.ArtistSonglistActivity;
import de.mse.musicplayer.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> listOfArtistNames;
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<String> listOfArtistNames) {
        this.listOfArtistNames = listOfArtistNames;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: item is added to the list.");
        final String artistName = listOfArtistNames.get(position);
        holder.entryName.setText(artistName);
        holder.layoutRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switcher = new Intent(context, ArtistSonglistActivity.class);
                switcher.putExtra("Artist", artistName);
                context.startActivity(switcher);
                Log.d(TAG, "onClick: Entry was clicked on: " + artistName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfArtistNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView entryName;
        RelativeLayout layoutRecyclerView;

        ViewHolder(View itemView) {
            super(itemView);
            entryName = itemView.findViewById(R.id.txt_itemtext);
            layoutRecyclerView = itemView.findViewById(R.id.layout_RecyclerView);
        }
    }
}
