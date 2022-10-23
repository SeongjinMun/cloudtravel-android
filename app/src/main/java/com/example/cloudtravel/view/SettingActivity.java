package com.example.cloudtravel.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.example.cloudtravel.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {

    private final static String TAG = SettingActivity.class.getSimpleName();

    private ActivitySettingBinding binding;
    private String email;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email = extras.getString("eamil");
            token = extras.getString("token");
        }


        binding.logoutButton.setOnClickListener(v->{
            AWSMobileClient.getInstance().signOut();
            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
            intent.putExtra("moveFragment", "profile");
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });

        binding.cancelButton.setOnClickListener(v->{
            onBackPressed();
        });

        binding.settingUpdate.setOnClickListener(v->{
            Intent intent = new Intent();

            String userBirth        = binding.editTextTextUserBirth.getText().toString();
            String userNickname     = binding.editTextTextUserName.getText().toString();
            String userSex          = binding.userSexSpinner.getSelectedItem().toString();

            Log.d("settingUpdate", userBirth);
            Log.d("settingUpdate", userNickname);
            Log.d("settingUpdate", userSex);

//            intent.putExtra("nickName", userNickname);
//            intent.putExtra("birthdate", userBirth);
//            intent.putExtra("userSex", userSex);
//
//            setResult(Activity.RESULT_OK, intent);
//            finish();
        });



        setResult(Activity.RESULT_CANCELED);
    }

}