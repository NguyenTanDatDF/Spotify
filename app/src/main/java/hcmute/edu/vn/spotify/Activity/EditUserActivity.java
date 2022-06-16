package hcmute.edu.vn.spotify.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.spotify.Database.DAOUser;
import hcmute.edu.vn.spotify.Model.User;
import hcmute.edu.vn.spotify.R;
import hcmute.edu.vn.spotify.Service.ThreadSafeLazyUserSingleton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditUserActivity extends AppCompatActivity {
    // Declare the view
    ImageView cancelIv;
    TextView saveTv;
    EditText username_et;
    // Create new daoUser to get data
    DAOUser daoUser = new DAOUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        //Create a user as a fake parameter of Singleton
        User user = new User();
        // Mapping the view
        username_et  = (EditText) findViewById(R.id.activityEditUser_editNameEt);

        //user = SigninActivity.definedUser;
        // Use Singleton to get the global logging user
        ThreadSafeLazyUserSingleton singleton = ThreadSafeLazyUserSingleton.getInstance(user);
        user = singleton.user;
        // set text to user name view
        username_et.setText(user.getName());
        // Mapping the view
        cancelIv = (ImageView) findViewById(R.id.activityEditUser_cancleIv);

        cancelIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get back to previous avivity
                finish();
            }
        });

        // Mapping the view
        saveTv = (TextView) findViewById(R.id.activityEditUser_saveTv);

        // create new temp user to pass to the fuction
        User finalUser = user;
        // when click this button the information will save to firebase by updateUser in daoUser class
        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = username_et.getText().toString();
                finalUser.setName(newName);
                daoUser.updateUser(finalUser).addOnSuccessListener(suc-> {
                    Toast.makeText(EditUserActivity.this, "Update user successfully!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er->{
                    Toast.makeText(EditUserActivity.this, er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

    }
}
