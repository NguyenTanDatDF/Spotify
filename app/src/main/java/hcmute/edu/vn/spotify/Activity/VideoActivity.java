package hcmute.edu.vn.spotify.Activity;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;

import android.net.Uri;
import android.os.Bundle;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_video);
        //PlayerView pvVideo ;
        //pvVideo = findViewById(R.id.pv_video); // creating player view
        //PlayMedia(pvVideo);

    }
    public void PlayMedia(PlayerView playerView)
    {
        playerView.setPlayer(MainActivity.player); // attach surface to the view
        //configure the controller
        playerView.setControllerShowTimeoutMs(0);
        playerView.showController();
        //playerView.setControllerHideOnTouch(false);
        Track track = new Track();
        PlayVideo(track);
    }
    public void PlayVideo(Track track)
    {
        // stop if player is playing
        if(MainActivity.player.isPlaying())
        {
            MainActivity.player.stop(true);
        }
        // create media item from url
        Uri uriOfContentUrl = Uri.parse("https://firebasestorage.googleapis.com/v0/b/admin-sportify.appspot.com/o/y2mate.com%20-%20JSOL%20%20HO%C3%80NG%20DUY%C3%8AN%20%20S%C3%80I%20G%C3%92N%20H%C3%94M%20NAY%20M%C6%AFA%20%20Official%20MV_480p.mp4?alt=media&token=447c5820-61c2-4b6a-bd15-5b1f0396fbd3");
        MediaItem Item = MediaItem.fromUri(uriOfContentUrl);
        // Add the media items to be played.
        MainActivity.player.addMediaItem(Item);
        // load the track
        MainActivity.player.prepare();
        // Start the playback.
        MainActivity.player.play();
        // configure the player
        // set always display
        MainActivity.pvMain.setControllerShowTimeoutMs(0);
        // show controller
        MainActivity.pvMain.showController();
        // show disable the hiding on touching
        MainActivity.pvMain.setControllerHideOnTouch(false);
    }
}