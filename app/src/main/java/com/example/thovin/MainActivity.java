package com.example.thovin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
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
    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_AUTHENTICATION = 1;
    public static final int FRAGMENT_AUTHENTICATION_CLIENT = 2;
    public static final int FRAGMENT_AUTHENTICATION_DELIVERER = 3;
    public static final int FRAGMENT_ACCOUNT = 5;
    public static final int FRAGMENT_ACCOUNT_EDITOR = 6;

    // --- Retrofit
    public static Retrofit retrofit;

    // --- Views
    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;
    private NavigationView navigationView;
    private AppBarConfiguration appBarConfiguration;

    // --- Fragment
    private Fragment fragmentHome;
    private Fragment fragmentAccount;
    private Fragment fragmentAccountEditor;
    private Fragment fragmentCart;

    // --- User
    public static UserViewModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // --- Configuration
        retrofit = new HttpClient(BASE_URL, HttpClient.DEBUG_ON).getRetrofit();
        configureToolBar();
        configureDrawerLayout();
        configureNavigationView();

        // --- Start
        // showFirstFragment();
    }


    // --- Toolbar methods

    /**
     * Configure the Toolbar
     */
    private void configureToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_cart_icon) {
            showCartFragment();
        }
        return true;
    }



    // --- DrawerLayout methods

    /**
     * Configure the DrawerLayout
     */
    private void configureDrawerLayout() {
        drawerLayout = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        drawerLayout.setFocusableInTouchMode(false);
    }


    // --- NavigationView methods

    /**
     * Configure the NavigationView
     */
    private void configureNavigationView() {
        navigationView = binding.navView;
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_auth,
                R.id.nav_parameters)
                .setOpenableLayout(drawerLayout)
                .build();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
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


    // --- Fragments methods

    /**
     * Show a specific fragment by is id
     *
     * @param fragmentId  The fragment Id
     * @param jsonObjects Optional jsonObject for passing data
     */
    public void showFragment(int fragmentId, JSONObject... jsonObjects) {
        switch (fragmentId) {
            case FRAGMENT_HOME:
                showHomeFragment();
                break;
            case FRAGMENT_AUTHENTICATION:
                showAuthenticationFragment();
                break;
            case FRAGMENT_AUTHENTICATION_CLIENT:
                showAuthenticationClientFragment();
                break;
            case FRAGMENT_AUTHENTICATION_DELIVERER:
                showAuthenticationDelivererFragment();
                break;
            case FRAGMENT_ACCOUNT:
                showAccountFragment();
                break;
            case FRAGMENT_ACCOUNT_EDITOR:
                if (jsonObjects[0] != null) showAccountEditorFragment(jsonObjects[0]);
                break;
            default:
                break;
        }
    }

    /**
     * Show the first fragment we want to display
     */
    private void showFirstFragment() {
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (visibleFragment == null) {
            showFragment(FRAGMENT_HOME);
            navigationView.getMenu().getItem(FRAGMENT_HOME).setChecked(true);
        }
    }

    /**
     * Show the Home fragment
     */
    private void showHomeFragment() {
        if (fragmentHome == null) fragmentHome = HomeFragment.newInstance();
        getSupportActionBar().setTitle(R.string.home);
        startTransactionFragment(fragmentHome);
    }

    /**
     * Show the Authentication Fragment
     */
    private void showAuthenticationFragment() {
        Fragment fragmentAuthentication = AuthFragment.newInstance();
        getSupportActionBar().setTitle(R.string.connection);
        startTransactionFragment(fragmentAuthentication);
    }

    /**
     * Show the Authentication Client Fragment
     */
    private void showAuthenticationClientFragment() {
        Fragment fragmentAuthenticationClient = AuthClientFragment.newInstance();
        startTransactionFragment(fragmentAuthenticationClient);
    }

    /**
     * Show the Authentication Deliverer Fragment
     */
    private void showAuthenticationDelivererFragment() {
        Fragment fragmentAuthenticationDeliverer = AuthDelivererFragment.newInstance();
        startTransactionFragment(fragmentAuthenticationDeliverer);
    }

    /**
     * Show the Account Fragment
     */
    private void showAccountFragment() {
        if (fragmentAccount == null) fragmentAccount = AccountFragment.newInstance();
        startTransactionFragment(fragmentAccount);
    }

    /**
     * Show the Account Editor Fragment
     */
    public void showAccountEditorFragment(JSONObject jsonUserData) {
        if (fragmentAccountEditor == null)
            fragmentAccountEditor = AccountEditorFragment.newInstance(jsonUserData);
        startTransactionFragment(fragmentAccountEditor);
    }

    /**
     * Show the Cart fragment
     */
    private void showCartFragment() {
        if (fragmentCart == null) fragmentCart = CartFragment.newInstance();
        getSupportActionBar().setTitle(R.string.cart);
        startTransactionFragment(fragmentCart);
    }

    /**
     * Start transaction, replace the actual fragment by the one give and commit
     *
     * @param fragment The fragment
     */
    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
        }
    }
}