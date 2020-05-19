package com.example.farmersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class ExploreActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final float END_SCALE = 0.7f;
    BottomNavigationView bottomNavigation;

    //Side Navigation Drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ConstraintLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setItemIconTintList(null);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(HomeFragment.newInstance("", ""));

        //Side Navigation Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        contentView =findViewById(R.id.content);

        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.home_bottomNavigation:
                            openFragment(HomeFragment.newInstance("", ""));
                            return true;
                        case R.id.marketPlace_bottomNavigation:
                            openFragment(MarketFragment.newInstance("", ""));
                            return true;
                        case R.id.blog_bottomNavigation:
                            openFragment(BlogFragment.newInstance("", ""));
                            return true;

                        case R.id.vehicleTrack_bottomNavigation:
                            openFragment(VehicleFragment.newInstance("", ""));
                            return true;
                        case R.id.more_bottomNavigation:
//                            openFragment(MoreFragment.newInstance("", ""));
                            if(drawerLayout.isDrawerVisible(GravityCompat.START)) {
                                drawerLayout.closeDrawer(GravityCompat.START);
                            } else {
                                drawerLayout.openDrawer(GravityCompat.START);
                            }
                            return true;
                    }
                    return false;
                }
            };

    //Navigation Drawer Functions
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_userProfile:
                openFragment(UserProfileFragment.newInstance("", ""));
                break;
            case R.id.nav_aboutUs:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
