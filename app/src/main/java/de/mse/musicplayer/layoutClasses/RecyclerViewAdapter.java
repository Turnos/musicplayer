package de.mse.musicplayer.layoutClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import de.mse.musicplayer.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> listOfNames;
    //private ArrayList<String> listOfImages = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<String> listOfNames) {
        this.listOfNames = listOfNames;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: item is added to the list.");
        holder.entryName.setText(listOfNames.get(position));
        holder.layoutRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Entry was clicked on: " + listOfNames.get(position));
                //TODO Action for list_item
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView entryName;
        RelativeLayout layoutRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            entryName = itemView.findViewById(R.id.txt_artist);
            layoutRecyclerView = itemView.findViewById(R.id.layout_RecyclerView);
        }
    }
}
