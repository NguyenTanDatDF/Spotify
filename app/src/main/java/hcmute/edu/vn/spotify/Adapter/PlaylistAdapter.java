package hcmute.edu.vn.spotify.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import hcmute.edu.vn.spotify.Activity.ArtistMusicActivity;
import hcmute.edu.vn.spotify.Activity.PlaylistMusicActivity;
import hcmute.edu.vn.spotify.R;
import java.util.List;

import hcmute.edu.vn.spotify.Model.Playlist;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    // playlist adapter context
    private Context pContext;
    //defined list playlist
    private List<Playlist> pPlaylist;

    public PlaylistAdapter(Context pContext) {
        this.pContext = pContext;
    }

    //Set data for playlist
    public void setData (List<Playlist> list) {
        this.pPlaylist = list;
        notifyDataSetChanged();
    }

    //Create view holder
    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_playlist_horizontal, parent, false);
        return new PlaylistViewHolder(view);
    }

    //Create bind view holder
    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        // get playlist defined pos
        Playlist playlist = pPlaylist.get(position);
        if(playlist == null){
            return;
        }
        //Set information
        Glide.with(pContext).load(playlist.getpUrl()).into(holder.playlistImage);
        holder.playlistName.setText(playlist.getpName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create intent to move to another activity
                Intent playlist_music = new Intent(pContext, PlaylistMusicActivity.class);
                //Create bundle to send playlist's information to next activity
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_playlist", playlist);
                playlist_music.putExtras(bundle);
                //Start activity
                pContext.startActivity(playlist_music);
            }
        });
    }

    //Get list number of item
    @Override
    public int getItemCount() {
        if (pPlaylist != null) {
            return pPlaylist.size();
        }
        return 0;
    }

    //Playlist view holder
    public class PlaylistViewHolder extends RecyclerView.ViewHolder {

        private ImageView playlistImage;
        private TextView playlistName;
        private TextView playlistCreator;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistImage = itemView.findViewById(R.id.componentPlaylistHorizontal_imageIv);
            playlistName = itemView.findViewById(R.id.componentPlaylistHorizontal_nameTv);
            playlistCreator = itemView.findViewById(R.id.componentPlaylistHorizontal_creatorTv);

        }
    }
}
