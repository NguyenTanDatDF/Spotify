package hcmute.edu.vn.spotify.Activity;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import hcmute.edu.vn.spotify.Service.MyService;

public class TopicMusicActivity extends AppCompatActivity {
    //Declare variables
    //recycle view to get all the track
    private RecyclerView rcvTrack;
    //Track adapter
    private TrackAdapter trackAdapter;
    //Get topic title
    CollapsingToolbarLayout topicTitle;
    //Get topic image
    ImageView topicImage;
    //Get topic name
    static String  topicName;
    //Play all the track button
    FloatingActionButton btn_playlist;
    //First time, list track is null
    List<Track> listTrack = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //create view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_music);

        //Get the topic's data from search fragment using get intent
        if(getIntent().getExtras() != null) {
            //defined topic
            Topic topic = (Topic) getIntent().getExtras().get("object_topic");

            //Mapping variables
            topicTitle = (CollapsingToolbarLayout) findViewById(R.id.activityTopicMusic_Tv);
            topicImage = (ImageView) findViewById(R.id.activityTopicMusic_Iv);
            topicTitle.setTitle(topic.getName().trim());
            Glide.with(this).load(topic.getUrl()).into(topicImage);

            //Set data for track recycle view
            setData(topic);
            topicName = topic.getName().trim();
            playListTrack(MainActivity.playlist);
        }

    }

    //Player will play a list of track which belong to this topic and set the global variables
    public void playListTrack(List<Track> trackList)
    {
        btn_playlist = findViewById(R.id.btn_playlist);
        btn_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PlayMedia(trackList.get(0).getSource());
                MainActivity.player.stop(true);
                PlayListMedia(MainActivity.playlist);
                MainActivity.track = MainActivity.playlist.get(0);
                MainActivity.name_track.setText( MainActivity.track .getName());
                MainActivity.nameArtist_track.setText( MainActivity.track .getName());
                MainActivity.img_track.setImageBitmap(MyService.getBitmapFromURL(MainActivity.track .getImage()));
                MainActivity.currentIndex = 0;
                MainActivity.typePlaying = "list";
            }
        });
    }

    //Re update list track when user comeback activity
    @Override
    protected void onResume() {
        super.onResume();
        getListTrack(topicName);
    }

    //Play a list of track
    public void PlayListMedia(List<Track> tracks)
    {

        int appNameStringRes = R.string.app_name;

        //MainActivity.pvMain.setPlayer(MainActivity.player);
        for(int i =0; i < tracks.size(); i++)
        {
            Uri uriOfContentUrl = Uri.parse(tracks.get(i).getSource());
            MediaItem Item = MediaItem.fromUri(uriOfContentUrl);
            // Add the media items to be played.
            MainActivity.player.addMediaItem(Item);
        }

        MainActivity.player.prepare();
        // Start the playback.
        MainActivity.player.play(); // start loading video and play it at the moment a chunk of it is available offline (start and play immediately)

        MainActivity.pvMain.setControllerShowTimeoutMs(0);

        MainActivity.pvMain.showController();
        MainActivity.pvMain.setControllerHideOnTouch(false);
    }



    //Set data for track recycle view using topic
    public void setData(Topic topic){
        //Set data for track
        rcvTrack = findViewById(R.id.activityTopicMusic_Rv);
        trackAdapter = new TrackAdapter(this);
        LinearLayoutManager linearLayoutTrackManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvTrack.setLayoutManager(linearLayoutTrackManager);
        listTrack = getListTrack(topic.getName().trim());
        trackAdapter.setData(listTrack);
        rcvTrack.setAdapter(trackAdapter);
    }


    //Get list track
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
