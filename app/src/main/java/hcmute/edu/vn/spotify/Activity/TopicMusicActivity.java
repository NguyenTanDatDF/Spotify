package hcmute.edu.vn.spotify.Activity;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 3000;
    String status = "Playing";
    private RecyclerView rcvTrack;
    private TrackAdapter trackAdapter;
    CollapsingToolbarLayout topicTitle;
    ImageView topicImage;
    FloatingActionButton btn_playlist;
    List<Track> listTrack = null;
    static Track track;
    static int flag =2;
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
            playListTrack(listTrack);
        }

    }

//    @Override
//    protected void onResume() {
//        final long[] videoWatchedTime = {0};
//
//        handler.postDelayed(runnable = new Runnable() {
//            public void run() {
//                handler.postDelayed(runnable, delay);
//                videoWatchedTime[0] = MainActivity.absPlayerInternal.getCurrentPosition();
//                Log.e(Long.toString(videoWatchedTime[0]),"Duration");
//                if(MainActivity.absPlayerInternal.isPlaying())
//                {
//                    status = "Playing";
//                }
//                else
//                {
//                    status = "End";
//                    if(MainActivity.currentIndex!=MainActivity.playlist.size()) {
//
//                        PlayMedia(MainActivity.playlist.get(MainActivity.currentIndex + 1).getSource());
//                        MainActivity.currentIndex = MainActivity.currentIndex +1;
//                    }
//                }
//                Log.e(status,"Status");
//            }
//        }, delay);
//        super.onResume();
//    }



    public void playListTrack(List<Track> trackList)
    {
        btn_playlist = findViewById(R.id.btn_playlist);
        btn_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PlayMedia(trackList.get(0).getSource());
                PlayListMedia(trackList);
                MainActivity.name_track.setText( trackList.get(0).getName());
                MainActivity.nameArtist_track.setText( trackList.get(0).getName());
                MainActivity.img_track.setImageBitmap(MyService.getBitmapFromURL(trackList.get(0).getImage()));
                track = trackList.get(0);
                Track track1 = (Track) track.createClone();
                System.out.println(track1);
                MainActivity.currentIndex = 0;
            }
        });
    }

//    public void PlayMedia(String url)
//    {
//        MainActivity.absPlayerInternal.stop();
//
//        String CONTENT_URL = url;
//
//        int appNameStringRes = R.string.app_name;
//
//        String userAgent = Util.getUserAgent(this, this.getString(appNameStringRes));
//        DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(this,userAgent);
//        Uri uriOfContentUrl = Uri.parse(CONTENT_URL);
//        MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(uriOfContentUrl);  // creating a media source
//
//        MainActivity.absPlayerInternal.prepare(mediaSource);
//
//        MainActivity.absPlayerInternal.setPlayWhenReady(true); // start loading video and play it at the moment a chunk of it is available offline (start and play immediately)
//
//        MainActivity.pvMain.setPlayer( MainActivity.absPlayerInternal); // attach surface to the view
//        MainActivity.pvMain.setControllerShowTimeoutMs(0);
//
//        MainActivity.pvMain.showController();
//        MainActivity.pvMain.setControllerHideOnTouch(false);
//    }

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
    //Get list track from firebase
//    private List<Track> getListTrack(String genreName)
//    {
//        List<Track> list = new ArrayList<>();
//
//        DAOTrack daoTrack = new DAOTrack();
//        daoTrack.getByKey().addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                List<Integer> listIndex = new ArrayList<>();
//                for(DataSnapshot data: snapshot.getChildren()){
//                    Track track = data.getValue(Track.class);
//                    if(track.gettGenre().trim().equals(genreName)){
//                        list.add(track);
//                        String key = data.getKey();
//                        track.setKey(key);
//                    }
//                }
//                MainActivity.playlist= list;
//                trackAdapter.setData(list);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        return list;
//    }

    private List<Track> getListTrack(String type)
    {
        List<Track>  list = new ArrayList<>();
        DAOTrack daoTrack = new DAOTrack();
        daoTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
