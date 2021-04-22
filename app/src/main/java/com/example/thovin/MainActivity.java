package com.example.thovin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.thovin.databinding.ActivityMainBinding;
import com.example.thovin.ui.account.AccountEditorFragment;
import com.example.thovin.ui.account.AccountFragment;
import com.example.thovin.ui.auth.client.AuthClientFragment;
import com.example.thovin.ui.auth.AuthDelivererFragment;
import com.example.thovin.ui.auth.AuthFragment;
import com.example.thovin.ui.auth.UserViewModel;
import com.example.thovin.ui.cart.CartFragment;
import com.example.thovin.ui.home.HomeFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    // --- Global variables
    public static final String BASE_URL = "http://192.168.1.13:29321/v1/";

    // --- Retrofit
    public static Retrofit retrofit;

    // --- Views
    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;
    private NavController navController;
    private NavigationView navigationView;
    private AppBarConfiguration appBarConfiguration;

    // --- User
    public static UserViewModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // --- Configuration
        retrofit = new HttpClient(BASE_URL, HttpClient.DEBUG_ON).getRetrofit();
        configureNavigationUi();
    }


    /**
     * Configure the Toolbar
     */
    private void configureToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    /**
     * Configure the Drawer
     */
    private void configureDrawer() {

        drawerLayout = binding.drawerLayout;
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setOpenableLayout(drawerLayout)
                .build();

//        appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home,
//                R.id.nav_auth,
//                R.id.nav_parameters)
//                .setOpenableLayout(drawerLayout)
//                .build();

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//        drawerLayout.setFocusableInTouchMode(false);
    }

    /**
     * Configure the navigation ui of the application (toolbar, drawer, navbar...)
     */
    private void configureNavigationUi() {
        // --- NavController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        navigationView = binding.navView;

        // --- Toolbar
        configureToolBar();

        // --- Drawer
        configureDrawer();

        // --- Navigation UI
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    /**
     * Handle navigation while a top bar item is selected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

    /**
     * Handle menu creation
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Handle navigation between fragment
     * @return
     */
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
}