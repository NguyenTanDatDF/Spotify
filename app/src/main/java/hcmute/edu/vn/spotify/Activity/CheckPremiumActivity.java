package hcmute.edu.vn.spotify.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import hcmute.edu.vn.spotify.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CheckPremiumActivity extends AppCompatActivity {
    ImageView backIv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_check);
        // Mapping the view
        backIv = (ImageView) findViewById(R.id.activityPremiumCheck_backIv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get back to previous activity
                finish();
            }
        });
    }
}
