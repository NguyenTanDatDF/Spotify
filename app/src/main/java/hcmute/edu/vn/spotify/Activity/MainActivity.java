package hcmute.edu.vn.spotify.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

import hcmute.edu.vn.spotify.Fragment.LibraryFragment;
import hcmute.edu.vn.spotify.Fragment.SearchFragment;
import hcmute.edu.vn.spotify.Fragment.HomeFragment;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;
import hcmute.edu.vn.spotify.Service.MyService;
import hcmute.edu.vn.spotify.Service.OnSwipeTouchListener;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class MainActivity extends AppCompatActivity {
    // declare the view
    MeowBottomNavigation bottomNavigation;

    // create global control player. Because when in other activity,
    // user change the track to play then control player must be updated
    public static PlayerView pvMain;

    // Music player of this is the most important of this application,
    // In any activity user change the track play must play this source of track
    public static ExoPlayer player;

    // This is store the list of current track is playing, when a single track this list will be set as null
    public static List<Track> playlist ;

    // This is the current index of track in playlist is playing
    public static int currentIndex;
    // Declare the view
    public static ImageView img_track;
    public static TextView name_track;
    public static TextView nameArtist_track;

    // this is current track is playing, if user playing a playlist this track will be set as null
    public static Track track;

    // It mean the type of playing, it can be "single" (a track is playing) or "list" (a playlist is playing)
    public static String typePlaying;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mapping the view
        bottomNavigation = findViewById(R.id.meow_bot_nav);
        ImageButton imgv_heart = findViewById(R.id.imgv_heart);
        img_track = findViewById(R.id.imgv_track);
        name_track = findViewById(R.id.txt_name);
        nameArtist_track = findViewById(R.id.txt_artist);

        // set the circle color of navigation bar
        bottomNavigation.setCircleColor(2);

        // add icon of every part of fragment
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_search_nav));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_music));

        // when user click to the part of navigation bar it will replace the current fragment by other fragment
        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                Fragment fragment = null;
                switch (model.getId())
                {
                    case 1:
                        replace(new HomeFragment());
                        break;
                    case 2:
                        replace(new SearchFragment());
                        break;
                    case 3:
                        replace(new LibraryFragment());
                        break;
                }
                return null;
            }
        });

        // set the count of navigation bar
        bottomNavigation.setCount(1, "10");

        // the first fragment when open this activity
        bottomNavigation.show(1, true);

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                return null;
            }
        });
        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                return null;
            }
        });

        // this will init the music player and attach it with music controller
       PlayMedia();

        // init the favorite icon, 0 if unline and 1 if liked
        final int[] favorite = {0};
        imgv_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favorite[0] == 0)
                {
              //     StartService();
                    // refresh icon
                    imgv_heart.setImageResource(R.drawable.ic_heart_fill);
                    // refresh flag
                    favorite[0] = 1;
                }
                else
                {
                    // refresh icon
                    imgv_heart.setImageResource(R.drawable.ic_not_heart);
                    // refresh flag
                    favorite[0] = 0;
                }
            }
        });

        // Seek to next if user swipe to right
        pvMain.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeRight() {

                player.seekToNext();
            }
            // Seek to previous if user swipe to left
            public void onSwipeLeft() {
                player.seekToPrevious();
            }
        });

        // mapping the view
        ImageView imgv_track = findViewById(R.id.imgv_track);

        // go to other activity if click to image of track in music controller
        imgv_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // this will start the foreground service (notification bar)
               StartService();
               // Start new intent
                Intent intent = new Intent(MainActivity.this, PlayTrackActivity.class);
                startActivity(intent);
                // start new intent with a animation
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });

        // Exoplayer support us many listener
        // this will listen the changing of track
        player.addListener(new Player.Listener() {
            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {


                Player.Listener.super.onTracksChanged(trackGroups, trackSelections);
                Log.e("status", "in Listener");
                if(typePlaying.equals("list"))
                {
                    track = playlist.get(player.getCurrentMediaItemIndex());
                       StartService();
                     //  UpdateService();
                    imgv_track.setImageBitmap(MyService.getBitmapFromURL(track.getImage()));
                    name_track.setText(track.getName());
                    nameArtist_track.setText(track.gettArtist().getNameArtist());
                }
                if(typePlaying.equals("single"))
                {
                         StartService();
                    //   UpdateService();

                    imgv_track.setImageBitmap(MyService.getBitmapFromURL(track.getImage()));
                    name_track.setText(track.getName());
                    nameArtist_track.setText(track.gettArtist().getNameArtist());
                }


            }
        });
    }

    //replace the fragment
    private void replace(Fragment fragment) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction();
    fragmentTransaction.replace(R.id.frame_layout, fragment);
    fragmentTransaction.commit();
    }
    // start forceground service
    public void StartService(){
        Intent intent = new Intent(this, MyService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("track",track);
        intent.putExtras(bundle);
        startService(intent);

    }
    // stop forceground service
    public void StopService()
    {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }
    // int the music player and attach with a controller player
    public void PlayMedia()
    {
        // Mapping the view
        pvMain = findViewById(R.id.pv_main); // creating player view
        // Build the player
        player = new ExoPlayer.Builder(this).build();
        // attach player with music controller
        pvMain.setPlayer(player);
        // set always display
        pvMain.setControllerShowTimeoutMs(0);
        // show controller
        pvMain.showController();
        // disable hiding when touching
        pvMain.setControllerHideOnTouch(false);
    }


}
