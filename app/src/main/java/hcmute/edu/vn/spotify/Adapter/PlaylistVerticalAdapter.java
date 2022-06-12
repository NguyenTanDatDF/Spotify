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

import java.util.List;

import hcmute.edu.vn.spotify.Activity.PlaylistMusicActivity;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.R;

public class PlaylistVerticalAdapter extends RecyclerView.Adapter<PlaylistVerticalAdapter.PlaylistVerticalViewHolder> {

    private Context pContext;
    private List<Playlist> pPlaylist;

    public PlaylistVerticalAdapter(Context pContext) {
        this.pContext = pContext;
    }

    public void setData (List<Playlist> list) {
        this.pPlaylist = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaylistVerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_playlist, parent, false);
        return new PlaylistVerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistVerticalViewHolder holder, int position) {
        Playlist playlist = pPlaylist.get(position);
        if(playlist == null){
            return;
        }
        Glide.with(pContext).load(playlist.getpUrl()).into(holder.playlistImage);
        holder.playlistName.setText(playlist.getpName());
        holder.playlistCreator.setText(playlist.getuName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playlist_music = new Intent(pContext, PlaylistMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_playlist", playlist);
                playlist_music.putExtras(bundle);
                pContext.startActivity(playlist_music);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (pPlaylist != null) {
            return pPlaylist.size();
        }
        return 0;
    }

    public class PlaylistVerticalViewHolder extends RecyclerView.ViewHolder {

        private ImageView playlistImage;
        private TextView playlistName;
        private TextView playlistCreator;

        public PlaylistVerticalViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistImage = itemView.findViewById(R.id.componentPlayList_imageIv);
            playlistName = itemView.findViewById(R.id.componentPlayList_playlistNameTv);
            playlistCreator = itemView.findViewById(R.id.componentPlayList_creatorTv);
        }
    }
}
