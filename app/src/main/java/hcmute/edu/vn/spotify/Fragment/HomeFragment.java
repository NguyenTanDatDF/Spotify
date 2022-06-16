package hcmute.edu.vn.spotify.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hcmute.edu.vn.spotify.Activity.NotificationsActivity;
import hcmute.edu.vn.spotify.Activity.SettingActivity;
import hcmute.edu.vn.spotify.Adapter.AlbumAdapter;
import hcmute.edu.vn.spotify.Adapter.ArtistAdapter;
import hcmute.edu.vn.spotify.Adapter.ListAdapter;
import hcmute.edu.vn.spotify.Adapter.PlaylistAdapter;
import hcmute.edu.vn.spotify.Adapter.PlaylistVerticalAdapter;
import hcmute.edu.vn.spotify.Database.DAOAlbum;
import hcmute.edu.vn.spotify.Database.DAOArtist;
import hcmute.edu.vn.spotify.Database.DAOMusicPlaylist;
import hcmute.edu.vn.spotify.Database.DAOPlaylist;
import hcmute.edu.vn.spotify.Model.Album;
import hcmute.edu.vn.spotify.Model.Artist;
import hcmute.edu.vn.spotify.Model.MusicPlaylist;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.Model.Topic;
import hcmute.edu.vn.spotify.Model.User;
import hcmute.edu.vn.spotify.R;
import hcmute.edu.vn.spotify.Service.ThreadSafeLazyUserSingleton;


public class HomeFragment extends Fragment {

    //Text variables
    TextView welcome;
    TextView album1;
    TextView album2;
    TextView album3;
    // Image view
    ImageView settingIv;
    ImageView notificationIv;

    //declare adapter for album
    RecyclerView rcvUser;
    private AlbumAdapter userAdapter;

    //declare adapter for playlist
    RecyclerView rcvPlaylist;
    private PlaylistVerticalAdapter playlistAdapter;

    //declare adapter for artist
    RecyclerView rcvArtist;
    private ArtistAdapter artistAdapter;

    //declare adapter for list music
    RecyclerView rcvListMusic;
    private ListAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Set data for recycle view
        setData(view);
        setArtistData(view);
        setPlaylistData(view);
        setMusicPlaylistData(view);
        // Move to another activity
        //go to setting activity
        settingIv = view.findViewById(R.id.icon_setting);
        settingIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent user_activity = new Intent(getActivity(), SettingActivity.class);
                startActivity(user_activity);
            }
        });

        //go to notifications activity
        notificationIv = view.findViewById(R.id.icon_notify);
        notificationIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notification_activity = new Intent(getActivity(), NotificationsActivity.class);
                startActivity(notification_activity);
            }
        });

        //Set welcome text
        Calendar now = Calendar.getInstance();
        int hour = now.get((Calendar.HOUR_OF_DAY));
        welcome = view.findViewById(R.id.welcome);
        if(hour <= 11) welcome.setText("Chào buổi sáng");
        else if (hour <= 17) welcome.setText("Chào buổi chiều");
        else welcome.setText("Chào buổi tối");

        return view;
    }

    //Set data for recycle views
    private void setData(View view) {
        //set data for album list
        rcvUser = view.findViewById(R.id.recycleView);
        userAdapter = new AlbumAdapter((getActivity()));
        userAdapter.setData(getListAlbum());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        rcvUser.setAdapter(userAdapter);
        rcvUser.setLayoutManager(linearLayoutManager);
    }
    //Set data for playlist
    private void setPlaylistData(View view){
        //get userid
        User user = new User();
        ThreadSafeLazyUserSingleton singleton = ThreadSafeLazyUserSingleton.getInstance(user);
        user = singleton.user;

        //Set data
        rcvPlaylist = view.findViewById(R.id.recycleView1);
        playlistAdapter = new PlaylistVerticalAdapter((getActivity()));
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        playlistAdapter.setData(getListPlaylist(user.getUserId().trim()));
        rcvPlaylist.setAdapter(playlistAdapter);
        rcvPlaylist.setLayoutManager(linearLayoutManager1);
    }
    //Set data for artist
    private void setArtistData(View view){
        rcvArtist = view.findViewById(R.id.recycleView2);
        artistAdapter = new ArtistAdapter((getActivity()));
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        artistAdapter.setData(getListArtist());
        rcvArtist.setAdapter(artistAdapter);
        rcvArtist.setLayoutManager(linearLayoutManager2);
    }

    private void setMusicPlaylistData(View view){
        //set data for music list
        rcvListMusic = view.findViewById(R.id.fragmentHome_listMusicRcv);
        listAdapter = new ListAdapter((getActivity()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL, false);
        rcvListMusic.setLayoutManager(gridLayoutManager);
        listAdapter.setData(getListMusicPlaylist());
        rcvListMusic.setAdapter(listAdapter);
    }

    //Get album list from firebase to show on recycle view
    private List<Album> getListAlbum()
    {
        List<Album> list = new ArrayList<>();
        List<Artist> artistsList = getListArtist();
        DAOAlbum daoAlbum = new DAOAlbum();
        daoAlbum.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Album album = data.getValue(Album.class);
                    String artistId = album.getArtistId().trim();
                    for(Artist artist: artistsList){
                        String temp = artist.getIdArtist().trim();
                        if(artistId.equals(temp)){
                            album.setArtistName(artist.nameArtist);
                            album.setKey(data.getKey());
                            list.add(album);
                        }
                    }
                }
                userAdapter.setData(list);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {            }
        });

        return list;
    }

    //Get artist list from firebase
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
    //Get playlist from firebase
    private List<Playlist> getListPlaylist(String userId) {
        List<Playlist> list = new ArrayList<>();

        DAOPlaylist daoPlaylist = new DAOPlaylist();
        daoPlaylist.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Playlist playlist = data.getValue(Playlist.class);
                    if(playlist.getuID().trim().equals(userId)){
                        list.add(playlist);
                        String key = data.getKey();
                        playlist.setKey(key);
                    }
                }
                playlistAdapter.setData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }
    //Get list music playlist
    private List<MusicPlaylist> getListMusicPlaylist() {
        List<MusicPlaylist> list = new ArrayList<>();
        DAOMusicPlaylist daoMusicPlaylist = new DAOMusicPlaylist();
        daoMusicPlaylist.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    MusicPlaylist musicPlaylist = data.getValue(MusicPlaylist.class);
                    list.add(musicPlaylist);
                    String key = data.getKey();
                    musicPlaylist.setKey(key);
                }
                listAdapter.setData(list);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }


}