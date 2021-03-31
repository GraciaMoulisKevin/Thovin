package com.example.thovin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;
    private NavigationView navigationView;


    // Fragment
    private Fragment fragmentHome;
    private static final int FRAGMENT_HOME = 0;
    private Fragment fragmentAuthentication;
    private static final int FRAGMENT_AUTHENTICATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        this.showFirstFragment();
    }

    // --- Toolbar methods
    private void configureToolBar(){
        this.toolbar = findViewById(R.id.activity_main_toolbar);
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
            // TODO: utiliser un fragment plutôt
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        }
        return true;
    }


    // --- DrawerLayout methods
    private void configureDrawerLayout() {
        this.drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        this.drawerLayout.setFocusableInTouchMode(false);
    }


    // --- NavigationView methods
    private void configureNavigationView(){
        this.navigationView = findViewById(R.id.activity_main_navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.activity_main_drawer_home :
                this.showFragment(FRAGMENT_HOME);
                break;
            case R.id.activity_main_drawer_user :
                this.showFragment(FRAGMENT_AUTHENTICATION);
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

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // --- Fragments methods
    private void showFragment(int fragmentId) {
        switch (fragmentId) {
            case FRAGMENT_HOME:
                this.showHomeFragment();
                getSupportActionBar().setTitle(R.string.home);
                break;
            case FRAGMENT_AUTHENTICATION:
                this.showAuthenticationFragment();
                getSupportActionBar().setTitle(R.string.connection);
                break;
            default:
                break;
        }
    }

    private void showFirstFragment() {
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null) {
            this.showFragment(FRAGMENT_HOME);
            this.navigationView.getMenu().getItem(FRAGMENT_HOME).setChecked(true);
        }
    }

    private void showAuthenticationFragment() {
        if (this.fragmentAuthentication == null) this.fragmentAuthentication = AuthenticationFragment.newInstance();
        this.startTransactionFragment(this.fragmentAuthentication);
    }

    private void showHomeFragment() {
        if (this.fragmentHome == null) this.fragmentHome = HomeFragment.newInstance();
        this.startTransactionFragment(this.fragmentHome);
    }

    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }
}



/* ARCHIVES
    private Button profilBtn;
    private TextInputEditText addressInput;
    private Button positionBtn;
    private Spinner categoriesSpinner;
    private NestedScrollView categoriesContainer;
    private Button connectionBtn;


        addressInput = findViewById(R.id.activity_main_address_input);
        positionBtn = findViewById(R.id.activity_main_position_btn);
        categoriesSpinner = findViewById(R.id.activity_main_categories_spinner);
        categoriesContainer = findViewById(R.id.activity_main_categories_container);

        // Modif
        positionBtn.setEnabled(false);

        // Spinner
        String[] categories = {"Catégorie", "Sushis", "Burger", "Saladerie", "Pizza"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        this.categoriesSpinner.setAdapter(adapter);

        // Listeners
        this.categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addressInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                positionBtn.setEnabled(s.toString().length() != 0);
            }
        });

        positionBtn.setOnClickListener(v -> {
            Snackbar.make(v, "Localisation en cours...", Snackbar.LENGTH_LONG).show();
        });


        profilBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfilActivity.class);
            startActivity(intent);
        });

        connectionBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AuthenticationActivity.class);
            startActivity(intent);
        });

    // --- Spinner Adapter methods
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            // On selecting a spinner item
            String item = parent.getItemAtPosition(position).toString();

            // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

 */