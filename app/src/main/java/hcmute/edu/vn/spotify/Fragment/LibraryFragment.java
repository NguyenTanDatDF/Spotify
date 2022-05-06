package hcmute.edu.vn.spotify.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.edu.vn.spotify.Activity.SettingActivity;
import hcmute.edu.vn.spotify.Activity.UserActivity;
import hcmute.edu.vn.spotify.Adapter.AlbumAdapter;
import hcmute.edu.vn.spotify.Adapter.ArtistAdapter;
import hcmute.edu.vn.spotify.Adapter.PlaylistVerticalAdapter;
import hcmute.edu.vn.spotify.Model.Artist;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.R;

public class LibraryFragment extends Fragment {

    RecyclerView rcvPlaylist;
    RecyclerView rcvArtist;
    CircleImageView avatarIv;
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

        return view;
    }
    private List<Playlist> getListPlaylist() {
        List<Playlist> list = new ArrayList<>();

        list.add(new Playlist(R.drawable.playlist, "My playlist", "Hoan"));
        list.add(new Playlist(R.drawable.album, "My first play", "Tien"));
        list.add(new Playlist(R.drawable.album1, "dancin", "Dat"));
        list.add(new Playlist(R.drawable.album2, "EDM", "Luan"));
        list.add(new Playlist(R.drawable.playlist, "My playlist", "Hoan"));
        list.add(new Playlist(R.drawable.album, "My first play", "Tien"));
        list.add(new Playlist(R.drawable.album1, "dancin", "Dat"));
        list.add(new Playlist(R.drawable.album2, "EDM", "Luan"));
        list.add(new Playlist(R.drawable.playlist, "My playlist", "Hoan"));
        list.add(new Playlist(R.drawable.album, "My first play", "Tien"));
        list.add(new Playlist(R.drawable.album1, "dancin", "Dat"));
        list.add(new Playlist(R.drawable.album2, "EDM", "Luan"));
        return list;
    }

    private List<Artist> getListArtist() {
        List<Artist> list = new ArrayList<>();

        list.add(new Artist(R.drawable.cardi, "Cardi A"));
        list.add(new Artist(R.drawable.cardi, "Cardi B"));
        list.add(new Artist(R.drawable.cardi, "Cardi C"));
        list.add(new Artist(R.drawable.cardi, "Cardi D"));
        list.add(new Artist(R.drawable.cardi, "Cardi E"));
        list.add(new Artist(R.drawable.cardi, "Cardi F"));
        list.add(new Artist(R.drawable.cardi, "Cardi G"));
        list.add(new Artist(R.drawable.cardi, "Cardi H"));

        return list;
    }
}