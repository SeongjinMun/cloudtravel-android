package com.example.cloudtravel.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignOutOptions;
import com.example.cloudtravel.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {

    private final static String TAG = SettingActivity.class.getSimpleName();

    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.logoutButton.setOnClickListener(v->{
            AWSMobileClient.getInstance().signOut();
            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
            intent.putExtra("moveFragment", "profile");
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}