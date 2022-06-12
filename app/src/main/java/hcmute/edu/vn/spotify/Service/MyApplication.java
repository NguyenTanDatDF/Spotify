package hcmute.edu.vn.spotify.Service;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApplication extends Application {
    public  static  final  String CHANEL_ID = "chanel_service_spotify";
    @Override
    public void onCreate() {
        super.onCreate();

        createChanelNotification();

    }

    private void createChanelNotification() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(CHANEL_ID, "Chanel Service Spotify", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setSound(null, null);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
