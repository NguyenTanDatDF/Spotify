package hcmute.edu.vn.spotify.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
// extend broadcast receiver to get onreceive method
public class Receiver extends BroadcastReceiver{

    // get the action and forward to myservice to process to action
    @Override
    public void onReceive(Context context, Intent intent) {
        int actionMusic = intent.getIntExtra("action",0);
        Log.e("actionMusic", Integer.toString(actionMusic));
        Intent intentService = new Intent(context, MyService.class);
        intentService.putExtra("action_music_service", actionMusic);
        context.startService(intentService);
    }

}
