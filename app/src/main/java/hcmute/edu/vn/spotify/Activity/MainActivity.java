package hcmute.edu.vn.spotify.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
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
    MeowBottomNavigation bottomNavigation;
    public static PlayerView pvMain;
    public static ExoPlayer player;
    public static List<Track> playlist ;
    public static int currentIndex;
    public static ImageView img_track;
    public static TextView name_track;
    public static TextView nameArtist_track;
    public static Track track;
    public static String typePlaying;
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.meow_bot_nav);
        ImageButton imgv_heart = findViewById(R.id.imgv_heart);
        img_track = findViewById(R.id.imgv_track);
        name_track = findViewById(R.id.txt_name);
        nameArtist_track = findViewById(R.id.txt_artist);


        bottomNavigation.setCircleColor(2);
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_search_nav));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_music));


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

        bottomNavigation.setCount(1, "10");
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

       PlayMedia();


        final int[] favorite = {0};
        imgv_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favorite[0] == 0)
                {
                  //  StartService();
                    imgv_heart.setImageResource(R.drawable.ic_heart_fill);
                    favorite[0] = 1;
                }
                else
                {
                    imgv_heart.setImageResource(R.drawable.ic_not_heart);
                    favorite[0] = 0;
                }
            }
        });


        pvMain.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeRight() {

                player.seekToNext();
            }
            public void onSwipeLeft() {
                player.seekToPrevious();
            }
        });
        ImageView imgv_track = findViewById(R.id.imgv_track);
        imgv_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartService();
                Intent intent = new Intent(MainActivity.this, PlayTrackActivity.class);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });

        player.addListener(new Player.Listener() {
            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Player.Listener.super.onTracksChanged(trackGroups, trackSelections);

                if(typePlaying.equals("list"))
                {
                    track = playlist.get(player.getCurrentMediaItemIndex());
                    StartService();
                    imgv_track.setImageBitmap(MyService.getBitmapFromURL(track.getImage()));
                    name_track.setText(track.getName());
                    nameArtist_track.setText(track.gettArtist().getNameArtist());
                }
                if(typePlaying.equals("single"))
                {
                    StartService();
                    imgv_track.setImageBitmap(MyService.getBitmapFromURL(track.getImage()));
                    name_track.setText(track.getName());
                    nameArtist_track.setText(track.gettArtist().getNameArtist());
                }


            }
        });
    }
    private void replace(Fragment fragment) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction();
    fragmentTransaction.replace(R.id.frame_layout, fragment);
    fragmentTransaction.commit();
    }

    public void StartService(){
        Intent intent = new Intent(this, MyService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("track",track);
        intent.putExtras(bundle);
        startService(intent);

    }
    public void StopService()
    {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    public void PlayMedia()
    {
        pvMain = findViewById(R.id.pv_main); // creating player view
        player = new ExoPlayer.Builder(this).build();
        pvMain.setPlayer(player);
        pvMain.setControllerShowTimeoutMs(0);
        pvMain.showController();
        pvMain.setControllerHideOnTouch(false);
    }


}
