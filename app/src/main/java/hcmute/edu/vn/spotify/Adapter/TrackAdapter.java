package hcmute.edu.vn.spotify.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.MediaItem;

import java.util.List;

import hcmute.edu.vn.spotify.Activity.MainActivity;
import hcmute.edu.vn.spotify.Activity.NewPlaylistActivity;
import hcmute.edu.vn.spotify.Activity.SigninActivity;
import hcmute.edu.vn.spotify.Database.DAOPlayListTrack;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.Model.Topic;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;
import hcmute.edu.vn.spotify.Service.MyService;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder >{

    private Context pContext;
    private List<Track> pTrack;
    DAOPlayListTrack daoPlayListTrack = new DAOPlayListTrack();

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
        else{
            Glide.with(pContext).load(track.getImage()).into(holder.tImage);
            holder.tName.setText(track.getName());
            holder.tListens.setText(String.valueOf(track.gettListens()));
            holder.tCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    daoPlayListTrack.removePlaylistTrack(track.getKey()).addOnSuccessListener(suc -> {
                        Toast.makeText(pContext, "Remove track successfully!", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(err -> {
                        Toast.makeText(pContext, "Can't remove this track!", Toast.LENGTH_SHORT).show();
                    });
                }
            });

            holder.trackLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.player.stop(true);
                    MainActivity.track = track;
                   // MainActivity.playlist.clear();
                    MainActivity.name_track.setText( track.getName());
                    MainActivity.nameArtist_track.setText( track.gettArtist().getNameArtist());
                    MainActivity.img_track.setImageBitmap(MyService.getBitmapFromURL(track.getImage()));
                    MainActivity.typePlaying = "single";
                    PlayMedia(MainActivity.track);
                }
            });
        }
    }
    public void PlayMedia(Track track)
    {
        if(MainActivity.player.isPlaying())
        {
            //MainActivity.player.seekTo(MainActivity.player.getDuration()-100);
            MainActivity.player.stop(true);
        }

        Uri uriOfContentUrl = Uri.parse(track.getSource());
        MediaItem Item = MediaItem.fromUri(uriOfContentUrl);
        // Add the media items to be played.
        MainActivity.player.addMediaItem(Item);
        MainActivity.player.prepare();
        // Start the playback.
        MainActivity.player.play(); // start loading video and play it at the moment a chunk of it is available offline (start and play immediately)
        MainActivity.pvMain.setControllerShowTimeoutMs(0);
        MainActivity.pvMain.showController();
        MainActivity.pvMain.setControllerHideOnTouch(false);
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
        private ImageView tCancel;
        private ConstraintLayout trackLayout;
        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            tImage = itemView.findViewById(R.id.componentMusic_imageIv);
            tName = itemView.findViewById(R.id.componentMusic_songTv);
            tListens = itemView.findViewById(R.id.componentMusic_listensTv);
            tCancel = itemView.findViewById(R.id.music_clear_btn);
            trackLayout = itemView.findViewById(R.id.trackLayout);
        }
    }
}
