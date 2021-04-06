package com.example.thovin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.thovin.databinding.ActivityMainBinding;
import com.example.thovin.fragments.AccountEditorFragment;
import com.example.thovin.fragments.AccountFragment;
import com.example.thovin.fragments.AuthenticationClientFragment;
import com.example.thovin.fragments.AuthenticationDelivererFragment;
import com.example.thovin.fragments.AuthenticationFragment;
import com.example.thovin.fragments.CartFragment;
import com.example.thovin.fragments.HomeFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;
    private NavigationView navigationView;

    // Fragment
    private Fragment fragmentHome;
    private Fragment fragmentAuthentication;
    private Fragment fragmentAuthenticationClient;
    private Fragment fragmentAuthenticationDeliverer;
    private Fragment fragmentAccount;
    private Fragment fragmentAccountEditor;
    private Fragment fragmentCart;

    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_AUTHENTICATION = 1;
    public static final int FRAGMENT_AUTHENTICATION_CLIENT = 2;
    public static final int FRAGMENT_AUTHENTICATION_DELIVERER = 3;
    public static final int FRAGMENT_AUTHENTICATION_RESTAURANT = 4;
    public static final int FRAGMENT_ACCOUNT = 5;
    public static final int FRAGMENT_ACCOUNT_EDITOR = 6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.showFirstFragment();
    }

    // --- Toolbar methods

    /**
     * Configure the Toolbar
     */
    private void configureToolBar(){
        this.toolbar = binding.activityMainToolbar;
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_cart_icon) {
            this.showCartFragment();
        }
        return true;
    }


    // --- DrawerLayout methods

    /**
     * Configure the DrawerLayout
     */
    private void configureDrawerLayout() {
        this.drawerLayout = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        this.drawerLayout.setFocusableInTouchMode(false);
    }


    // --- NavigationView methods

    /**
     * Configure the NavigationView
     */
    private void configureNavigationView(){
        this.navigationView = binding.activityMainNavigationView;
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * TODO: Try to use ViewBinding instead of R.id
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.activity_main_drawer_home :
                this.showFragment(FRAGMENT_HOME);
                break;
            case R.id.activity_main_drawer_user :
                // TODO: if connected, open PROFIL
                this.showFragment(FRAGMENT_AUTHENTICATION);
                break;
            case R.id.activity_main_drawer_user_account:
                this.showFragment(FRAGMENT_ACCOUNT);
                break;
            case R.id.activity_main_drawer_parameters:
                // TODO
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    /**
     * Handle back button pressed when the drawer is open. On back pressed then close the menu (if it is open)
     */
    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    // --- Fragments methods

    /**
     * Show a specific fragment by is id
     * @param fragmentId
     * @param jsonObjects
     */
    public void showFragment(int fragmentId, JSONObject ...jsonObjects) {
        switch (fragmentId) {
            case FRAGMENT_HOME:
                this.showHomeFragment();
                break;
            case FRAGMENT_AUTHENTICATION:
                this.showAuthenticationFragment();
                break;
            case FRAGMENT_AUTHENTICATION_CLIENT:
                this.showAuthenticationClientFragment();
                break;
            case FRAGMENT_AUTHENTICATION_DELIVERER:
                this.showAuthenticationDelivererFragment();
                break;
//            case FRAGMENT_AUTHENTICATION_RESTAURANT:
//                this.showAuthenticationRestaurantFragment();
//                break;
            case FRAGMENT_ACCOUNT:
                this.showAccountFragment();
                break;
            case FRAGMENT_ACCOUNT_EDITOR:
                if (jsonObjects[0] != null) this.showAccountEditorFragment(jsonObjects[0]);
                break;
            default:
                break;
        }
    }

    /**
     * Show the first fragment we want to display
     */
    private void showFirstFragment() {
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null) {
            this.showFragment(FRAGMENT_HOME);
            this.navigationView.getMenu().getItem(FRAGMENT_HOME).setChecked(true);
        }
    }

    /**
     * Show the Home fragment
     */
    private void showHomeFragment() {
        if (this.fragmentHome == null) this.fragmentHome = HomeFragment.newInstance();
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.home);
        this.startTransactionFragment(this.fragmentHome);
    }

    /**
     * Show the Authentication Fragment
     */
    private void showAuthenticationFragment() {
        if (this.fragmentAuthentication == null) this.fragmentAuthentication = AuthenticationFragment.newInstance();
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.connection);
        this.startTransactionFragment(this.fragmentAuthentication);
    }

    /**
     * Show the Authentication Client Fragment
     */
    private void showAuthenticationClientFragment() {
        if (this.fragmentAuthenticationClient == null) this.fragmentAuthenticationClient = AuthenticationClientFragment.newInstance();
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.connection);
        this.startTransactionFragment(this.fragmentAuthenticationClient);
    }

    /**
     * Show the Authentication Deliverer Fragment
     */
    private void showAuthenticationDelivererFragment() {
        if (this.fragmentAuthenticationDeliverer == null) this.fragmentAuthenticationDeliverer = AuthenticationDelivererFragment.newInstance();
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.connection);
        this.startTransactionFragment(this.fragmentAuthenticationDeliverer);
    }

    /**
     * Show the Account Fragment
     */
    private void showAccountFragment() {
        if (this.fragmentAccount == null) this.fragmentAccount = AccountFragment.newInstance();
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.fragment_account_header);
        this.startTransactionFragment(this.fragmentAccount);
    }

    /**
     * Show the Account Editor Fragment
     */
    public void showAccountEditorFragment(JSONObject jsonUserData) {
        if (this.fragmentAccountEditor == null) this.fragmentAccountEditor = AccountEditorFragment.newInstance(jsonUserData);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.fragment_account_header);
        this.startTransactionFragment(this.fragmentAccountEditor);
    }

    /**
     * Show the Cart fragment
     */
    private void showCartFragment() {
        if (this.fragmentCart == null) this.fragmentCart = CartFragment.newInstance();
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.cart);
        this.startTransactionFragment(this.fragmentCart);
    }

    /**
     * Start transaction, replace the actual fragment by the one give and commit
     * @param fragment The fragment
     */
    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }
}