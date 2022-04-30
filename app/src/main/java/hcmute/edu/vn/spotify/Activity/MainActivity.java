package hcmute.edu.vn.spotify.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;

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

import hcmute.edu.vn.spotify.Fragment.LibraryFragment;
import hcmute.edu.vn.spotify.Fragment.SearchFragment;
import hcmute.edu.vn.spotify.Fragment.HomeFragment;
import hcmute.edu.vn.spotify.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class MainActivity extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;
    SimpleExoPlayer absPlayerInternal;
    PlayerView pvMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.meow_bot_nav);


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
        bottomNavigation.show(2, true);

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

        String url = "https://firebasestorage.googleapis.com/v0/b/algebraic-fin-332903.appspot.com/o/track1.mp3?alt=media&token=8d5a909e-c56d-44ad-9ad5-8f148039e2d1";
        PlayMedia(url);
    }
    private void replace(Fragment fragment) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction();

    fragmentTransaction.replace(R.id.frame_layout, fragment);
    fragmentTransaction.commit();
    }

    protected void PlayMedia(String url)
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
