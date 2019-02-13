package com.example.jojo.obsido.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.jojo.obsido.AppDatabase;
import com.example.jojo.obsido.Partner;
import com.example.jojo.obsido.PartnerAdapter;
import com.example.jojo.obsido.PartnerViewModel;
import com.example.jojo.obsido.R;
import com.example.jojo.obsido.SettingsActivity;
import com.example.jojo.obsido.utils.SharedPreferenceUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PartnersListActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private PartnerViewModel partnerViewModel;
    private static final String LOG_TAG = "PartnersListActivity".getClass().getSimpleName();

    // UI XML View
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;


    // Shared Preferences Theme color values
    private int mColorPrimary;
    private int mColorPrimaryAccent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUpSharedPreference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners_list);

        initFab();
        initToolbar();
        initDrawer();

        final PartnerAdapter partnerAdapter = new PartnerAdapter();

        RecyclerView partnerRecyclerView = findViewById(R.id.partners_list);
        partnerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        partnerRecyclerView.setHasFixedSize(true);
        partnerRecyclerView.setAdapter(partnerAdapter);

        partnerViewModel = ViewModelProviders.of(this).get(PartnerViewModel.class);
        partnerViewModel.getAllPartners().observe(this, new Observer<List<Partner>>() {
            @Override
            public void onChanged(@Nullable List<Partner> partners) {
                partnerAdapter.setPartners(partners);
            }
        });
        //View emptyView = findViewById(R.id.empty_view);
        //partnerRecyclerView.setEmptyView(emptyView);
        /*partnerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: what should happen when a partner from the list is clicked
            }
        })*/

    }

    private void setUpSharedPreference() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setThemeFromSharedPreferences(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void setThemeFromSharedPreferences(SharedPreferences sharedPreferences) {
        String sharedPreferenceTheme = sharedPreferences.getString(getString(R.string.pref_theme_key),
                getString(R.string.pref_show_red_theme_label));

        Log.v(LOG_TAG, "loadThemeFromPreferences: called.");

        if(sharedPreferenceTheme != null) {
            try {
                if (sharedPreferenceTheme.equals(getString(R.string.pref_show_red_theme_key))) {
                    setTheme(R.style.AppThemeRed);
                } else if (sharedPreferenceTheme.equals(getString(R.string.pref_show_blue_theme_key))) {
                    setTheme(R.style.AppThemeBlue);
                } else if (sharedPreferenceTheme.equals(getString(R.string.pref_show_green_theme_key))) {
                    setTheme(R.style.AppThemeGreen);
                } else if (sharedPreferenceTheme.equals(getString(R.string.pref_show_pink_theme_key))) {
                    setTheme(R.style.AppThemePink);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        // Gets the values from the activity theme
        mColorPrimary = SharedPreferenceUtils.getColorPrimary(getTheme());
        mColorPrimaryAccent = SharedPreferenceUtils.getColorAccent(getTheme());
    }

    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(LOG_TAG, "initFab: adding a partner flaoting action button");
                Intent addingPartnerIntent = new Intent(PartnersListActivity.this, EditProfileActivity.class);
                startActivity(addingPartnerIntent);
            }
        });

        fab.setBackgroundTintList(ColorStateList.valueOf(mColorPrimaryAccent));
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void initDrawer() {
        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        if (headerView != null) {
            headerView.setBackgroundColor(mColorPrimary);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_partners_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Log.v(LOG_TAG, "onOptionsItemSelected:: opened Settings activity");
                Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
                startActivity(startSettingsActivity);

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_theme_key))) {
            Intent mIntent = getIntent();
            finish();
            startActivity(mIntent);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch(id) {
            case R.id.nav_overview:
                Log.v(LOG_TAG, "onNavigationItemSelected: navigation overview item selected");
                Intent overviewIntent = new Intent(this, OverviewActivity.class);
                startActivity(overviewIntent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.nav_calendar:
                Log.v(LOG_TAG, "onNavigationItemSelected: navigation calendar item selected");
                Intent calendarIntent = new Intent(this, CalendarActivity.class);
                startActivity(calendarIntent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.nav_partners:
                Log.v(LOG_TAG, "onNavigationItemSelected: navigation partners item selected");
                break;

            default:
                Log.v(LOG_TAG, "onNavigationItemSelected: default item selected");

                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpSharedPreference();
    }

}
