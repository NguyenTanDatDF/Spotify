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

import hcmute.edu.vn.spotify.Activity.ArtistMusicActivity;
import hcmute.edu.vn.spotify.Activity.TopicMusicActivity;
import hcmute.edu.vn.spotify.Model.Artist;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.R;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>{
    //Artist context
    private Context pContext;
    //Get list artist
    private List<Artist> pArtist;

    public ArtistAdapter(Context pContext) {
        this.pContext = pContext;
    }

    //Set data for artist list
    public void setData (List<Artist> list) {
        this.pArtist = list;
        notifyDataSetChanged();
    }

    //Create view holder
    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_artist, parent, false);
        return new ArtistViewHolder(view);
    }

    //Create bind view holder

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        //Get defined artist
        Artist artist = pArtist.get(position);
        if(artist == null){
            return;
        }
        Glide.with(pContext).load(artist.getImageArtist()).into(holder.artistImage);
        holder.artistName.setText(artist.getNameArtist());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create new intent when click to artist component
                Intent artist_music = new Intent(pContext, ArtistMusicActivity.class);
                //Create bundle to move artist's information to next activity
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_artist", artist);
                artist_music.putExtras(bundle);
                //Start activity
                pContext.startActivity(artist_music);
            }
        });
    }

    //Limit the number of song that can be shown
    private final int limit = 5;
    @Override
    public int getItemCount() {
        if (pArtist.size() > limit) {
            return pArtist.size();
        }
        else {
            return pArtist.size();
        }
    }

    //View holder
    public class ArtistViewHolder extends RecyclerView.ViewHolder {

        private ImageView artistImage;
        private TextView artistName;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            artistImage = itemView.findViewById(R.id.componentArtist_imageIv);
            artistName = itemView.findViewById(R.id.componentArtist_nameTv);
        }
    }
}
