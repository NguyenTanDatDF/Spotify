package hcmute.edu.vn.spotify.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.spotify.R;

public class WelcomeActivity extends AppCompatActivity {
    Button signup_btn;
    Button signin_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //come to sign up activity
        signup_btn = (Button) findViewById(R.id.activityWelcome_signUpBt);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup_activity = new Intent(WelcomeActivity.this, SignupActivity.class);
                startActivity(signup_activity);
            }
        });

        //come to sign in activity
        signin_btn = (Button) findViewById(R.id.activityWelcome_signInBt);
        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signin_activity = new Intent(WelcomeActivity.this, SigninActivity.class);
                startActivity(signin_activity);
            }
        });
    }
}

