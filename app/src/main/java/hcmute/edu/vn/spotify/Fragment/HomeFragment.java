package hcmute.edu.vn.spotify.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hcmute.edu.vn.spotify.Activity.SettingActivity;
import hcmute.edu.vn.spotify.Adapter.AlbumAdapter;
import hcmute.edu.vn.spotify.Adapter.ListAdapter;
import hcmute.edu.vn.spotify.Adapter.PlaylistAdapter;
import hcmute.edu.vn.spotify.Model.Album;
import hcmute.edu.vn.spotify.Model.MusicPlaylist;
import hcmute.edu.vn.spotify.R;


public class HomeFragment extends Fragment {

    TextView welcome;
    RecyclerView rcvUser;
    ImageView settingIv;
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
        //set data for album list
        rcvUser = view.findViewById(R.id.recycleView);
        userAdapter = new AlbumAdapter((getActivity()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        rcvUser.setLayoutManager(linearLayoutManager);
        userAdapter.setData(getListAlbum());
        rcvUser.setAdapter(userAdapter);

        rcvUser1 = view.findViewById(R.id.recycleView1);
        userAdapter1 = new AlbumAdapter((getActivity()));
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        rcvUser1.setLayoutManager(linearLayoutManager1);
        userAdapter1.setData(getListAlbum());
        rcvUser1.setAdapter(userAdapter1);

        rcvUser2 = view.findViewById(R.id.recycleView2);
        userAdapter2 = new AlbumAdapter((getActivity()));
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        rcvUser2.setLayoutManager(linearLayoutManager2);
        userAdapter2.setData(getListAlbum());
        rcvUser2.setAdapter(userAdapter2);


        //Set welcome text

        Calendar now = Calendar.getInstance();
        int hour = now.get((Calendar.HOUR_OF_DAY));

        welcome = view.findViewById(R.id.welcome);

        if(hour <= 11) welcome.setText("Chào buổi sáng");
        else if (hour <= 17) welcome.setText("Chào buổi chiều");
        else welcome.setText("Chào buổi tối");

        //set data for music list
        rcvListMusic = view.findViewById(R.id.fragmentHome_listMusicRcv);
        listAdapter = new ListAdapter((getActivity()));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL, false);
        rcvListMusic.setLayoutManager(gridLayoutManager);

        listAdapter.setData(getListMusic());
        rcvListMusic.setAdapter(listAdapter);

        //go to setting activity
        settingIv = view.findViewById(R.id.icon_setting);
        settingIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent user_activity = new Intent(getActivity(), SettingActivity.class);
                startActivity(user_activity);
            }
        });

        return view;
    }
    private List<Album> getListAlbum()
    {
        List<Album> list = new ArrayList<>();
        list.add(new Album("Chill" ,R.drawable.album1, "MCK, K-ICM, LowG"));
        list.add(new Album("Remix Tiktok" ,R.drawable.album2,"Nguyen Tan Dat, Cukak"));
        list.add(new Album("Bolero" ,R.drawable.album3,"Tran Dang Khoa"));
        list.add(new Album("Nonstop", R.drawable.album4, "Nguyen Le Minh Nhut"));
        list.add(new Album( "Piano",R.drawable.album5,"Ho Dang Tien" ));
        list.add(new Album("Guitar", R.drawable.album6, "Nguyen Thien Hoan"));

        return list;
    }
    private List<MusicPlaylist> getListMusic()
    {
        List<MusicPlaylist> list = new ArrayList<>();
        list.add(new MusicPlaylist(R.drawable.album1, "Nghe chán nghe tiếp"));
        list.add(new MusicPlaylist(R.drawable.album2, "Nghe chán khỏi nghe"));
        list.add(new MusicPlaylist(R.drawable.album3, "Nghe chán thì thôi"));
        list.add(new MusicPlaylist(R.drawable.album4, "Nghe chán nghỉ"));
        list.add(new MusicPlaylist(R.drawable.album5, "Nghe chán nữa"));
        list.add(new MusicPlaylist(R.drawable.album6, "Nghe chán thì ngủ"));

        return list;
    }


}