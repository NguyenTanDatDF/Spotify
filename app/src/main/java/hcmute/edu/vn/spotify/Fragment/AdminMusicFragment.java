package hcmute.edu.vn.spotify.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Adapter.TrackAdapter;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;

public class AdminMusicFragment extends Fragment {

    RecyclerView rcvTrack;
    private TrackAdapter trackAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        //set data for artist admin
        rcvTrack = view.findViewById(R.id.music_playlist);
        trackAdapter = new TrackAdapter(getActivity());
        GridLayoutManager gridArtistayoutManager = new GridLayoutManager(getActivity(),1, GridLayoutManager.VERTICAL, false);
        rcvTrack.setLayoutManager(gridArtistayoutManager);
        trackAdapter.setData(getListTrack());
        rcvTrack.setAdapter(trackAdapter);
        return view;
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
