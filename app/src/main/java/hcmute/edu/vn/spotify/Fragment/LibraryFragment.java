package hcmute.edu.vn.spotify.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.edu.vn.spotify.Activity.NewPlaylistActivity;
import hcmute.edu.vn.spotify.Activity.SettingActivity;
import hcmute.edu.vn.spotify.Activity.UserActivity;
import hcmute.edu.vn.spotify.Adapter.AlbumAdapter;
import hcmute.edu.vn.spotify.Adapter.ArtistAdapter;
import hcmute.edu.vn.spotify.Adapter.PlaylistVerticalAdapter;
import hcmute.edu.vn.spotify.Database.DAOArtist;
import hcmute.edu.vn.spotify.Database.DAOPlaylist;
import hcmute.edu.vn.spotify.Model.Artist;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.R;

public class LibraryFragment extends Fragment {

    RecyclerView rcvPlaylist;
    RecyclerView rcvArtist;
    CircleImageView avatarIv;
    ImageView addPlaylistIv;
    private PlaylistVerticalAdapter playlistVerticalAdapter;
    private ArtistAdapter artistAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        //set data for playlist
        rcvPlaylist = view.findViewById(R.id.fragmentLibrary_playlistRv);
        playlistVerticalAdapter = new PlaylistVerticalAdapter((getActivity()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL, false);
        rcvPlaylist.setLayoutManager(gridLayoutManager);
        playlistVerticalAdapter.setData(getListPlaylist());
        rcvPlaylist.setAdapter(playlistVerticalAdapter);

        //set data for artist
        rcvArtist = view.findViewById(R.id.fragmentLibrary_artistRv);
        artistAdapter = new ArtistAdapter((getActivity()));
        GridLayoutManager gridArtistayoutManager = new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL, false);
        rcvArtist.setLayoutManager(gridArtistayoutManager);
        artistAdapter.setData(getListArtist());
        rcvArtist.setAdapter(artistAdapter);

        //Click on user detail information
        avatarIv = view.findViewById(R.id.fragmentLibrary_avatarIv);
        avatarIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent user_activity = new Intent(getActivity(), SettingActivity.class);
                startActivity(user_activity);
            }
        });

        addPlaylistIv = view.findViewById(R.id.fragmentLibrary_addPlaylistIv);
        addPlaylistIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPlaylist_activity = new Intent(getActivity(), NewPlaylistActivity.class);
                startActivity(addPlaylist_activity);
            }
        });

        return view;
    }
    private List<Playlist> getListPlaylist() {
        List<Playlist> list = new ArrayList<>();

        DAOPlaylist daoPlaylist = new DAOPlaylist();
        daoPlaylist.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Playlist playlist = data.getValue(Playlist.class);
                    list.add(playlist);
                    String key = data.getKey();
                    playlist.setKey(key);
                }
                playlistVerticalAdapter.setData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }

    private List<Artist> getListArtist() {
        List<Artist> list = new ArrayList<>();

        DAOArtist daoArtist = new DAOArtist();
        daoArtist.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Artist artist = data.getValue(Artist.class);
                    list.add(artist);
                    String key = data.getKey();
                    artist.setKey(key);
                }
                artistAdapter.setData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }
}