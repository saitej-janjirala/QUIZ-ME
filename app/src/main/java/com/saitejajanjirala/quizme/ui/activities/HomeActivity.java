package com.saitejajanjirala.quizme.ui.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.saitejajanjirala.quizme.R;
import com.saitejajanjirala.quizme.ui.fragments.HomeFragment;
import com.saitejajanjirala.quizme.ui.fragments.PastTestsFragment;

public class HomeActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private Toolbar toolBar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        navigationView.setCheckedItem(R.id.nav_home);
        openHome();
    }

    private void openHome() {
        if(getSupportFragmentManager().findFragmentById(R.id.container) instanceof HomeFragment) return;
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, HomeFragment.class,null)
                .commit();
    }

    private void initViews() {
        navigationView = findViewById(R.id.navigation_view);
        toolBar = findViewById(R.id.tool_bar);
        drawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Flight-Bnb");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                HomeActivity.this,
                drawerLayout,
                toolBar,
                R.string.open_drawer,
                R.string.close_drawer
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        setUpNavView();
    }

    private void setUpNavView() {
        navigationView.setNavigationItemSelectedListener(item -> {
            handleNavViewBar(item);
            drawerLayout.closeDrawer(navigationView,true);
            return true;
        });
    }

    private void handleNavViewBar(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_logout:{
                logoutUser();
                break;
            }
            case R.id.nav_past_tests:{
                openPastTestResults();
                break;
            }
            case R.id.nav_home:{
                openHome();
                break;
            }
        }
    }

    private void logoutUser() {
        AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this)
                .setCancelable(false)
                .setTitle("Logout")
                .setMessage("Would you like to Logout?")
                .setNegativeButton("no", (dialogInterface, i) -> {

                })
                .setPositiveButton("yes", (dialogInterface, i) -> {

                })
                .show();
        Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(view -> {
            dialog.dismiss();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomeActivity.this,MainActivity.class));
            finish();
        });
    }

    private void openPastTestResults() {
        if(getSupportFragmentManager().findFragmentById(R.id.container) instanceof PastTestsFragment) return;
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, PastTestsFragment.class,null)
                .commit();
    }


}