package hcmute.edu.vn.spotify.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.spotify.R;

public class SigninActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        //variables
        ImageView backIv = (ImageView) findViewById(R.id.activitySignin_backIv);
        EditText username_et = (EditText) findViewById(R.id.activitySignin_usernameEt);
        EditText password_et = (EditText) findViewById(R.id.activitySignin_passwordEt);
        Button signin_btn = (Button) findViewById(R.id.activitySignin_singinBt);


        //Set on click event
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username_et.getText().toString().equals("hoan123") && password_et.getText().toString().equals("1709"))
                {
                    Toast.makeText(SigninActivity.this, "Login Successfully!", Toast.LENGTH_SHORT);
                    Intent main_activity = new Intent(SigninActivity.this, MainActivity.class);
                    startActivity(main_activity);
                }
                else if(username_et.getText().toString().isEmpty() || password_et.getText().toString().isEmpty())
                {
                    Toast.makeText(SigninActivity.this, "Please enter your username and password!", Toast.LENGTH_SHORT);
                }
                else{
                    Toast.makeText(SigninActivity.this, "Login fail", Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
