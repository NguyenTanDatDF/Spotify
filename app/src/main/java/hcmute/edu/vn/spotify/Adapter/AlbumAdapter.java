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

import hcmute.edu.vn.spotify.Activity.AlbumMusicActivity;
import hcmute.edu.vn.spotify.Model.Album;
import hcmute.edu.vn.spotify.R;
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>{

    //Context
    private Context mContext;
    //Get list album
    private List<Album> albumList;

    public AlbumAdapter(Context mContext) {
        this.mContext = mContext;
    }

    //Set data for list album
    public void setData(List<Album> list)
    {
        this.albumList = list;
        notifyDataSetChanged();
    }

    //Binding view
    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.AlbumViewHolder holder, int position) {
        //Get defined album
        Album album = albumList.get(position);
        if(album == null)
        {
            return;
        }
        else {
            //Set text, images for component
            Glide.with(mContext).load(album.getResourceId()).into(holder.imgUser);
            holder.tvName.setText(album.getName());
            holder.tvDescription.setText(album.getArtistName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //create intent
                    Intent album_music = new Intent(mContext, AlbumMusicActivity.class);
                    //Create bundle to send album's information to another activity
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("object_album", album);
                    album_music.putExtras(bundle);
                    mContext.startActivity(album_music);
                }
            });
        }
    }

    //Limit the album that shown (5)
    private final int limit = 5;
    @Override
    public int getItemCount() {

        if(albumList.size() > limit)
        {
            return limit;
        }else {
            return albumList.size();
        }
    }

    //Create view holder
    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);

        return new AlbumViewHolder(view);
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgUser;
        private TextView tvName;
        private TextView tvDescription;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);

            //Mapping
            imgUser = itemView.findViewById(R.id.img_user);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);

        }
    }

}
