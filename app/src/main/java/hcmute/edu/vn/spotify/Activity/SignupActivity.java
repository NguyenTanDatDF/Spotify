package hcmute.edu.vn.spotify.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import hcmute.edu.vn.spotify.Database.DAOPlaylist;
import hcmute.edu.vn.spotify.Database.DAOUser;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.Model.User;
import hcmute.edu.vn.spotify.R;

public class SignupActivity extends AppCompatActivity {

    DAOUser daoUser = new DAOUser();
    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



        //Variables
        EditText username_et = (EditText) findViewById(R.id.activitySignup_usernameEt);
        EditText password_et = (EditText) findViewById(R.id.activitySignup_passwordEt);
        EditText name_et = (EditText) findViewById(R.id.activitySignup_nameEt);
        EditText mail_et = (EditText) findViewById(R.id.activitySignup_emailEt);
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
        DAOPlaylist daoPlaylist = new DAOPlaylist();
        //sign up event
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!username_et.getText().toString().isEmpty() || !password_et.getText().toString().isEmpty() || !name_et.getText().toString().isEmpty() || !mail_et.getText().toString().isEmpty()){


                    User user = new User(randomId(), username_et.getText().toString(), password_et.getText().toString(), name_et.getText().toString(), mail_et.getText().toString(), false, false);


                     User cloneUser = (User) user.createClone();

                     // hạn chế một số trường hợp Id bị thay đổi

                    daoUser.addNewUser(user).addOnSuccessListener(suc -> {
                        Playlist playlist = new Playlist("https://img.freepik.com/free-vector/playlist-neon-sign-black-brick-wall_77399-755.jpg?w=2000", cloneUser.getUserId().toString(), "Song of " + cloneUser.getUsername(), cloneUser.getUsername().toString(), cloneUser.getUserId());

                        // add playlist to firebase by daoPlaylist.addNewPlaylist and listen when it successful or fail
                        daoPlaylist.addNewPlaylist(playlist);
                        Toast.makeText(SignupActivity.this, "Sign up new user successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }).addOnFailureListener(err -> {
                        Toast.makeText(SignupActivity.this, "Sign up failed!", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });

    }


    //Create random id for user
    public String randomId(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 20; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
