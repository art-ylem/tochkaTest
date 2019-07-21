package com.example.tochkatest;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.tochkatest.view.gitUsers.FragmentGitUsers;
import com.facebook.AccessToken;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class StartFBActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private TextView name;
    private CircleImageView img;
    private AccessToken accessToken;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_fb);


        init();

        View headerView = navigationView.getHeaderView(0);

        name = headerView.findViewById(R.id.account_name);
        img = headerView.findViewById(R.id.account_img);

        name.setText(getIntent().getStringExtra("nameFB"));
        Picasso.with(this).load(getIntent().getStringExtra("imageFB")).into(img);
        launchFragment(FragmentGitUsers.newInstance());


    }



    public void init() {
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            drawerLayout = findViewById(R.id.drawer_layout);
            toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                    R.string.nav_open_drawer,
                    R.string.nav_close_drawer
            );
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.changeAccount:
                startActivity(new Intent(StartFBActivity.this, MainActivity.class));
                break;
            case R.id.list_git_users:
                launchFragment(FragmentGitUsers.newInstance());
                break;

            default:
                return true;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return false;

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else super.onBackPressed();

    }

    public void launchFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_frame, fragment);
        fragmentTransaction.commit();

    }

    //fb



//
}
