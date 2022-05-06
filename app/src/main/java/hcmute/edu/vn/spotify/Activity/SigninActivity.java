package hcmute.edu.vn.spotify.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.spotify.R;

public class SigninActivity extends AppCompatActivity {
    ImageView backIv;
    Button signInBt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        backIv = (ImageView) findViewById(R.id.activitySignin_backIv);
        signInBt  = (Button) findViewById(R.id.activitySignin_singinBt);

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signInBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_activity = new Intent(SigninActivity.this, MainActivity.class);
                startActivity(main_activity);
            }
        });
    }
}
