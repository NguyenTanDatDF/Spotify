package hcmute.edu.vn.spotify.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.spotify.R;

public class SignupActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Variables
        EditText username_et = (EditText) findViewById(R.id.activitySignup_usernameEt);
        EditText password_et = (EditText) findViewById(R.id.activitySignup_passwordEt);
        Button signup_btn = (Button) findViewById(R.id.activitySignup_signupBtn);
        ImageView backIv = (ImageView) findViewById(R.id.activitySignup_backIv);

        //set onclick event
        //back to welcome page
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //sign up event
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username_et.getText().toString() != "hoan123"){

                }
            }
        });

    }
}
