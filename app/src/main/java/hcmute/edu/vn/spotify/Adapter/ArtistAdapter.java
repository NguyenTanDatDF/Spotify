package hcmute.edu.vn.spotify.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import hcmute.edu.vn.spotify.Model.Artist;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.R;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>{
    private Context pContext;
    private List<Artist> pArtist;
    public ArtistAdapter(Context pContext) {
        this.pContext = pContext;
    }

    public void setData (List<Artist> list) {
        this.pArtist = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_artist, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Artist artist = pArtist.get(position);
        if(artist == null){
            return;
        }
        Glide.with(pContext).load(artist.getImageArtist()).into(holder.artistImage);
        holder.artistName.setText(artist.getNameArtist());
    }

    @Override
    public int getItemCount() {
        if (pArtist != null) {
            return pArtist.size();
        }
        return 0;
    }

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
