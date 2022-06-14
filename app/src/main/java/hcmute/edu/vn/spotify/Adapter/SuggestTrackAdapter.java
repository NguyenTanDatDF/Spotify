package hcmute.edu.vn.spotify.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hcmute.edu.vn.spotify.Activity.NewPlaylistActivity;
import hcmute.edu.vn.spotify.Activity.PlaylistMusicActivity;
import hcmute.edu.vn.spotify.Activity.SigninActivity;
import hcmute.edu.vn.spotify.Database.DAOPlayListTrack;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.Model.PlaylistTrack;
import hcmute.edu.vn.spotify.Model.Topic;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;

public class SuggestTrackAdapter extends RecyclerView.Adapter<SuggestTrackAdapter.TrackViewHolder > implements Filterable {

    private Context pContext;
    private List<Track> pTrack;
    private List<Track> pTrackOld;
    DAOPlayListTrack daoPlayListTrack = new DAOPlayListTrack();
    List<PlaylistTrack> list = getPlaylistTrack();

    public SuggestTrackAdapter(Context pContext){
        this.pContext = pContext;
    }

    public void setData (List<Track> list) {
        this.pTrack = list;
        this.pTrackOld = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_suggest_track, parent, false);
        return new SuggestTrackAdapter.TrackViewHolder(view);
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
            holder.tAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PlaylistTrack newTrack = new PlaylistTrack(randomId() ,track.getTrackId().trim(), PlaylistMusicActivity.definedPlaylist.getPlaylistId().trim());
                    daoPlayListTrack.addNewPlaylistTrack(newTrack).addOnSuccessListener(suc -> {
                        Toast.makeText(pContext, "Add track to playlist successfully!", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(err -> {
                        Toast.makeText(pContext, "Can't add this track!", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }
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
        private ImageView tAdd;
        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            tImage = itemView.findViewById(R.id.componentSuggest_imageIv);
            tName = itemView.findViewById(R.id.componentSuggest_songTv);
            tListens = itemView.findViewById(R.id.componentSuggest_listensTv);
            tAdd = itemView.findViewById(R.id.music_add_btn);
        }
    }

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
    public String randomId(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 25; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
