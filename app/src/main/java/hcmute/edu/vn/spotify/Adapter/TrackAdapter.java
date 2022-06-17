package hcmute.edu.vn.spotify.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.google.android.exoplayer2.MediaItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hcmute.edu.vn.spotify.Activity.MainActivity;
import hcmute.edu.vn.spotify.Activity.NewPlaylistActivity;
import hcmute.edu.vn.spotify.Activity.SigninActivity;
import hcmute.edu.vn.spotify.Database.DAOPlayListTrack;
import hcmute.edu.vn.spotify.Database.DAOTrack;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.Model.PlaylistTrack;
import hcmute.edu.vn.spotify.Model.Topic;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;
import hcmute.edu.vn.spotify.Service.MyService;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder > implements Filterable {
    // declare the context
    private Context pContext;
    //declare the list track
    private List<Track> pTrack;
    // declare the old list track to compare
    private List<Track> pTrackOld;
    // declare the dao object to get data
    DAOPlayListTrack daoPlayListTrack = new DAOPlayListTrack();
    // init the first dataa for playlist track
    List<PlaylistTrack> list = getPlaylistTrack();

    // pass the activity context to this adapter
    public TrackAdapter(Context pContext){
        this.pContext = pContext;
    }

     // set data for recyclerview, list track and old list track
    public void setData (List<Track> list) {
        this.pTrack = list;
        this.pTrackOld = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflater with component music to return a view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_music, parent, false);
        return new TrackAdapter.TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        // get the track in this position
        Track track = pTrack.get(position);
        // return if null
        if(track == null){
            return;
        }

        else{
            // set the track infomation
            Glide.with(pContext).load(track.getImage()).into(holder.tImage);
            holder.tName.setText(track.getName());
            holder.tListens.setText(String.valueOf(track.gettListens()) + " views");
            // remove if click on remove button
            holder.tCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(PlaylistTrack playlistTrack: list){
                        if(track.getTrackId().trim().equals(playlistTrack.getTrackId().trim())){
                                daoPlayListTrack.removePlaylistTrack(playlistTrack.getKey().trim()).addOnSuccessListener(suc -> {
                                Toast.makeText(pContext, "Remove track successfully!", Toast.LENGTH_SHORT).show();
                                }).addOnFailureListener(err -> {
                                Toast.makeText(pContext, "Can't remove this track!", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }
                }
            });
            // if click to the specify track, it will play this track and set some of global variable
            holder.trackLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // stop previous track
                    MainActivity.player.stop(true);
                    // set the current track
                    MainActivity.track = track;
                   // MainActivity.playlist.clear();
                    // set track information
                    MainActivity.name_track.setText( track.getName());
                    MainActivity.nameArtist_track.setText( track.gettArtist().getNameArtist());
                    MainActivity.img_track.setImageBitmap(MyService.getBitmapFromURL(track.getImage()));
                    // set mode of play is single
                    MainActivity.typePlaying = "single";
                    DAOTrack daoTrack = new DAOTrack();
                    // increase the view if play this track
                    daoTrack.databaseReference.child(MainActivity.track.getName().trim()).child("tListens").setValue(MainActivity.track.gettListens()+1);
                    // lunch to play this track
                    PlayMedia(MainActivity.track);
                }
            });
        }
    }
    // play a track
    public void PlayMedia(Track track)
    {
        // stop if player is playing
        if(MainActivity.player.isPlaying())
        {
            MainActivity.player.stop(true);
        }
        // create media item from url
        Uri uriOfContentUrl = Uri.parse(track.getSource());
        MediaItem Item = MediaItem.fromUri(uriOfContentUrl);
        // Add the media items to be played.
        MainActivity.player.addMediaItem(Item);
        // load the track
        MainActivity.player.prepare();
        // Start the playback.
        MainActivity.player.play();
        // configure the player
        // set always display
        MainActivity.pvMain.setControllerShowTimeoutMs(0);
        // show controller
        MainActivity.pvMain.showController();
        // show disable the hiding on touching
        MainActivity.pvMain.setControllerHideOnTouch(false);
    }

    //Limit the number of song that can be shown
    private final int limit = 5;
    @Override
    public int getItemCount() {
        if (pTrack.size() > limit) {
            return limit;
        }
        else return pTrack.size();
    }


    public class TrackViewHolder extends RecyclerView.ViewHolder {
        // declare the view
        private ImageView tImage;
        private TextView tName;
        private TextView tListens;
        private ImageView tCancel;
        private ConstraintLayout trackLayout;
        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            // mapping the view
            tImage = itemView.findViewById(R.id.componentMusic_imageIv);
            tName = itemView.findViewById(R.id.componentMusic_songTv);
            tListens = itemView.findViewById(R.id.componentMusic_listensTv);
            tCancel = itemView.findViewById(R.id.music_clear_btn);
            trackLayout = itemView.findViewById(R.id.trackLayout);
        }
    }

    // get all playlist track
    private List<PlaylistTrack> getPlaylistTrack()
    {
        List<PlaylistTrack> list = new ArrayList<>();
        DAOPlayListTrack daoPlayListTrack = new DAOPlayListTrack();
        daoPlayListTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot data: snapshot.getChildren()){
                    PlaylistTrack playlistTrack = data.getValue(PlaylistTrack.class);
                    list.add(playlistTrack);
                    String key = data.getKey();
                    playlistTrack.setKey(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return list;
    }


    // it will filter the data and reload to recyclerview in realtime
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchString = charSequence.toString();
                if(searchString.isEmpty()){
                    pTrack = pTrackOld;
                } else {
                    List<Track> list = new ArrayList<>();
                    for(Track track : pTrackOld){
                        if(track.getName().toLowerCase().contains(searchString.toLowerCase())){
                            list.add(track);
                        }
                    }

                    pTrack = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = pTrack;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                pTrack = (List<Track>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
