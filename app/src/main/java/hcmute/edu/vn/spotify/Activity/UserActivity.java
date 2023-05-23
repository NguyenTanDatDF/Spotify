package hcmute.edu.vn.spotify.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import hcmute.edu.vn.spotify.Database.DAOPlaylist;
import hcmute.edu.vn.spotify.Model.User;
import hcmute.edu.vn.spotify.R;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Adapter.PlaylistAdapter;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.Service.ThreadSafeLazyUserSingleton;

public class UserActivity extends AppCompatActivity {

    //Edit button
    Button editUserBt;
    //Back to previous activity
    ImageView backIv;

    //Recycle view user playlist
    private RecyclerView rcvPlaylist;
    //Playlist adapter
    private PlaylistAdapter playlistAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //create view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //set user name
        TextView username_tv = (TextView) findViewById(R.id.activityUser_usernameTv);

        //defined new user
        User user = new User();

        //user = SigninActivity.definedUser;
        ThreadSafeLazyUserSingleton singleton = ThreadSafeLazyUserSingleton.getInstance(user);
        user = singleton.user;
        username_tv.setText(user.getName());

        //Edit user information
        editUserBt = (Button) findViewById(R.id.activityUser_editUserBtn);
        editUserBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editUser_activity = new Intent(UserActivity.this, EditUserActivity.class);
                startActivity(editUser_activity);
            }
        });

        //Back btn
        backIv = (ImageView) findViewById(R.id.activityUser_backIv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Show data of user's playlist
        rcvPlaylist = findViewById(R.id.activityUser_listPlaylistRv);
        playlistAdapter = new PlaylistAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvPlaylist.setLayoutManager(linearLayoutManager);
        playlistAdapter.setData(getListPlaylist());
        rcvPlaylist.setAdapter(playlistAdapter);
    }

    //Get all user's playlist
    private List<Playlist> getListPlaylist() {
        List<Playlist> list = new ArrayList<>();

        DAOPlaylist daoPlaylist = new DAOPlaylist();
        daoPlaylist.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Playlist playlist = data.getValue(Playlist.class);
                    list.add(playlist);
                    String key = data.getKey();
                    playlist.setKey(key);
                }
                playlistAdapter.setData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }

    public static class Videoplaying extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_videoplaying);
        }
    }
}
