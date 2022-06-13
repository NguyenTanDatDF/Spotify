package hcmute.edu.vn.spotify.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
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
    public static SimpleExoPlayer absPlayerInternal;
    public static PlayerView pvMain;
    String url = "https://firebasestorage.googleapis.com/v0/b/algebraic-fin-332903.appspot.com/o/y2mate.com%20-%20Martin%20Garrix%20%20David%20Guetta%20%20So%20Far%20Away%20Official%20Video%20feat%20Jamie%20Scott%20%20Romy%20Dya.mp3?alt=media&token=9b3c3357-6cde-43b0-ac26-e1e22aaf9884";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.meow_bot_nav);
        ImageButton imgv_heart = findViewById(R.id.imgv_heart);

        bottomNavigation.setCircleColor(2);
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_search_nav));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_music));
//        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_person));


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
//                    case 4:
//                        replace(new PersonFragment());
//                        break;
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

        PlayMedia(url);


        final int[] favorite = {0};
        imgv_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favorite[0] == 0)
                {
                    StartService();
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
                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                absPlayerInternal.stop();
                PlayMedia("https://firebasestorage.googleapis.com/v0/b/algebraic-fin-332903.appspot.com/o/y2mate.com%20-%20The%20Kid%20LAROI%20Justin%20Bieber%20%20Stay%20Lyrics.mp3?alt=media&token=4cce8bd1-a714-43cc-b1fb-8a9a293f4e26");
            }
            public void onSwipeLeft() {
                absPlayerInternal.stop();
                PlayMedia("https://firebasestorage.googleapis.com/v0/b/algebraic-fin-332903.appspot.com/o/y2mate.com%20-%20dhruv%20%20double%20take%20Official%20Audio.mp3?alt=media&token=db669883-f41f-4d28-8363-cd451749e17d");
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
        });
        ImageView imgv_track = findViewById(R.id.imgv_track);
        imgv_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayTrackActivity.class);

                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });


        DatabaseReference storageRef = FirebaseDatabase.getInstance("https://admin-sportify-default-rtdb.firebaseio.com/").getReference();
        List<String> list = new ArrayList<>();
        list.add("Cho");
        list.add("Meo");
        list.add("Khi");
        for(int i = 0 ; i < 3; i ++)
        {
           // storageRef.child("Animal").push().setValue(list.get(i));
        }
         Log.e("Key",storageRef.child("Animal").getKey());
    }
    private void replace(Fragment fragment) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction();
    fragmentTransaction.replace(R.id.frame_layout, fragment);
    fragmentTransaction.commit();
    }

    public void StartService(){
        Track track = new Track("1", "1", "https://avatar-ex-swe.nixcdn.com/song/2017/12/01/4/c/0/3/1512117950090_640.jpg","So far away", url,"US UK", 123, "werqe", "");
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


    public void PlayMedia(String url)
    {
        String CONTENT_URL = url;

        int appNameStringRes = R.string.app_name;

        pvMain = findViewById(R.id.pv_main); // creating player view
        pvMain.setBackgroundDrawable(getDrawable(R.drawable.album1));

        TrackSelector trackSelectorDef = new DefaultTrackSelector();
        absPlayerInternal = ExoPlayerFactory.newSimpleInstance(this, trackSelectorDef); //creating a player instance

        String userAgent = Util.getUserAgent(this, this.getString(appNameStringRes));
        DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(this,userAgent);
        Uri uriOfContentUrl = Uri.parse(CONTENT_URL);
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(uriOfContentUrl);  // creating a media source

        absPlayerInternal.prepare(mediaSource);



        absPlayerInternal.setPlayWhenReady(true); // start loading video and play it at the moment a chunk of it is available offline (start and play immediately)


        pvMain.setPlayer(absPlayerInternal); // attach surface to the view
        pvMain.setControllerShowTimeoutMs(0);

        pvMain.showController();
        pvMain.setControllerHideOnTouch(false);
    }



}
