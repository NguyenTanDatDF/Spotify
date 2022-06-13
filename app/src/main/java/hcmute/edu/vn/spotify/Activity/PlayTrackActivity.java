package hcmute.edu.vn.spotify.Activity;

import androidx.appcompat.app.AppCompatActivity;


import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;

import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;
import hcmute.edu.vn.spotify.Service.LyricFacade;
import hcmute.edu.vn.spotify.Service.MyService;
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
    NotificationManager notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_track);
        pvMain = findViewById(R.id.pv_main2); // creating player view
        next = findViewById(R.id.next);
        prvious = findViewById(R.id.previous);
        img_track = findViewById(R.id.img_track);
        back = findViewById(R.id.btn_back);
//step 2
        mLyricView = (LyricView)findViewById(R.id.custom_lyric_view);
        img_track.setImageBitmap(MyService.getBitmapFromURL(MainActivity.playlist.get(MainActivity.player.getCurrentMediaItemIndex()).getImage()));

//step 3
//        String yourFilePath = PlayTrackActivity.this.getFilesDir() + "/" + "hello.lrc";
//        File file = new File( yourFilePath );
//        try {
//            file.createNewFile();
//            String s = TopicMusicActivity.track.gettLyric();
//
//            String lines[] = s.split("\\|");
//
//            FileWriter fileWriter = new FileWriter(file);
//            for(int i = 0 ; i < lines.length; i++)
//            {
//                fileWriter.write(lines[i]);
//                fileWriter.write("\r\n");
//            }
//
//            fileWriter.close();
//
////            FileReader fileReader = new FileReader(file);
////            int c = fileReader.read();
////            fileReader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        LyricFacade lyricFacade = new LyricFacade();
        File file = lyricFacade.createFileObjectWithLyric(PlayTrackActivity.this, TopicMusicActivity.track.gettLyric());
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

        MainActivity.player.addListener(new Player.Listener() {
            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Player.Listener.super.onTracksChanged(trackGroups, trackSelections);
                img_track.setImageBitmap(MyService.getBitmapFromURL(MainActivity.playlist.get(MainActivity.player.getCurrentMediaItemIndex()).getImage()));
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

    public void PlayMedia(PlayerView playerView)
    {

        playerView.setPlayer(MainActivity.player); // attach surface to the view
        playerView.setControllerShowTimeoutMs(0);

        playerView.showController();
        playerView.setControllerHideOnTouch(false);
    }


}