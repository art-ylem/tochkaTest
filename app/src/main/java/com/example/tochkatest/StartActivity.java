package com.example.tochkatest;

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
import android.widget.TextView;

import com.example.tochkatest.model.vk.Profile;
import com.example.tochkatest.presenter.VkProfilePresenter;
import com.example.tochkatest.view.gitUsers.FragmentGitUsers;
import com.example.tochkatest.view.vkProfile.VkProfileView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

import de.hdodenhof.circleimageview.CircleImageView;

public class StartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, VkProfileView {

    public static final String[] DEFAULT_LOGIN_SCOPE = {VKScope.WALL, VKScope.PHOTOS};
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private TextView name;
    private CircleImageView img;
    private Profile profile;
    private VkProfilePresenter vkProfilePresenter;

    @Override
    public void contactInfo(Profile obj) {
        profile = obj;
        name = findViewById(R.id.account_name);
        img = findViewById(R.id.account_img);
        name.setText(profile.getResponse().get(0).getFirstName() + " " + profile.getResponse().get(0).getLastName());
        Picasso.with(this).load(profile.getResponse().get(0).getPhoto200()).into(img);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        checkAuth();
    }

    public void checkAuth() {
        if (!CurrentUser.isAuthorized()) {
            VKSdk.login(this, DEFAULT_LOGIN_SCOPE);
        } else {
            vkProfilePresenter = new VkProfilePresenter(this);
            vkProfilePresenter.loadData();

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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.changeAccount:

                break;
            case R.id.list_git_users:
                launchFragmentWitchBackStack(FragmentGitUsers.newInstance());
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

    public void launchFragmentWitchBackStack(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


//
}
