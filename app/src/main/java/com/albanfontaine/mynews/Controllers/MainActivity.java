package com.albanfontaine.mynews.Controllers;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Tabs as constants
    private final int TAB_TOP_STORIES = 0;
    private final int TAB_MOST_POPULAR = 1;
    private final int TAB_ARTS = 2;
    private final int TAB_BUSINESS = 3;
    private final int TAB_POLITICS = 4;
    private final int TAB_TRAVEL = 5;

    // Key for sharedPreferences
    private static final String LAST_TAB = "lastTab";

    // Channel ID for notifications
    public final static String CHANNEL_ID = "News notifications";

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.activity_main_drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.activity_main_nav_view) NavigationView mNavigationView;
    @BindView(R.id.activity_main_viewpager) ViewPager mPager;
    @BindView(R.id.activity_main_tabs) TabLayout mTabs;

    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Configuration of the different views
        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureViewPager();
        this.createNotificationChannel();

        mSharedPreferences = getPreferences(MODE_PRIVATE);
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

    ////////////////////
    // CONFIGURATIONS //
    ////////////////////

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
                this.mPager.setCurrentItem(TAB_TOP_STORIES);
                break;
            case R.id.activity_main_drawer_most_popular:
                this.mPager.setCurrentItem(TAB_MOST_POPULAR);
                break;
            case R.id.activity_main_drawer_arts:
                this.mPager.setCurrentItem(TAB_ARTS);
                break;
            case R.id.activity_main_drawer_business:
                this.mPager.setCurrentItem(TAB_BUSINESS);
                break;
            case R.id.activity_main_drawer_politics:
                this.mPager.setCurrentItem(TAB_POLITICS);
                break;
            case R.id.activity_main_drawer_travel:
                this.mPager.setCurrentItem(TAB_TRAVEL);
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
        // Click on a Toolbar icon
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
                Intent helpIntent = new Intent(MainActivity.this, HelpActivity.class);
                this.startActivity(helpIntent);
                return true;
            case R.id.toolbar_about:
                this.showAboutDialog();
                return true;
            default:
                return true;
        }
    }

    private void createNotificationChannel() {
        // Create the Notification Channel for API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "News notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription("Sends notifications once a day if new articles are posted on a specific subject");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }
    }

    private void showAboutDialog(){
        // Builds an AlertDialog then shows it
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("About");
        alert.setMessage(getResources().getString(R.string.about));
        alert.setPositiveButton("OK", null);
        alert.show();
    }

    @Override
    protected void onPause() {
        // Remember the last tab viewed
        int lastTab = mPager.getCurrentItem();
        mSharedPreferences.edit().putInt(LAST_TAB, lastTab).apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // Display the last tab viewed
        int lastTab = mSharedPreferences.getInt(LAST_TAB, 0);
        this.mPager.setCurrentItem(lastTab);
        super.onResume();
    }
}
