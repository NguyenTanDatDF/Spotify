package hcmute.edu.vn.spotify.Adapter;

import android.content.Context;
import android.content.Intent;
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

    private Context mContext;
    private List<Album> albumList;

    public AlbumAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Album> list)
    {
        this.albumList = list;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.AlbumViewHolder holder, int position) {
        Album album = albumList.get(position);
        if(album == null)
        {
            return;
        }
        else {
            Glide.with(mContext).load(album.getImageUrl()).into(holder.imgUser);
            holder.tvName.setText(album.getName());
            holder.tvDescription.setText(album.getDescription());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent album_music = new Intent(mContext, AlbumMusicActivity.class);
                    mContext.startActivity(album_music);
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        if(albumList != null)
        {
            return albumList.size();
        }
        return 0;
    }

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

            imgUser = itemView.findViewById(R.id.img_user);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);

        }
    }

}
