package hcmute.edu.vn.spotify.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import hcmute.edu.vn.spotify.R;

public class SettingActivity extends AppCompatActivity {

    LinearLayout rules;
    LinearLayout support;
    LinearLayout thirdside;
    LinearLayout terms;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

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
