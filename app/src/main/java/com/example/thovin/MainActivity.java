package com.example.thovin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // --- Views
    private DrawerLayout drawerLayout;
    private AppBarConfiguration appBarConfiguration;

    private Boolean isFirstStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String state = intent.getStringExtra("STATE");

        if (state != null && isFirstStart) {
            Utility.getWarningSnackbar(getApplicationContext(), findViewById(android.R.id.content).getRootView(), state, Snackbar.LENGTH_SHORT).show();
            isFirstStart = false;
        }

        // --- Configure the fragment navigation
        configureNavigationUi();

        setProductCategories();
    }

    /**
     * Configure the navigation ui of the application (toolbar, drawer, navbar...)
     */
    public void configureNavigationUi() {
        // --- Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // --- NavController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationView navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);

        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setOpenableLayout(drawerLayout)
                .build();

        // --- Navigation UI
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals(getString(R.string.exit))) {
            this.finish();
            return true;
        }
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_app_top_bar, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    /**
     * Handle back button pressed when the drawer is open. On back pressed then close the menu (if it is open)
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    public void setProductCategories() {
        Utility.PRODUCT_TYPE = new ArrayList<>(Arrays.asList(
                "",
                "Meal",
                "Burger",
                "Sushi",
                "Salad",
                "Soup",
                "Noodles",
                "Accompaniment",
                "Drink",
                "Sauce"));

        Utility.PRODUCT_TYPE_TRANSLATE = new ArrayList<>(Arrays.asList(
                "",
                getString(R.string.meal),
                getString(R.string.burger),
                getString(R.string.sushi),
                getString(R.string.salad),
                getString(R.string.soup),
                getString(R.string.noodles),
                getString(R.string.accompaniment),
                getString(R.string.drink),
                getString(R.string.sauce)));
    }
}