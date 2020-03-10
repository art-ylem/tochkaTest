package com.example.tochkatest.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment

import android.view.MenuItem
import android.widget.TextView
import com.example.tochkatest.R
import com.example.tochkatest.model.User

import com.example.tochkatest.view.gitUsers.FragmentGitUsers
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso

import de.hdodenhof.circleimageview.CircleImageView

class StartActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var drawerLayout: DrawerLayout? = null
    private var toolbar: Toolbar? = null
    private var toggle: ActionBarDrawerToggle? = null
    private var navigationView: NavigationView? = null
    private var name: TextView? = null
    private var img: CircleImageView? = null
    private var user: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        init()
        user = intent.getSerializableExtra("USER") as User
        name!!.text = user!!.name
        Picasso.with(this).load(user!!.url).into(img)
    }

    private fun init() {

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer
        )
        drawerLayout!!.addDrawerListener(toggle!!)
        toggle!!.syncState()

        navigationView = findViewById(R.id.nav_view)
        navigationView!!.setNavigationItemSelectedListener(this)

        val headerView = navigationView!!.getHeaderView(0)
        name = headerView.findViewById(R.id.account_name)
        img = headerView.findViewById(R.id.account_img)

        launchFragment(FragmentGitUsers.newInstance())

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.changeAccount -> startActivity(Intent(this@StartActivity, MainActivity::class.java))
            R.id.list_git_users -> launchFragment(FragmentGitUsers.newInstance())

            else -> return true
        }
        drawerLayout!!.closeDrawer(GravityCompat.START)
        return false
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else
            super.onBackPressed()
    }

    private fun launchFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container_frame, fragment)
        fragmentTransaction.commit()

    }

}
