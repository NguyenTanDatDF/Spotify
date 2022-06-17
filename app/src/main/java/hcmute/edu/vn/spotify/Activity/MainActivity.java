package hcmute.edu.vn.spotify.Activity;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Database.DAOPlayListTrack;
import hcmute.edu.vn.spotify.Database.DAOPlaylist;
import hcmute.edu.vn.spotify.Database.DAOTrack;
import hcmute.edu.vn.spotify.Fragment.LibraryFragment;
import hcmute.edu.vn.spotify.Fragment.SearchFragment;
import hcmute.edu.vn.spotify.Fragment.HomeFragment;
import hcmute.edu.vn.spotify.Model.Favorite;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.Model.PlaylistTrack;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.Model.User;
import hcmute.edu.vn.spotify.R;
import hcmute.edu.vn.spotify.Service.MyService;
import hcmute.edu.vn.spotify.Service.OnSwipeTouchListener;
import hcmute.edu.vn.spotify.Service.ThreadSafeLazyUserSingleton;
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

    public static boolean isHeartTrack;

    // this is current track is playing, if user playing a playlist this track will be set as null
    public static Track track;

    // It mean the type of playing, it can be "single" (a track is playing) or "list" (a playlist is playing)
    public static String typePlaying;

    // get the status of login or logout
    public static boolean logout = false;

    // current playlist of user
    public static List<Track> userTrackList;

    List<PlaylistTrack> allPlayListTrack = getPlaylistTrack();
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

        // get user by singleton pattern
        User user = new User();
        ThreadSafeLazyUserSingleton singleton = ThreadSafeLazyUserSingleton.getInstance(user);
        user = singleton.user;


        // this will init the music player and attach it with music controller
       PlayMedia();
       // connect with firebase
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://admin-sportify-default-rtdb.firebaseio.com/");
        DatabaseReference databaseReference = db.getReference("favoriteHeart");
        // init the favorite icon, 0 if unline and 1 if liked




        // Ví dụ prototype
//        Favorite favorite1 = new Favorite("Dat","Dat");
//
//        //Favorite favorite2 = favorite1;
//
//        Favorite favorite2 = (Favorite) favorite1.createClone();
//
//
//        favorite1.setTid("Hoan");
//        favorite1.setUid("Hoan");
//
//        Log.e(favorite1.getUid(),favorite1.getTid());
//        Log.e(favorite2.getUid(),favorite2.getTid());



        final int[] favorite = {0};
        User finalUser1 = user;
        User finalUser = user;
        DAOPlaylist daoPlaylist = new DAOPlaylist();

        // the heart icon in the first time
        if(track!=null)
        {
            getUserListTrack(user.getUserId(),user.getUserId(), imgv_heart, track);
        }


        User finalUser3 = user;
        DAOPlayListTrack daoPlayListTrack =  new DAOPlayListTrack();

        // When you click on the heart icon check it's current state by tag and
        // add to song of user if user has not like and remove if double tap
        imgv_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(getDrawableId(imgv_heart)==R.drawable.ic_not_heart)
                {
              //     StartService();
                    // refresh icon
                    imgv_heart.setImageResource(R.drawable.ic_heart_fill);
                    // refresh flag
                    // Create default format of playlist

                    PlaylistTrack newTrack = new PlaylistTrack(randomId() ,track.getTrackId().trim(), finalUser3.getUserId());
                    daoPlayListTrack.addNewPlaylistTrack(newTrack).addOnSuccessListener(suc -> {

                    }).addOnFailureListener(err -> {

                    });

                }
                else
                {
                    // refresh icon
                    imgv_heart.setImageResource(R.drawable.ic_not_heart);
                    // refresh flag
                    for(PlaylistTrack playlistTrack: allPlayListTrack){
                        if(track.getTrackId().trim().equals(playlistTrack.getTrackId().trim())){
                            daoPlayListTrack.removePlaylistTrack(playlistTrack.getKey().trim()).addOnSuccessListener(suc -> {
                            }).addOnFailureListener(err -> {
                            });
                        }
                    }
                }

                // attach with first child

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
        User finalUser2 = user;
        player.addListener(new Player.Listener() {
            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {



                Player.Listener.super.onTracksChanged(trackGroups, trackSelections);
                Log.e("status", "in Listener");
                if(typePlaying.equals("list"))
                {
                    track = playlist.get(player.getCurrentMediaItemIndex());

                    getUserListTrack(finalUser2.getUserId(),finalUser2.getUserId(), imgv_heart,track);

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
                    getUserListTrack(finalUser2.getUserId(),finalUser2.getUserId(), imgv_heart,track);

                    imgv_track.setImageBitmap(MyService.getBitmapFromURL(track.getImage()));
                    name_track.setText(track.getName());
                    nameArtist_track.setText(track.gettArtist().getNameArtist());
                }


            }
        });
    }

    // Hash the id of the user when create
    public String randomId() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 15; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
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
    // get the tag
    private int getDrawableId(ImageView iv) {
        return (Integer) iv.getTag();
    }

    //Get all list track that belong to one user
    private List<Track> getUserListTrack(String userId, String playlistId, ImageView imgv_heart, Track track1)
    {
        List<Track> list = new ArrayList<>();
        List<PlaylistTrack> playlistTrack = getPlaylistTrack();
        DAOTrack daoTrack = new DAOTrack();
        daoTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               // list.clear();

                    User user = new User();

                    //user = SigninActivity.definedUser;
                    ThreadSafeLazyUserSingleton singleton = ThreadSafeLazyUserSingleton.getInstance(user);
                    user = singleton.user;

                    // check in data if the track in the song of user then show the fill heart and
                    // empty heart if don't have
                    for(PlaylistTrack playlistTrack1: playlistTrack)
                    {
                        if(userId.equals(user.getUserId().trim()) && playlistId.equals(playlistTrack1.getPlaylistId().trim()) && track.getTrackId().trim().equals(playlistTrack1.getTrackId().trim()) )
                        {
                            imgv_heart.setImageResource(R.drawable.ic_heart_fill);
                            imgv_heart.setTag(R.drawable.ic_heart_fill);
                            break;
                        }
                        else
                        {
                            imgv_heart.setImageResource(R.drawable.ic_not_heart);
                            imgv_heart.setTag(R.drawable.ic_not_heart);

                        }
                    }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return list;
    }

    // when get back to activity we need to update the heart icon
    @Override
    protected void onResume() {
        super.onResume();
        User user = new User();
        ImageView imgv_heart = findViewById(R.id.imgv_heart);
        //user = SigninActivity.definedUser;
        ThreadSafeLazyUserSingleton singleton = ThreadSafeLazyUserSingleton.getInstance(user);
        user = singleton.user;
        if(track!=null)
        {
            getUserListTrack(user.getUserId(),user.getUserId(), imgv_heart, track);
        }
    }

    // get all playlist by id user
    private List<Playlist> getListPlaylist(String userId) {
        List<Playlist> list = new ArrayList<>();

        DAOPlaylist daoPlaylist = new DAOPlaylist();
        daoPlaylist.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Playlist playlist = data.getValue(Playlist.class);
                    if(playlist.getuID().trim().equals(userId)){
                        list.add(playlist);
                        String key = data.getKey();
                        playlist.setKey(key);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return list;
    }

    // all playlist track
    private List<PlaylistTrack> getPlaylistTrack()
    {
        List<PlaylistTrack> list = new ArrayList<>();
        DAOPlayListTrack daoPlayListTrack = new DAOPlayListTrack();
        daoPlayListTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot data: snapshot.getChildren()){
                    PlaylistTrack playlistTrack = data.getValue(PlaylistTrack.class);
                    list.add(playlistTrack);
                    String key = data.getKey();
                    playlistTrack.setKey(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }
}
