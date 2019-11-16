package com.example.finalp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.finalp.ui.gallery.GalleryFragment;
import com.example.finalp.ui.home.HomeFragment;
import com.example.finalp.ui.send.SendFragment;
import com.example.finalp.ui.share.ShareFragment;
import com.example.finalp.ui.slideshow.SlideshowFragment;
import com.example.finalp.ui.tools.ToolsFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback{

    private AppBarConfiguration mAppBarConfiguration;

    HomeFragment homefragment;
    GalleryFragment galleryfragment;
    SlideshowFragment slideshowfragment;
    ToolsFragment toolsfragment;
    ShareFragment sharefragment;
    SendFragment sendfragment;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

         */

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        homefragment= new HomeFragment();
        galleryfragment = new GalleryFragment();
        slideshowfragment = new SlideshowFragment();
        toolsfragment = new ToolsFragment();
        sharefragment = new ShareFragment();
        sendfragment = new SendFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.container,galleryfragment).commit();
        /*
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

         */
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //네비게이션 아이템 선택
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        String title = getString(R.string.app_name);

        if (id == R.id.nav_home) {
            Toast.makeText(this, "home 메뉴 선택됨",Toast.LENGTH_LONG).show();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this, "gallery 메뉴 선택됨",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(this, "slideshow 메뉴 선택됨",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "send 메뉴 선택됨",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "share 메뉴 선택됨",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_tools) {
            Toast.makeText(this, "tools 메뉴 선택됨",Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentSelected(int position, Bundle bundle) {
        Fragment curFragment = null;

        if (position == 0) {
            curFragment = homefragment;
            toolbar.setTitle("첫번째 화면");
        } else if (position == 1) {
            curFragment = galleryfragment;
            toolbar.setTitle("두번째 화면");
        } else if (position == 2) {
            curFragment = slideshowfragment;
            toolbar.setTitle("세번째 화면");
        }
        else if (position == 3) {
            curFragment = toolsfragment;
            toolbar.setTitle("네번째 화면");
        }
        else if (position == 4) {
            curFragment = sharefragment;
            toolbar.setTitle("다섯번째 화면");
        }else if (position == 5) {
            curFragment = sendfragment;
            toolbar.setTitle("여섯번째 화면");
        }


        getSupportFragmentManager().beginTransaction().replace(R.id.container, curFragment).commit();
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    */

}
