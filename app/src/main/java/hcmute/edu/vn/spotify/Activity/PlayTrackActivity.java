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
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 1000;
    LyricView mLyricView;
    PlayerView pvMain ; // creating player view
    ImageButton next;
    ImageButton prvious;
    ImageView img_track;
    ImageView back;
    ImageView repeat;
    ImageView imgv_heart;
    TextView name, artist;
    NotificationManager notificationManager;
    List<PlaylistTrack> allPlayListTrack = getPlaylistTrack();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_track);
        pvMain = findViewById(R.id.pv_main2); // creating player view
        next = findViewById(R.id.next);
        prvious = findViewById(R.id.previous);
        img_track = findViewById(R.id.img_track);
        back = findViewById(R.id.btn_back);
        repeat = findViewById(R.id.repeat);
        imgv_heart = findViewById(R.id.imgv_heart);
        name = findViewById(R.id.tv_name);
        artist = findViewById(R.id.tv_artist);
//step 2
        mLyricView = (LyricView)findViewById(R.id.custom_lyric_view);

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

        LyricFacade lyricFacade = new LyricFacade();
        File file = lyricFacade.createFileObjectWithLyric(PlayTrackActivity.this, MainActivity.track.gettLyric());
        mLyricView.setLyricFile(file);






      PlayMedia(pvMain);

//step 4, update LyricView every interval
//
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.player.seekToNext();
            }
        });
        prvious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.player.seekToPrevious();
            }
        });
        User user = new User();
        ThreadSafeLazyUserSingleton singleton = ThreadSafeLazyUserSingleton.getInstance(user);
        user = singleton.user;
        User finalUser1 = user;

        DAOPlayListTrack daoPlayListTrack = new DAOPlayListTrack();

        imgv_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



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

                // attach with first child

            }
        });

        if ( MainActivity.player.getRepeatMode()==MainActivity.player.REPEAT_MODE_OFF){
           repeat.setImageResource(R.drawable.ic_repeat);
        }
        else
        {
            repeat.setImageResource(R.drawable.repeat_on);

        }

        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( MainActivity.player.getRepeatMode()==MainActivity.player.REPEAT_MODE_OFF){
                    if(MainActivity.typePlaying.equals("list"))
                    {
                        MainActivity.player.setRepeatMode(MainActivity.player.REPEAT_MODE_ALL);
                        repeat.setImageResource(R.drawable.repeat_on);
                    }
                    else
                    {
                        MainActivity.player.setRepeatMode(MainActivity.player.REPEAT_MODE_ONE);
                        repeat.setImageResource(R.drawable.repeat_on);
                    }
                }
                else
                {
                    MainActivity.player.setRepeatMode(MainActivity.player.REPEAT_MODE_OFF);
                    repeat.setImageResource(R.drawable.ic_repeat);
                }

            }
        });



        User finalUser = user;


        if(MainActivity.track!=null)
        {
            getUserListTrack(user.getUserId(),user.getUserId(), imgv_heart, MainActivity.track);
        }

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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
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
    public void PlayMedia(PlayerView playerView)
    {

        playerView.setPlayer(MainActivity.player); // attach surface to the view
        playerView.setControllerShowTimeoutMs(0);

        playerView.showController();
        playerView.setControllerHideOnTouch(false);
    }

    public void StartService(){
        Intent intent = new Intent(this, MyService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("track",MainActivity.track);
        intent.putExtras(bundle);
        startService(intent);

    }

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