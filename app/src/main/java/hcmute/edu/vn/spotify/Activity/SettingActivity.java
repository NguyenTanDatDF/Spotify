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
import hcmute.edu.vn.spotify.Service.ThreadSafeLazyUserSingleton;

public class SettingActivity extends AppCompatActivity {

    //variables (Declare all the component in Setting activity)

    // layout for rules,users support, ...
    LinearLayout rules;
    LinearLayout support;
    LinearLayout thirdside;
    LinearLayout terms;

    //User information component
    ConstraintLayout userCl;
    //Back to previous activity
    ImageView backIv;
    //Check premium
    LinearLayout premiumLl;
    //Log out
    LinearLayout logoutLl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Handle activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //Define user object using singleton
        User user = new User();
        //user = SigninActivity.definedUser;
         ThreadSafeLazyUserSingleton singleton = ThreadSafeLazyUserSingleton.getInstance(user);
        user = singleton.user;

        //Set data for user by name, email
        TextView name_tv = (TextView) findViewById(R.id.activitySetting_usernameTv);
        name_tv.setText(user.getName().trim());
        TextView email_tv = (TextView) findViewById(R.id.activitySetting_emailTv);
        email_tv.setText(user.getEmail().trim());

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

        //Logout
        logoutLl = (LinearLayout) findViewById(R.id.activitySetting_logoutLl);
        logoutLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent welcome_activity = new Intent(SettingActivity.this, WelcomeActivity.class);
                startActivity(welcome_activity);
                MainActivity.logout = true;
            }
        });


        // Go to webpage written by react (User's rules, support, ...)
        rules = findViewById(R.id.activitySetting_rulesLl);
        support = findViewById(R.id.activitySetting_supportLl);
        thirdside = findViewById(R.id.activitySetting_thirdPartLl);
        terms = findViewById(R.id.activitySetting_termsLl);

        //Go to rules website
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://spotify-seven-sepia.vercel.app/rules");
            }
        });

        //Go to support website
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.facebook.com/SpotifyVietnam");
            }
        });

        //Go to thirdside webpage
        thirdside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://spotify-seven-sepia.vercel.app/third-side");
            }
        });

        //Go to terms webpage
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://spotify-seven-sepia.vercel.app/terms-and-privacy");
            }
        });


    }

    //Function to go to webpage URL
    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}
