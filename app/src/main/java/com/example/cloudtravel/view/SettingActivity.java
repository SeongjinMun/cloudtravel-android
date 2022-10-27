package com.example.cloudtravel.view;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bumptech.glide.Glide;
import com.example.cloudtravel.R;
import com.example.cloudtravel.databinding.ActivitySettingBinding;
import com.example.cloudtravel.model.S3Util;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {

    private final static String TAG = SettingActivity.class.getSimpleName();

    private ActivitySettingBinding binding;
    private String email;
    private String token;

    private static final int REQUEST_CODE = 0;

    private static final String BUCKET_NAME = "cloudtvl-content";
    private static final String ACCESS_KEY = "AKIAXR6CRQY4DH4SUJOJ";
    private static final String SECRET_KEY = "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";
    private AmazonS3 amazonS3;

    Uri uri;
    File file;


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

        binding.settingProfileSet.setClipToOutline(true);

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



        binding.settingProfileSet.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, REQUEST_CODE);
        });


//        setResult(Activity.RESULT_CANCELED);
    }



//    public String getPath(Uri url){
//        String[] mediaData = {MediaStore.Images.Media.DATA};
//        CursorLoader cursorLoader = new CursorLoader(this, uri, mediaData, null, null, null);
//
//        Cursor cursor = cursorLoader.loadInBackground();
//        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//
//        cursor.moveToFirst();
//
//        return cursor.getString(index);
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ImageView  imageView = findViewById(R.id.setting_profile_set);

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                try {
                    Uri temp = data.getData();
                    Glide.with(getApplicationContext()).load(temp).into(imageView);

                    Log.d("Uri", temp.toString());



                    Log.d("rewq", file.toString());
                    Log.d("rewq", file.getName());

                }catch (Exception e){

                }
            }else if(resultCode == RESULT_CANCELED){

            }
        }
    }






}