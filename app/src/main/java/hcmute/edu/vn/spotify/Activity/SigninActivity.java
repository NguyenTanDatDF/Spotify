package hcmute.edu.vn.spotify.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Database.DAOTrack;
import hcmute.edu.vn.spotify.Database.DAOUser;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.Model.User;
import hcmute.edu.vn.spotify.R;
import hcmute.edu.vn.spotify.Service.ThreadSafeLazyUserSingleton;

public class SigninActivity extends AppCompatActivity {
    //private static User definedUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Create view for sign in activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //Get list user
        List<User> users = getListUser();

        //variables
        //back to previous activity (Welcome)
        ImageView backIv = (ImageView) findViewById(R.id.activitySignin_backIv);
        //Get username value
        EditText username_et = (EditText) findViewById(R.id.activitySignin_usernameEt);
        //Get password value
        EditText password_et = (EditText) findViewById(R.id.activitySignin_passwordEt);
        //Sign in button
        Button signin_btn = (Button) findViewById(R.id.activitySignin_singinBt);

        Log.e("context",  getClass().getSimpleName());

        //Set on click event for back button
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Sign in with username and password
        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //First get all user from firebase after that check if the username and password are correct or not
                for(User user: users){
                    if(username_et.getText().toString().equals(user.getUsername().trim()) && password_et.getText().toString().equals(user.getPassword().trim()))
                    {
                        //If the username and password is correct, show the toast and move to app
                        Toast.makeText(SigninActivity.this, "Login Successfully!", Toast.LENGTH_SHORT);
                        Intent main_activity = new Intent(SigninActivity.this, MainActivity.class);

                        // Also get the user information to use for another activity
                        ThreadSafeLazyUserSingleton singleton = ThreadSafeLazyUserSingleton.getInstance(user);

                        MainActivity.logout = false;
                        //definedUser = user;
                        startActivity(main_activity);
                        break;
                    }
                    else if(username_et.getText().toString().isEmpty() || password_et.getText().toString().isEmpty())
                    {
                        //IF the field is blank, please enter value
                        Toast.makeText(SigninActivity.this, "Please enter your username and password!", Toast.LENGTH_SHORT);
                    }
                    else{
                        //else, cannot login
                        Toast.makeText(SigninActivity.this, "Login fail", Toast.LENGTH_SHORT);
                    }
                }

            }
        });
    }

    //Get list user from database
    private List<User> getListUser()
    {
        //Define list user
        List<User> list = new ArrayList<>();

        //Call DAO user method from Database
        DAOUser daoUser = new DAOUser();
        daoUser.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //add data to list user
                for(DataSnapshot data: snapshot.getChildren()){
                    User user = data.getValue(User.class);
                    list.add(user);
                    String key = data.getKey();
                    user.setKey(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Return list of user
        return list;
    }

}
