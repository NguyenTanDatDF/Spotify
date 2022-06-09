package hcmute.edu.vn.spotify.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Adapter.AlbumAdapter;
import hcmute.edu.vn.spotify.Adapter.TrackAdapter;
import hcmute.edu.vn.spotify.Model.Album;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;

public class AlbumMusicActivity extends AppCompatActivity {

    private RecyclerView rcvAlbum;
    private RecyclerView rcvTrack;
    private AlbumAdapter albumAdapter;
    private TrackAdapter trackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_music);
        //Set data for albums
        rcvAlbum = findViewById(R.id.activityAlbumMusic_listAlbumRv);
        albumAdapter = new AlbumAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvAlbum.setLayoutManager(linearLayoutManager);

        albumAdapter.setData(getListAlbum());
        rcvAlbum.setAdapter(albumAdapter);

        //Set data for track
        rcvTrack = findViewById(R.id.activityAlbumMusic_listMusicRv);
        trackAdapter = new TrackAdapter(this);

        LinearLayoutManager linearLayoutTrackManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvTrack.setLayoutManager(linearLayoutTrackManager);

        trackAdapter.setData(getListTrack());
        rcvTrack.setAdapter(trackAdapter);
    }
    private List<Album> getListAlbum()
    {
        List<Album> list = new ArrayList<>();
        list.add(new Album("Chill" ,"https://image.thanhnien.vn/w1024/Uploaded/2022/wpdhnwejw/2022_03_18/img-8371-4267.jpeg", "MCK, K-ICM, LowG"));
        list.add(new Album("Remix Tiktok" ,"https://image.thanhnien.vn/w1024/Uploaded/2022/wpdhnwejw/2022_03_18/img-8371-4267.jpeg","Nguyen Tan Dat, Cukak"));
        list.add(new Album("Bolero" ,"https://image.thanhnien.vn/w1024/Uploaded/2022/wpdhnwejw/2022_03_18/img-8371-4267.jpeg","Tran Dang Khoa"));
        list.add(new Album("Nonstop", "https://image.thanhnien.vn/w1024/Uploaded/2022/wpdhnwejw/2022_03_18/img-8371-4267.jpeg", "Nguyen Le Minh Nhut"));
        list.add(new Album( "Piano","https://image.thanhnien.vn/w1024/Uploaded/2022/wpdhnwejw/2022_03_18/img-8371-4267.jpeg","Ho Dang Tien" ));
        list.add(new Album("Guitar", "https://image.thanhnien.vn/w1024/Uploaded/2022/wpdhnwejw/2022_03_18/img-8371-4267.jpeg", "Nguyen Thien Hoan"));

        return list;
    }
    private List<Track> getListTrack()
    {
        List<Track> list = new ArrayList<>();
        list.add(new Track("Không con đâu" ,17092001, R.drawable.album, "",  ""));
        list.add(new Track("Không con nha" ,17092001, R.drawable.album1, "",  ""));
        list.add(new Track("Không con haha" ,17092001, R.drawable.album2, "",  ""));
        list.add(new Track("Không con sad" ,17092001, R.drawable.album3, "",  ""));
        list.add(new Track("Không con vjp" ,17092001, R.drawable.album4, "",  ""));

        return list;
    }
}