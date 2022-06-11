package hcmute.edu.vn.spotify.Activity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.res.AssetManager;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import hcmute.edu.vn.spotify.R;
import me.zhengken.lyricview.LyricView;

public class PlayTrackActivity extends AppCompatActivity {
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 1000;
    LyricView mLyricView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_track);
//step 2
        mLyricView = (LyricView)findViewById(R.id.custom_lyric_view);

//step 3
        String yourFilePath = PlayTrackActivity.this.getFilesDir() + "/" + "hello.lrc";
        File file = new File( yourFilePath );
        try {
            file.createNewFile();
            String s = "[ar:MARTIN GARRIX AND DAVID GUETTA]\n" +
                    "[ti:SO FAR AWAY]\n" +
                    "[length:03:03.70]\n" +
                    "[by:ASH]\n" +
                    "[re:www.megalobiz.com/lrc/maker]\n" +
                    "[ve:v1.2.3]\n" +
                    "[00:12.53]Light 'em up, light 'em up\n" +
                    "[00:14.77]Tell me where you are, tell me where you are\n" +
                    "[00:18.53]Summer nights, bright lights\n" +
                    "[00:21.01]And the shootin' stars, they break my heart\n" +
                    "[00:24.81]Callin' you now, but you're not pickin' up\n" +
                    "[00:27.53]Shadows so close if that's still enough\n" +
                    "[00:31.54]Light a match, light a match\n" +
                    "[00:34.33]Baby, in the dark, show me where you are\n" +
                    "[00:38.81]Oh, love\n" +
                    "[00:41.35]How I miss you every single day\n" +
                    "[00:43.79]When I see you on those streets\n" +
                    "[00:45.59]Oh, love\n" +
                    "[00:47.53]Tell me there's a river I can swim that will bring you back to me\n" +
                    "[00:52.06]'Cause I don't know how to love someone else\n" +
                    "[00:55.04]I don't know how to forget your face\n" +
                    "[00:58.52]No, love\n" +
                    "[01:00.09]God, I miss you every single day and now you're so far away\n" +
                    "[01:04.35]So far away\n" +
                    "[01:17.02]It's breakin' me, losin' you\n" +
                    "[01:19.28]We were far from perfect\n" +
                    "[01:21.27]But we were worth it\n" +
                    "[01:23.81]Too many fights, and we cried\n" +
                    "[01:26.06]But never said we're sorry\n" +
                    "[01:27.58]Stop sayin' you love me\n" +
                    "[01:29.52]You're callin' me now, but I can't pick up\n" +
                    "[01:32.53]Your shadow's still close, and I'm still in love\n" +
                    "[01:36.51]The summer's over now\n" +
                    "[01:39.78]But somehow it still breaks my heart\n" +
                    "[01:40.78]We could have had this talk\n" +
                    "[01:42.54]Oh\n" +
                    "[01:44.03]Oh, love\n" +
                    "[01:45.08]How I miss you every single day\n" +
                    "[01:46.33]When I see you on those streets\n" +
                    "[01:50.04]Oh, love\n" +
                    "[01:52.07]Tell me there's a river I can swim that will bring you back to me\n" +
                    "[01:56.29]'Cause I don't know how to love someone else\n" +
                    "[01:59.34]I don't know how to forget your face\n" +
                    "[02:02.77]No, love\n" +
                    "[02:04.29]God, I miss you every single day and now you're so far away\n" +
                    "[02:07.82]So far away\n" +
                    "[02:20.26]So far away, oh\n" +
                    "[02:26.82]So far away\n" +
                    "[02:33.53]So far away\n" +
                    "[02:35.54]Oh, love\n" +
                    "[02:36.81]How I miss you every single day\n" +
                    "[02:38.78]When I see you on those streets\n" +
                    "[02:41.54]Oh, love\n" +
                    "[02:43.58]Tell me there's a river I can swim that will bring you back to me\n" +
                    "[02:47.80]'Cause I don't know how to love someone else\n" +
                    "[02:50.59]I don't know how to forget your face\n" +
                    "[02:54.07]Oh, love\n" +
                    "[02:56.35]God, I miss you every single day when you're so far away";
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(s);
            fileWriter.close();

//            FileReader fileReader = new FileReader(file);
//            int c = fileReader.read();
//            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mLyricView.setLyricFile(file);

        PlayMedia();
//step 4, update LyricView every interval
//
        mLyricView.setOnPlayerClickListener(new LyricView.OnPlayerClickListener() {
            @Override
            public void onPlayerClicked(long progress, String content) {

            }
        });
    }
    @Override
    protected void onResume() {
        final long[] videoWatchedTime = {0};

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                videoWatchedTime[0] = MainActivity.absPlayerInternal.getCurrentPosition();
                Log.e(Long.toString(videoWatchedTime[0]),"Duration");
                String status = "End";
                if(MainActivity.absPlayerInternal.isPlaying())
                {
                    status = "Playing";
                }
                Log.e(status,"Status");
                mLyricView.setCurrentTimeMillis(videoWatchedTime[0]);
            }
        }, delay);
        super.onResume();
    }

    public void PlayMedia()
    {

        int appNameStringRes = R.string.app_name;

        PlayerView pvMain = findViewById(R.id.pv_main2); // creating player view
        pvMain.setBackgroundDrawable(getDrawable(R.drawable.album1));


        pvMain.setPlayer(MainActivity.absPlayerInternal); // attach surface to the view
        pvMain.setControllerShowTimeoutMs(0);

        pvMain.showController();
        pvMain.setControllerHideOnTouch(false);
    }

}