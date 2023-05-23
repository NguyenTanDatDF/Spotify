package hcmute.edu.vn.spotify.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Activity.MainActivity;
import hcmute.edu.vn.spotify.Adapter.TrackAdapter;
import hcmute.edu.vn.spotify.Adapter.TrackAdapter2;
import hcmute.edu.vn.spotify.Database.DAOTrack;
import hcmute.edu.vn.spotify.Model.Topic;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;


public class VIdeoFragment extends Fragment {
    private RecyclerView rcvTrack;
    private TrackAdapter2 trackAdapter;
    List<Track> listTrack = null;

    public VIdeoFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        Topic topic = new Topic("-NW38f6B0UcFhwjdF-J5",1,"Video","https://cliktodeal.com/wp-content/uploads/2020/08/audio-video-logo-1.png");
        setData(view,topic);

        return view;
    }

    public void setData(View view, Topic topic){
        //Set data for track
        rcvTrack = view.findViewById(R.id.videorecycle);
        trackAdapter = new TrackAdapter2((getActivity()));
        LinearLayoutManager linearLayoutTrackManager = new LinearLayoutManager((getActivity()), RecyclerView.VERTICAL, false);
        rcvTrack.setLayoutManager(linearLayoutTrackManager);
        listTrack = getListTrack(topic.getName().trim());
        trackAdapter.setData(listTrack);
        rcvTrack.setAdapter(trackAdapter);
    }

    private List<Track> getListTrack(String type)
    {
        List<Track>  list = new ArrayList<>();
        DAOTrack daoTrack = new DAOTrack();
        daoTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot data: snapshot.getChildren()){
                    Track track = data.getValue(Track.class);
                    String key = data.getKey();

                    track.setKey(key);
                    try{
                        if(track.gettGenre() != null){
                            if(track.gettGenre().equals(type)){
                                list.add(track);
                            }
                        }
                        else
                        if(track.gettGenre() == null){
                            if(track.gettGenre().equals(type)){
                                list.add(track);
                            }
                        }
                    }
                    catch (Exception e){

                    }

                }
                MainActivity.playlist= list;
                trackAdapter.notifyDataSetChanged();
                trackAdapter.setData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return list;
    }
}