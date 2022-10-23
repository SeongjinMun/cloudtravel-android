package com.example.cloudtravel.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cloudtravel.R;

import com.example.cloudtravel.databinding.ActivityMainBinding;
import com.example.cloudtravel.view.fragment.HomeFragment;
import com.example.cloudtravel.view.fragment.ProfileFragment;
import com.example.cloudtravel.view.fragment.SearchFragment;
import com.example.cloudtravel.viewModel.MainViewModel;
import com.example.cloudtravel.viewModel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {

    private HomeFragment fragmentHome;
    private ActivityMainBinding binding;
    private String fragmentName;
    private MenuItem prevBottomNavigation;
    private String moveFragment;
    private MainViewModel viewModel;
    private UserViewModel userViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        viewModel.firstOpen.setValue(true);
        userViewModel.login.setValue(false);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_profile, R.id.navigation_search).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                navView.setSelectedItemId(R.id.navigation_home);
            }
        }


        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                switch (item.getItemId()) {
                    case R.id.navigation_home: {
                        if (fragmentManager.findFragmentByTag("home") != null){
                            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("home"))
                                    .commit();
                        }else {
                            fragmentManager.beginTransaction().add(R.id.nav_host_fragment_activity_main, new HomeFragment(), "home")
                                    .commit();
                        }
                        if (fragmentManager.findFragmentByTag("search") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("search"))
                                    .commit();
                        }
                        if (fragmentManager.findFragmentByTag("profile") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("profile"))
                                    .commit();
                        }
                        return true;
                    }
                    case R.id.navigation_search: {
                        if (fragmentManager.findFragmentByTag("search") != null){
                            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("search"))
                                    .commit();
                        }else {
                            fragmentManager.beginTransaction().add(R.id.nav_host_fragment_activity_main, new SearchFragment(), "search")
                                    .commit();
                        }
                        if (fragmentManager.findFragmentByTag("home") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("home"))
                                    .commit();
                        }
                        if (fragmentManager.findFragmentByTag("profile") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("profile"))
                                    .commit();
                        }
                        return true;
                    }
                    case R.id.navigation_profile: {

                        if (viewModel.firstOpen.getValue() && userViewModel.login.getValue()){
                            viewModel.firstOpen.setValue(false);
                            Intent intent = new Intent(MainActivity.this, AuthMainActivity.class);
                            startActivity(intent);
                        }

                        if (fragmentManager.findFragmentByTag("profile") != null){
                            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("profile"))
                                    .commit();
                        }else {
                            fragmentManager.beginTransaction().add(R.id.nav_host_fragment_activity_main, new ProfileFragment(), "profile")
                                    .commit();
                        }
                        if (fragmentManager.findFragmentByTag("home") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("home"))
                                    .commit();
                        }
                        if (fragmentManager.findFragmentByTag("search") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("search"))
                                    .commit();
                        }
                        return true;
                    }
                    default:return  false;
                }
            }
        });

    }

}