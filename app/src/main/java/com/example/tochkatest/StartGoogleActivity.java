package com.example.tochkatest;

import android.content.Intent;
import android.net.Uri;
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

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tochkatest.view.gitUsers.FragmentGitUsers;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.vk.sdk.VKScope;

import de.hdodenhof.circleimageview.CircleImageView;

public class StartGoogleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String[] DEFAULT_LOGIN_SCOPE = {VKScope.WALL, VKScope.PHOTOS};
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private TextView name;
    private CircleImageView img;
    private GoogleSignInClient mGoogleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_google);



        init();
        launchFragment(FragmentGitUsers.newInstance());

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    Toast.makeText(StartGoogleActivity.this,"Signed out id successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }

    public void init() {



        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String personGivenName = acct.getGivenName();
        String personFamilyName = acct.getFamilyName();
        Uri personPhoto = acct.getPhotoUrl();




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

        View headerView = navigationView.getHeaderView(0);

        name = headerView.findViewById(R.id.account_name);
        img = headerView.findViewById(R.id.account_img);

        name.setText(personGivenName + " " + personFamilyName);
        Picasso.with(this).load(personPhoto).into(img);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.changeAccount:
                startActivity(new Intent(StartGoogleActivity.this, MainActivity.class));
                signOut();
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


//
}
