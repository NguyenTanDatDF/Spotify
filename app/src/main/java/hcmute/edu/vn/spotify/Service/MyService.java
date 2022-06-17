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
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;

import hcmute.edu.vn.spotify.Activity.MainActivity;

import hcmute.edu.vn.spotify.R;

public class MyService extends Service {
    // declare the action to the switch case
    private static final int ACTION_PAUSE = 1;
    private static final int ACTION_RESUME = 2;
    private static final int ACTION_CLEAR = 3;
    private static final int ACTION_NEXT = 4;
    private static final int ACTION_PREVIOUS = 5;

    // declare the remoteView to remote with the notification bar
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

    // if forward to service this fuction will be execute
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // get data from bundle object
        Bundle bundle = intent.getExtras();
        if(bundle !=null)
        {
            // send to notification bar
            sendNotifycation();
        }
        // get action of music from broadcast receiver
        int actionMusic = intent.getIntExtra("action_music_service",0);
        // it will play a action depend on the action parameter
        handleActionMusic(actionMusic);
        return START_NOT_STICKY;
    }

    // it will play a action depend on the action parameter
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
    // pause music
    public void pauseMusic()
    {
        if (MainActivity.player.isPlaying())
        {
            Log.e("action:", "Play");
            MainActivity.player.pause();
            sendNotifycation();
        }
    }
    // play music
    public void playMusic()
    {
        if (!MainActivity.player.isPlaying())
        {
            Log.e("action:", "Pause");
            MainActivity.player.play();
            sendNotifycation();
        }
    }
    // seek to next music
    public void nextMusic()
    {
        MainActivity.player.seekToNext();
    }
    // seek to previous music
    public void previousMusic()
    {
        MainActivity.player.seekToPrevious();
    }
    // stop music
    public void clearMusic()
    {
        MainActivity.player.stop();
    }
    // convert url to bitmap (I get this function on stack overflow. link: https://stackoverflow.com/questions/20899551/cookies-with-getbitmapfromurl)
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
    // send to notification bar
    public void sendNotifycation() {
        // set strict mode, i add this to fix the getBitmapFromURL fuction
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = new Intent(this, MainActivity.class);
        // create pending intent
      PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
       //  PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);
        // user remote view to remote the notification to set the image and icon of media notification
        remoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);
        remoteViews.setTextViewText(R.id.txt_name, MainActivity.track.getName());
        remoteViews.setTextViewText(R.id.txt_artist, MainActivity.track.gettArtist().getNameArtist());
        Bitmap bitmap = getBitmapFromURL(MainActivity.track.getImage());
        remoteViews.setImageViewBitmap(R.id.img_track,bitmap);
        // if player is playing set the icon is pause
        if(MainActivity.player.isPlaying())
        {
            remoteViews.setOnClickPendingIntent(R.id.btn_pause_or_playx, getPedingIntent(this, ACTION_PAUSE));
                Log.e("if","1");
            remoteViews.setImageViewResource(R.id.btn_pause_or_playx, R.drawable.ic_pause);
        }
        // if player is stop set the icon is play
        else
        {
            remoteViews.setOnClickPendingIntent(R.id.btn_pause_or_playx, getPedingIntent(this, ACTION_RESUME));
            Log.e("if","2");
            remoteViews.setImageViewResource(R.id.btn_pause_or_playx, R.drawable.ic_play);

        }
        // send the action to broadcast receiver and pass it to the notification bar
        remoteViews.setOnClickPendingIntent(R.id.previous, getPedingIntent(this, ACTION_PREVIOUS));

        remoteViews.setOnClickPendingIntent(R.id.next, getPedingIntent(this, ACTION_NEXT));

        remoteViews.setOnClickPendingIntent(R.id.close, getPedingIntent(this, ACTION_CLEAR));

        // build the notification
        Notification notification = new NotificationCompat.Builder(this, CHANEL_ID)
                .setSmallIcon(R.drawable.ic_note)
                .setContentIntent(pendingIntent)
                .setSound(null)
                .setCustomContentView(remoteViews)
                .build();
        startForeground(1, notification);
    }

    // get pendding itent and put action to broadcast receiver
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