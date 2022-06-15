package hcmute.edu.vn.spotify.Service;

import static hcmute.edu.vn.spotify.Service.MyApplication.CHANEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import hcmute.edu.vn.spotify.Activity.MainActivity;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;

public class MyService extends Service {

    private static final int ACTION_PAUSE = 1;
    private static final int ACTION_RESUME = 2;
    private static final int ACTION_CLEAR = 3;
    private static final int ACTION_NEXT = 4;
    private static final int ACTION_PREVIOUS = 5;

    RemoteViews remoteViews;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if(bundle !=null)
        {
            sendNotifycation();
        }

        int actionMusic = intent.getIntExtra("action_music_service",0);
        handleActionMusic(actionMusic);
        return START_NOT_STICKY;
    }

    private void handleActionMusic(int action)
    {
        switch (action)
        {
            case ACTION_PAUSE:
                pauseMusic();
                break;
            case ACTION_RESUME:
                playMusic();
                break;
            case ACTION_CLEAR:
                clearMusic();
                break;
            case ACTION_NEXT:
                nextMusic();
                break;
            case ACTION_PREVIOUS:
                previousMusic();
                break;
        }
    }

    public void pauseMusic()
    {
        if (MainActivity.player.isPlaying())
        {
            Log.e("action:", "Play");
            MainActivity.player.pause();
            sendNotifycation();
        }
    }

    public void playMusic()
    {
        if (!MainActivity.player.isPlaying())
        {
            Log.e("action:", "Pause");
            MainActivity.player.play();
            sendNotifycation();
        }
    }

    public void nextMusic()
    {
        MainActivity.player.seekToNext();
    }
    public void previousMusic()
    {
        MainActivity.player.seekToPrevious();
    }
    public void clearMusic()
    {
        MainActivity.player.seekToPrevious();
    }

    public static Bitmap getBitmapFromURL(String src) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }

    public void sendNotifycation() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);
        remoteViews.setTextViewText(R.id.txt_name, MainActivity.track.getName());
        remoteViews.setTextViewText(R.id.txt_artist, MainActivity.track.gettArtist().getNameArtist());
        Bitmap bitmap = getBitmapFromURL(MainActivity.track.getImage());
        remoteViews.setImageViewBitmap(R.id.img_track,bitmap);

        if(MainActivity.player.isPlaying())
        {
            remoteViews.setOnClickPendingIntent(R.id.btn_pause_or_playx, getPedingIntent(this, ACTION_PAUSE));
                Log.e("if","1");
            remoteViews.setImageViewResource(R.id.btn_pause_or_playx, R.drawable.ic_pause);
        }

        else
        {
            remoteViews.setOnClickPendingIntent(R.id.btn_pause_or_playx, getPedingIntent(this, ACTION_RESUME));
            Log.e("if","2");
            remoteViews.setImageViewResource(R.id.btn_pause_or_playx, R.drawable.ic_play);

        }

        remoteViews.setOnClickPendingIntent(R.id.previous, getPedingIntent(this, ACTION_PREVIOUS));

        remoteViews.setOnClickPendingIntent(R.id.next, getPedingIntent(this, ACTION_NEXT));

        remoteViews.setOnClickPendingIntent(R.id.close, getPedingIntent(this, ACTION_CLEAR));

        Notification notification = new NotificationCompat.Builder(this, CHANEL_ID)
                .setSmallIcon(R.drawable.ic_note)
                .setContentIntent(pendingIntent)
                .setSound(null)
                .setCustomContentView(remoteViews)
                .build();
        startForeground(1, notification);
    }


    private PendingIntent getPedingIntent(Context context, int action)
    {
        Intent intent = new Intent(this, Receiver.class);
        intent.putExtra("action",action);
        return PendingIntent.getBroadcast(context.getApplicationContext(), action, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}