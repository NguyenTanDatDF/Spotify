package hcmute.edu.vn.spotify.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import hcmute.edu.vn.spotify.Activity.ArtistMusicActivity;
import hcmute.edu.vn.spotify.Activity.MusicPlaylistTrackActivity;
import hcmute.edu.vn.spotify.Activity.PlaylistMusicActivity;
import hcmute.edu.vn.spotify.Model.MusicPlaylist;
import hcmute.edu.vn.spotify.Model.PlaylistTrack;
import hcmute.edu.vn.spotify.R;

public class ListAdapter extends  RecyclerView.Adapter<ListAdapter.ListViewHolder>{


    private Context pContext;
    private List<MusicPlaylist> pMusicPlaylist;

    public ListAdapter(Context pContext){
        this.pContext = pContext;
    }

    public void setData (List<MusicPlaylist> list) {
        this.pMusicPlaylist = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_list, parent, false);
        return new ListAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        MusicPlaylist musicPlaylist = pMusicPlaylist.get(position);
        if(musicPlaylist == null){
            return;
        }
        Glide.with(pContext).load(musicPlaylist.getlImageUrl()).into(holder.listImage);
        holder.listName.setText(musicPlaylist.getlName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent music_playlist = new Intent(pContext, MusicPlaylistTrackActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_music_playlist", musicPlaylist);
                music_playlist.putExtras(bundle);
                pContext.startActivity(music_playlist);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (pMusicPlaylist != null) {
            return pMusicPlaylist.size();
        }
        return 0;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private ImageView listImage;
        private TextView listName;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            listImage = itemView.findViewById(R.id.componentList_imageIv);
            listName = itemView.findViewById(R.id.componentList_nameTv);
        }
    }

}
