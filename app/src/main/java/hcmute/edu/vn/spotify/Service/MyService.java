package hcmute.edu.vn.spotify.Service;

import static hcmute.edu.vn.spotify.Service.MyApplication.CHANEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("DatNguyen", "Service On create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if(bundle !=null)
        {
            Track track = (Track) bundle.get("track");
            sendNotifycation(track);
        }

        return START_NOT_STICKY;
    }

    public static Bitmap getBitmapFromURL(String src) {
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

    private void sendNotifycation(Track track) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);
        remoteViews.setTextViewText(R.id.txt_name, track.getName());
        remoteViews.setTextViewText(R.id.txt_artist, "Dat Nguyen");
        Bitmap bitmap = getBitmapFromURL("https://c-fa.cdn.smule.com/rs-s80/arr/dc/95/7347ebe0-f710-48db-ad61-5f3154ae8873_1024.jpg");
        remoteViews.setImageViewResource(R.id.btn_pause_or_play, R.drawable.ic_play);
        remoteViews.setImageViewBitmap(R.id.img_track,bitmap);
      Notification notification = new NotificationCompat.Builder(this, CHANEL_ID)
              .setSmallIcon(R.drawable.ic_note)
              .setContentIntent(pendingIntent)
              .setSound(null)
              .setCustomContentView(remoteViews)
              .build();
      startForeground(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("DatNguyen", "Service Destroy");
    }
}
