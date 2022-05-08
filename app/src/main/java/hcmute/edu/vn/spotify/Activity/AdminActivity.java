package hcmute.edu.vn.spotify.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import hcmute.edu.vn.spotify.Fragment.AdminArtistFragment;
import hcmute.edu.vn.spotify.Fragment.AdminMusicFragment;
import hcmute.edu.vn.spotify.Fragment.UserFragment;
import hcmute.edu.vn.spotify.R;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(AdminActivity.this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.sidebar_nav);
        navigationView.setNavigationItemSelectedListener(AdminActivity.this);

        replaceFragment(new UserFragment());
        navigationView.getMenu().findItem(R.id.nav_user).setChecked(true);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_user:
                replaceFragment(new UserFragment());
                break;
            case R.id.nav_music:
                replaceFragment(new AdminMusicFragment());
                break;
            case R.id.nav_artist:
                replaceFragment(new AdminArtistFragment());
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout2, fragment);
        transaction.commit();
    }
}
