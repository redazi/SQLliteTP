package com.zidahi.example.sqlitetp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.zidahi.example.sqlitetp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            FragmentMachine machineFragment = new FragmentMachine();
            FragmentManager fm1 = getFragmentManager();
            FragmentTransaction ft1 = fm1.beginTransaction();
            ft1.replace(R.id.fragment_container, machineFragment);
            ft1.commit();
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_machine:
                        FragmentMachine machineFragment = new FragmentMachine();
                        FragmentManager fm1 = getFragmentManager();
                        FragmentTransaction ft1 = fm1.beginTransaction();
                        ft1.replace(R.id.fragment_container, machineFragment);
                        ft1.commit();
                        break;
                    case R.id.nav_salles:
                        FragmentSalle fragmentSalle = new FragmentSalle();
                        FragmentManager fm2 = getFragmentManager();
                        FragmentTransaction ft2 = fm2.beginTransaction();
                        ft2.replace(R.id.fragment_container, fragmentSalle);
                        ft2.commit();
                        break;
                    case R.id.nav_machine_salle:
                        FragmentListMS fragmentSallem = new FragmentListMS();
                        FragmentManager fm3 = getFragmentManager();
                        FragmentTransaction ft3 = fm3.beginTransaction();
                       ft3.replace(R.id.fragment_container, fragmentSallem);
                        ft3.commit();
                        break;
                    case R.id.nav_graph:
                        GraphMachineBySalle graphMachineBySalle = new GraphMachineBySalle();
                        FragmentManager fm4 = getFragmentManager();
                        FragmentTransaction ft4 = fm4.beginTransaction();
                        ft4.replace(R.id.fragment_container, graphMachineBySalle);
                        ft4.commit();
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}