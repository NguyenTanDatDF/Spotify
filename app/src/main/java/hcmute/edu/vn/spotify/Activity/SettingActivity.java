package hcmute.edu.vn.spotify.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Database.DAOPlaylist;
import hcmute.edu.vn.spotify.Database.DAOUser;
import hcmute.edu.vn.spotify.Model.Album;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.Model.User;
import hcmute.edu.vn.spotify.R;

public class SettingActivity extends AppCompatActivity {

    LinearLayout rules;
    LinearLayout support;
    LinearLayout thirdside;
    LinearLayout terms;
    ConstraintLayout userCl;
    ImageView backIv;
    LinearLayout premiumLl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        User user = new User();
        user = SigninActivity.definedUser;
        Log.e(user.getName().trim(), "Blaaa");
//        TextView name_tv = (TextView) findViewById(R.id.activitySetting_usernameTv);
//        name_tv.setText(user.getEmail().trim());


        //go to user activity
        userCl = (ConstraintLayout) findViewById(R.id.activitySetting_userCl);
        userCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent user_activity = new Intent(SettingActivity.this, UserActivity.class);
                startActivity(user_activity);
            }
        });
        //back to fragment
        backIv = (ImageView) findViewById(R.id.activitySetting_backIv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Check premium
        premiumLl = (LinearLayout) findViewById(R.id.activitySetting_premiumLl);
        premiumLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent premium_activity = new Intent(SettingActivity.this, CheckPremiumActivity.class);
                startActivity(premium_activity);
            }
        });

        rules = findViewById(R.id.activitySetting_rulesLl);
        support = findViewById(R.id.activitySetting_supportLl);
        thirdside = findViewById(R.id.activitySetting_thirdPartLl);
        terms = findViewById(R.id.activitySetting_termsLl);

        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://spotify-seven-sepia.vercel.app/rules");
            }
        });
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.facebook.com/SpotifyVietnam");
            }
        });
        thirdside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://spotify-seven-sepia.vercel.app/third-side");
            }
        });
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://spotify-seven-sepia.vercel.app/terms-and-privacy");
            }
        });


    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}
