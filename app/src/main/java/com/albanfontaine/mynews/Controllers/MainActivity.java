package com.albanfontaine.mynews.Controllers;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.albanfontaine.mynews.R;
import com.albanfontaine.mynews.Views.PageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.activity_main_drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.activity_main_nav_view) NavigationView mNavigationView;
    @BindView(R.id.activity_main_viewpager) ViewPager mPager;
    @BindView(R.id.activity_main_tabs) TabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Configuration of the different views
        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(this.mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    // Setup
    private void configureToolbar(){
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    private void configureDrawerLayout(){
        ButterKnife.bind(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView(){
        ButterKnife.bind(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        // Start with the first item highlighted
        this.mNavigationView.getMenu().getItem(0).setChecked(true);
    }

    private void configureViewPager(){
        ButterKnife.bind(this);
        mPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        mTabs.setupWithViewPager(mPager);
        // Set tabs design
        mTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        // Updates the Navigation Drawer highlighted item on tab change
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            @Override
            public void onPageSelected(int i) {
                mNavigationView.getMenu().getItem(i).setChecked(true);
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Changes the ViewPager to the selected tab from the Navigation Drawer
        switch (item.getItemId()){
            case R.id.activity_main_drawer_top_stories:
                this.mPager.setCurrentItem(0);
                this.getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_viewpager, MainFragment.newInstance(0)).commit();
                break;
            case R.id.activity_main_drawer_most_popular:
                this.mPager.setCurrentItem(1);
                this.getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_viewpager, MainFragment.newInstance(1)).commit();
                break;
            case R.id.activity_main_drawer_arts:
                this.mPager.setCurrentItem(2);
                this.getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_viewpager, MainFragment.newInstance(2)).commit();
                break;
            case R.id.activity_main_drawer_business:
                this.mPager.setCurrentItem(3);
                this.getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_viewpager, MainFragment.newInstance(3)).commit();
                break;
            case R.id.activity_main_drawer_politics:
                this.mPager.setCurrentItem(4);
                this.getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_viewpager, MainFragment.newInstance(4)).commit();
                break;
            case R.id.activity_main_drawer_travel:
                this.mPager.setCurrentItem(5);
                this.getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_viewpager, MainFragment.newInstance(5)).commit();
                break;
            default:
                break;
        }
        item.setChecked(true);
        this.mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Toolbar icons
        switch (item.getItemId()){
            case R.id.toolbar_search:
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                this.startActivity(searchIntent);
                return true;
            case R.id.toolbar_notifications:
                Intent notificationsIntent = new Intent(MainActivity.this, NotificationsActivity.class);
                this.startActivity(notificationsIntent);
                return true;
            case R.id.toolbar_help:
                // TODO
                return true;
            case R.id.toolbar_about:
                this.showAboutDialog();
                return true;
            default:
                return true;
        }
    }

    private void showAboutDialog(){
        // Builds an AlertDialog then shows it
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("About");
        alert.setMessage("This app was made by\nAlban Fontaine");
        alert.setPositiveButton("OK", null);
        alert.show();
    }
}
