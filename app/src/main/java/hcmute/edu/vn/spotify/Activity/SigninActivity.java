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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        List<User> users = getListUser();

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
                for(User user: users){
                    if(username_et.getText().toString().equals(user.getUsername().trim()) && password_et.getText().toString().equals(user.getPassword().trim()))
                    {
                        Toast.makeText(SigninActivity.this, "Login Successfully!", Toast.LENGTH_SHORT);
                        Intent main_activity = new Intent(SigninActivity.this, MainActivity.class);

                        ThreadSafeLazyUserSingleton singleton = ThreadSafeLazyUserSingleton.getInstance(user);

                        //definedUser = user;
                        startActivity(main_activity);
                        break;
                    }
                    else if(username_et.getText().toString().isEmpty() || password_et.getText().toString().isEmpty())
                    {
                        Toast.makeText(SigninActivity.this, "Please enter your username and password!", Toast.LENGTH_SHORT);
                    }
                    else{
                        Toast.makeText(SigninActivity.this, "Login fail", Toast.LENGTH_SHORT);
                    }
                }

            }
        });
    }private List<User> getListUser()
    {
        List<User> list = new ArrayList<>();

        DAOUser daoUser = new DAOUser();
        daoUser.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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

        return list;
    }

}
