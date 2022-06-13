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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditUserActivity extends AppCompatActivity {

    ImageView cancelIv;
    TextView saveTv;
    EditText username_et;
    DAOUser daoUser = new DAOUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        User user = new User();

        username_et  = (EditText) findViewById(R.id.activityEditUser_editNameEt);
        username_et.setText(SigninActivity.definedUser.getName());

        cancelIv = (ImageView) findViewById(R.id.activityEditUser_cancleIv);
        cancelIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveTv = (TextView) findViewById(R.id.activityEditUser_saveTv);
        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = username_et.getText().toString();
                user.setName(newName);
                daoUser.updateUser(user).addOnSuccessListener(suc-> {
                    Toast.makeText(EditUserActivity.this, "Update user successfully!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er->{
                    Toast.makeText(EditUserActivity.this, er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

    }
}
