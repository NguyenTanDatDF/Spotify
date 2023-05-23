package hcmute.edu.vn.spotify.Activity;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

import hcmute.edu.vn.spotify.Model.Topic;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;




public class TrackVideo extends AppCompatActivity {
    PlayerView pvVideo ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //create view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_video);
        Track track = (Track) getIntent().getExtras().get("object_topic");
        ExoPlayer player = new ExoPlayer.Builder(this).build();
        pvVideo = findViewById(R.id.pv_video1);
        pvVideo.setPlayer(player);
        // Build the media item.

        MediaItem mediaItem = MediaItem.fromUri(track.getSource());

        player.setMediaItem(mediaItem);

        player.prepare();

        player.play();




        }

    }


