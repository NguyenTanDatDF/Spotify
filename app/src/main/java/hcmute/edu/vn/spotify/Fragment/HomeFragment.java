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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hcmute.edu.vn.spotify.Activity.NotificationsActivity;
import hcmute.edu.vn.spotify.Activity.SettingActivity;
import hcmute.edu.vn.spotify.Adapter.AlbumAdapter;
import hcmute.edu.vn.spotify.Adapter.ListAdapter;
import hcmute.edu.vn.spotify.Adapter.PlaylistAdapter;
import hcmute.edu.vn.spotify.Database.DAOAlbum;
import hcmute.edu.vn.spotify.Database.DAOArtist;
import hcmute.edu.vn.spotify.Model.Album;
import hcmute.edu.vn.spotify.Model.Artist;
import hcmute.edu.vn.spotify.Model.MusicPlaylist;
import hcmute.edu.vn.spotify.Model.Topic;
import hcmute.edu.vn.spotify.R;


public class HomeFragment extends Fragment {

    TextView welcome;
    TextView album1;
    TextView album2;
    TextView album3;
    RecyclerView rcvUser;
    ImageView settingIv;
    ImageView notificationIv;
    private AlbumAdapter userAdapter;

    RecyclerView rcvUser1;
    private AlbumAdapter userAdapter1;

    RecyclerView rcvUser2;
    private AlbumAdapter userAdapter2;

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

        setData(view);
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

        //Set album name
        album1 = view.findViewById(R.id.album1);
        album2 = view.findViewById(R.id.album2);
        album3 = view.findViewById(R.id.album3);
        album1.setText("Popular Albums");
        album2.setText("Only For You");
        album3.setText("Albums");

        //Set welcome text
        Calendar now = Calendar.getInstance();
        int hour = now.get((Calendar.HOUR_OF_DAY));
        welcome = view.findViewById(R.id.welcome);
        if(hour <= 11) welcome.setText("Chào buổi sáng");
        else if (hour <= 17) welcome.setText("Chào buổi chiều");
        else welcome.setText("Chào buổi tối");

        return view;
    }

    //Get album list from firebase to show on recycleview
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
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }

    //Get music playlist from firebase
    private List<MusicPlaylist> getListMusic()
    {
        List<MusicPlaylist> list = new ArrayList<>();
        list.add(new MusicPlaylist("https://image.thanhnien.vn/w1024/Uploaded/2022/wpdhnwejw/2022_03_18/img-8371-4267.jpeg", "Nghe chán nghe tiếp"));
        list.add(new MusicPlaylist("https://image.thanhnien.vn/w1024/Uploaded/2022/wpdhnwejw/2022_03_18/img-8371-4267.jpeg", "Nghe chán khỏi nghe"));
        list.add(new MusicPlaylist("https://image.thanhnien.vn/w1024/Uploaded/2022/wpdhnwejw/2022_03_18/img-8371-4267.jpeg", "Nghe chán thì thôi"));
        list.add(new MusicPlaylist("https://image.thanhnien.vn/w1024/Uploaded/2022/wpdhnwejw/2022_03_18/img-8371-4267.jpeg", "Nghe chán nghỉ"));
        list.add(new MusicPlaylist("https://image.thanhnien.vn/w1024/Uploaded/2022/wpdhnwejw/2022_03_18/img-8371-4267.jpeg", "Nghe chán nữa"));
        list.add(new MusicPlaylist("https://image.thanhnien.vn/w1024/Uploaded/2022/wpdhnwejw/2022_03_18/img-8371-4267.jpeg", "Nghe chán thì ngủ"));

        return list;
    }


    //Set data for recycle views
    public void setData(View view) {
        //set data for album list
        rcvUser = view.findViewById(R.id.recycleView);
        userAdapter = new AlbumAdapter((getActivity()));
        userAdapter.setData(getListAlbum());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        rcvUser.setAdapter(userAdapter);
        rcvUser.setLayoutManager(linearLayoutManager);

        rcvUser1 = view.findViewById(R.id.recycleView1);
        userAdapter1 = new AlbumAdapter((getActivity()));
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        userAdapter1.setData(getListAlbum());
        rcvUser1.setAdapter(userAdapter1);
        rcvUser1.setLayoutManager(linearLayoutManager1);

        rcvUser2 = view.findViewById(R.id.recycleView2);
        userAdapter2 = new AlbumAdapter((getActivity()));
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        userAdapter2.setData(getListAlbum());
        rcvUser2.setAdapter(userAdapter2);
        rcvUser2.setLayoutManager(linearLayoutManager2);

        //set data for music list
        rcvListMusic = view.findViewById(R.id.fragmentHome_listMusicRcv);
        listAdapter = new ListAdapter((getActivity()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL, false);
        rcvListMusic.setLayoutManager(gridLayoutManager);
        listAdapter.setData(getListMusic());
        rcvListMusic.setAdapter(listAdapter);
    }
}