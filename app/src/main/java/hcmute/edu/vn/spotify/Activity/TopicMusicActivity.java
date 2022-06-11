package hcmute.edu.vn.spotify.Activity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Adapter.AlbumAdapter;
import hcmute.edu.vn.spotify.Adapter.TrackAdapter;
import hcmute.edu.vn.spotify.Database.DAOTrack;
import hcmute.edu.vn.spotify.Model.Album;
import hcmute.edu.vn.spotify.Model.Topic;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;

public class TopicMusicActivity extends AppCompatActivity {

    private RecyclerView rcvTrack;
    private TrackAdapter trackAdapter;
    CollapsingToolbarLayout topicTitle;
    ImageView topicImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_music);
        if(getIntent().getExtras() != null) {
            Topic topic = (Topic) getIntent().getExtras().get("object_topic");
            topicTitle = (CollapsingToolbarLayout) findViewById(R.id.activityTopicMusic_Tv);
            topicImage = (ImageView) findViewById(R.id.activityTopicMusic_Iv);
            topicTitle.setTitle(topic.getName().trim());
            Glide.with(this).load(topic.getUrl()).into(topicImage);
            setData(topic);
        }

    }

    public void setData(Topic topic){
        //Set data for track
        rcvTrack = findViewById(R.id.activityTopicMusic_Rv);
        trackAdapter = new TrackAdapter(this);
        LinearLayoutManager linearLayoutTrackManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvTrack.setLayoutManager(linearLayoutTrackManager);
        trackAdapter.setData(getListTrack(topic.getName().trim()));
        rcvTrack.setAdapter(trackAdapter);
    }
    //Get list track from firebase
    private List<Track> getListTrack(String genreName)
    {
        List<Track> list = new ArrayList<>();

        DAOTrack daoTrack = new DAOTrack();
        daoTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Track track = data.getValue(Track.class);
                    if(track.gettGenre().trim().equals(genreName)){
                        list.add(track);
                        String key = data.getKey();
                        track.setKey(key);
                    }
                }
                trackAdapter.setData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return list;
    }
}
