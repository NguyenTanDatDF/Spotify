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

import hcmute.edu.vn.spotify.Adapter.ArtistAdapter;
import hcmute.edu.vn.spotify.Model.Artist;
import hcmute.edu.vn.spotify.R;

public class AdminArtistFragment extends Fragment {

    RecyclerView rcvArtist;
    private ArtistAdapter artistAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist, container, false);

        //set data for artist admin
        rcvArtist = view.findViewById(R.id.artist_playlist);
        artistAdapter = new ArtistAdapter(getActivity());
        GridLayoutManager gridArtistayoutManager = new GridLayoutManager(getActivity(),1, GridLayoutManager.VERTICAL, false);
        rcvArtist.setLayoutManager(gridArtistayoutManager);
        artistAdapter.setData(getListArtist());
        rcvArtist.setAdapter(artistAdapter);
        return view;
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
