package hcmute.edu.vn.spotify.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import hcmute.edu.vn.spotify.R;
public class PlayTrackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_track);
        TextView textView25 = findViewById(R.id.textView25);
        textView25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.animateSlideDown(PlayTrackActivity.this);
            }
        });
    }
}