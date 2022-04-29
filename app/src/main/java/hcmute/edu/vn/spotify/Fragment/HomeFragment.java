package hcmute.edu.vn.spotify.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Adapter.AlbumAdapter;
import hcmute.edu.vn.spotify.Model.Album;
import hcmute.edu.vn.spotify.R;


public class HomeFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    RecyclerView rcvUser;
    private AlbumAdapter userAdapter;

    RecyclerView rcvUser1;
    private AlbumAdapter userAdapter1;

    RecyclerView rcvUser2;
    private AlbumAdapter userAdapter2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rcvUser = view.findViewById(R.id.recycleView);
        userAdapter = new AlbumAdapter((getActivity()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        rcvUser.setLayoutManager(linearLayoutManager);
        userAdapter.setData(getListUser());
        rcvUser.setAdapter(userAdapter);

        rcvUser1 = view.findViewById(R.id.recycleView1);
        userAdapter1 = new AlbumAdapter((getActivity()));
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        rcvUser1.setLayoutManager(linearLayoutManager1);
        userAdapter1.setData(getListUser());
        rcvUser1.setAdapter(userAdapter1);

        rcvUser2 = view.findViewById(R.id.recycleView2);
        userAdapter2 = new AlbumAdapter((getActivity()));
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        rcvUser2.setLayoutManager(linearLayoutManager2);
        userAdapter2.setData(getListUser());
        rcvUser2.setAdapter(userAdapter2);

        return view;
    }
    private List<Album> getListUser()
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
}