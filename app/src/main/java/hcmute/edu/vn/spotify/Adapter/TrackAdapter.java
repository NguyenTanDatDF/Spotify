package hcmute.edu.vn.spotify.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.edu.vn.spotify.Model.Topic;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder >{

    private Context pContext;
    private List<Track> pTrack;

    public TrackAdapter(Context pContext){
        this.pContext = pContext;
    }

    public void setData (List<Track> list) {
        this.pTrack = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_music, parent, false);
        return new TrackAdapter.TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        Track track = pTrack.get(position);
        if(track == null){
            return;
        }
        holder.tImage.setImageResource(track.gettId());
        holder.tName.setText(track.gettName());
        holder.tListens.setText(String.valueOf(track.gettListens()));
    }

    @Override
    public int getItemCount() {
        if (pTrack != null) {
            return pTrack.size();
        }
        return 0;
    }

    public class TrackViewHolder extends RecyclerView.ViewHolder {

        private ImageView tImage;
        private TextView tName;
        private TextView tListens;
        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            tImage = itemView.findViewById(R.id.componentMusic_imageIv);
            tName = itemView.findViewById(R.id.componentMusic_songTv);
            tListens = itemView.findViewById(R.id.componentMusic_listensTv);
        }
    }
}
