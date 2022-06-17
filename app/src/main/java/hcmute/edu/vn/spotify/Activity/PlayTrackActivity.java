package hcmute.edu.vn.spotify.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Database.DAOPlayListTrack;
import hcmute.edu.vn.spotify.Database.DAOTrack;
import hcmute.edu.vn.spotify.Model.PlaylistTrack;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.Model.User;
import hcmute.edu.vn.spotify.R;
import hcmute.edu.vn.spotify.Service.LyricFacade;
import hcmute.edu.vn.spotify.Service.MyService;
import hcmute.edu.vn.spotify.Service.ThreadSafeLazyUserSingleton;
import me.zhengken.lyricview.LyricView;

public class PlayTrackActivity extends AppCompatActivity {
    // declare the Handler and Rumble to update the lyric in every second
    Handler handler = new Handler();
    Runnable runnable;

    // Set the delay is 1 second (update every one second)
    int delay = 1000;

    // Declere the lyricview
    LyricView mLyricView;
    PlayerView pvMain ; // creating player view
    // declare the button to control the music
    ImageButton next;
    ImageButton prvious;
    ImageView img_track;
    ImageView back;
    ImageView repeat;
    // declare the heart icon and name of song and artist
    ImageView imgv_heart;
    TextView name, artist;
    // set the value for playlist track (all playlist track)
    List<PlaylistTrack> allPlayListTrack = getPlaylistTrack();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_track);

        // mapping the view
        pvMain = findViewById(R.id.pv_main2); // creating player view
        next = findViewById(R.id.next);
        prvious = findViewById(R.id.previous);
        img_track = findViewById(R.id.img_track);
        back = findViewById(R.id.btn_back);
        repeat = findViewById(R.id.repeat);
        imgv_heart = findViewById(R.id.imgv_heart);
        name = findViewById(R.id.tv_name);
        artist = findViewById(R.id.tv_artist);
        mLyricView = (LyricView)findViewById(R.id.custom_lyric_view);

        //check if the mode of play is list or single track
        if(MainActivity.typePlaying.equals("list"))
        {
            img_track.setImageBitmap(MyService.getBitmapFromURL(MainActivity.playlist.get(MainActivity.player.getCurrentMediaItemIndex()).getImage()));
            name.setText(MainActivity.playlist.get(MainActivity.player.getCurrentMediaItemIndex()).getName());
            artist.setText(MainActivity.playlist.get(MainActivity.player.getCurrentMediaItemIndex()).getArtistName());
        }
        if(MainActivity.typePlaying.equals("single"))
        {
            img_track.setImageBitmap(MyService.getBitmapFromURL(MainActivity.track.getImage()));
            name.setText(MainActivity.track.getName());
            artist.setText(MainActivity.track.getArtistName());
        }

        // create a file object and put lyric of track to this file
        LyricFacade lyricFacade = new LyricFacade();
        File file = lyricFacade.createFileObjectWithLyric(PlayTrackActivity.this, MainActivity.track.gettLyric());
        // set lyric by file
        mLyricView.setLyricFile(file);


        // attach the new controller to player
         PlayMedia(pvMain);

        // update LyricView every interval
        // seek to next
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.player.seekToNext();
            }
        });
        // seek to previous
        prvious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.player.seekToPrevious();
            }
        });

        // get user by singleton
        User user = new User();
        ThreadSafeLazyUserSingleton singleton = ThreadSafeLazyUserSingleton.getInstance(user);
        user = singleton.user;
        User finalUser1 = user;

        // Create dao object to get data
        DAOPlayListTrack daoPlayListTrack = new DAOPlayListTrack();

        // When you click on the heart icon check it's current state by tag and
        // add to song of user if user has not like and remove if double tap
        imgv_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // check if current tag is fill heart or empty heart
                if(getDrawableId(imgv_heart)==R.drawable.ic_not_heart)
                {
                    //     StartService();
                    // refresh icon
                    imgv_heart.setImageResource(R.drawable.ic_heart_fill);
                    // refresh flag
                    // Create default format of playlist

                    PlaylistTrack newTrack = new PlaylistTrack(randomId() ,MainActivity.track.getTrackId().trim(), finalUser1.getUserId());
                    daoPlayListTrack.addNewPlaylistTrack(newTrack).addOnSuccessListener(suc -> {

                    }).addOnFailureListener(err -> {

                    });

                }
                else
                {
                    // refresh icon
                    imgv_heart.setImageResource(R.drawable.ic_not_heart);
                    // refresh flag
                    for(PlaylistTrack playlistTrack: allPlayListTrack){
                        if(MainActivity.track.getTrackId().trim().equals(playlistTrack.getTrackId().trim())){
                            daoPlayListTrack.removePlaylistTrack(playlistTrack.getKey().trim()).addOnSuccessListener(suc -> {
                            }).addOnFailureListener(err -> {
                            });
                        }
                    }
                }



            }
        });

        // Check the mode of repeat is turn on or off to active and refresh the icon
        if ( MainActivity.player.getRepeatMode()==MainActivity.player.REPEAT_MODE_OFF){
           repeat.setImageResource(R.drawable.ic_repeat);
        }
        else
        {
            repeat.setImageResource(R.drawable.repeat_on);

        }

        // turn or off the repeat mode
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( MainActivity.player.getRepeatMode()==MainActivity.player.REPEAT_MODE_OFF){
                    // if player are playing list of track then set mode of repeat if all
                    if(MainActivity.typePlaying.equals("list"))
                    {
                        MainActivity.player.setRepeatMode(MainActivity.player.REPEAT_MODE_ALL);
                        repeat.setImageResource(R.drawable.repeat_on);
                    }
                    // if player are playing a single track then set mode of repeat if one
                    else
                    {
                        MainActivity.player.setRepeatMode(MainActivity.player.REPEAT_MODE_ONE);
                        repeat.setImageResource(R.drawable.repeat_on);
                    }
                }
                // turn off if double click
                else
                {
                    MainActivity.player.setRepeatMode(MainActivity.player.REPEAT_MODE_OFF);
                    repeat.setImageResource(R.drawable.ic_repeat);
                }

            }
        });


        // get the temp variable
        User finalUser = user;

        // if track is exist find the icon of heart
        // (it can be empty or fill depend on it is on the song of user)
        if(MainActivity.track!=null)
        {
            getUserListTrack(user.getUserId(),user.getUserId(), imgv_heart, MainActivity.track);
        }

        // Exoplayer library support us a lot of listener
        // Here i use on track change event to update the information to show for user
        MainActivity.player.addListener(new Player.Listener() {
            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                if(MainActivity.typePlaying.equals("list"))
                {
                    MainActivity.track = MainActivity.playlist.get(MainActivity.player.getCurrentMediaItemIndex());
                }
//                else
//                {
//                    MainActivity.track = MainActivity.playlist.get(MainActivity.player.getCurrentMediaItemIndex());
//                }
                getUserListTrack(finalUser.getUserId(),finalUser.getUserId(), imgv_heart,MainActivity.track);

                StartService();
                Player.Listener.super.onTracksChanged(trackGroups, trackSelections);

                // update lyric
                LyricFacade lyricFacade = new LyricFacade();
                File file = new File(getFilesDir() + "/" + "hello.lrc");
                file = lyricFacade.createFileObjectWithLyric(PlayTrackActivity.this, MainActivity.track.gettLyric());
                mLyricView.setLyricFile(file);

            }

        });

        mLyricView.setOnPlayerClickListener(new LyricView.OnPlayerClickListener() {
            @Override
            public void onPlayerClicked(long progress, String content) {

            }
        });
        // get back to previous activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // refresh the lyric every interval
    @Override
    protected void onResume() {
        final long[] videoWatchedTime = {0};

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                videoWatchedTime[0] = MainActivity.player.getCurrentPosition();

                if(MainActivity.player.isPlaying() || MainActivity.player.isLoading())
                {
                }
                mLyricView.setCurrentTimeMillis(videoWatchedTime[0]);
            }
        }, delay);
        super.onResume();
    }
    // get the tag you have set
    private int getDrawableId(ImageView iv) {
        return (Integer) iv.getTag();
    }
    // Hash the id of the user when create
    public String randomId() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 15; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }
    // attach the controller with player
    public void PlayMedia(PlayerView playerView)
    {

        playerView.setPlayer(MainActivity.player); // attach surface to the view

        //configure the controller
        playerView.setControllerShowTimeoutMs(0);
        playerView.showController();
        playerView.setControllerHideOnTouch(false);
    }
    // start a forceground service (media notification bar)
    public void StartService(){
        Intent intent = new Intent(this, MyService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("track",MainActivity.track);
        intent.putExtras(bundle);
        startService(intent);

    }


    //get all playlist track
    private List<PlaylistTrack> getPlaylistTrack()
    {
        List<PlaylistTrack> list = new ArrayList<>();
        DAOPlayListTrack daoPlayListTrack = new DAOPlayListTrack();
        daoPlayListTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot data: snapshot.getChildren()){
                    PlaylistTrack playlistTrack = data.getValue(PlaylistTrack.class);
                    list.add(playlistTrack);
                    String key = data.getKey();
                    playlistTrack.setKey(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }

    // this function will update the icon heart
    private List<Track> getUserListTrack(String userId, String playlistId, ImageView imgv_heart, Track track1)
    {
        List<Track> list = new ArrayList<>();
        List<PlaylistTrack> playlistTrack = getPlaylistTrack();
        DAOTrack daoTrack = new DAOTrack();
        daoTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // list.clear();
                img_track.setImageBitmap(MyService.getBitmapFromURL(MainActivity.track.getImage()));
                name.setText(MainActivity.track.getName());
                artist.setText(MainActivity.track.getArtistName());

                User user = new User();

                //user = SigninActivity.definedUser;
                ThreadSafeLazyUserSingleton singleton = ThreadSafeLazyUserSingleton.getInstance(user);
                user = singleton.user;

                for(PlaylistTrack playlistTrack1: playlistTrack)
                {
                    if(userId.equals(user.getUserId().trim()) && playlistId.equals(playlistTrack1.getPlaylistId().trim()) && MainActivity.track.getTrackId().trim().equals(playlistTrack1.getTrackId().trim()) )
                    {
                        // set fill heart if it is in the song of user
                        imgv_heart.setImageResource(R.drawable.ic_heart_fill);
                        imgv_heart.setTag(R.drawable.ic_heart_fill);
                        break;
                    }
                    else
                    {
                        imgv_heart.setImageResource(R.drawable.ic_not_heart);
                        imgv_heart.setTag(R.drawable.ic_not_heart);

                    }
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return list;
    }


}