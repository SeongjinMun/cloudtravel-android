package com.example.cloudtravel.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudtravel.databinding.ActivityFilterBinding;
import com.example.cloudtravel.model.BannerModel;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

    final String TAG_NONE = "TAG_TEXT_NONE";
    ActivityFilterBinding binding;
    String search = "";
    boolean tagInput = false;
    List<String> tags = new ArrayList<String>();
    boolean tagCheck[] = { false, false, false, false };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        binding = ActivityFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        binding.scrollView.setHorizontalScrollBarEnabled(false);
        binding.cancle.setVisibility(View.GONE);
        binding.editText1.requestFocus();

        binding.editText1.setOnEditorActionListener((textView, action, keyEvent) -> {


            tagCheck();

            if (action == EditorInfo.IME_ACTION_DONE){

                if (tagInput){
                    String dumpTag = binding.editText1.getText().toString();
                    binding.editText1.setText("");

                    for (int i = 0; i < tagCheck.length; i++){
                        if (tagCheck[i] == false){
                            if (i == 0){
                                binding.tag1.setText(dumpTag);
                                binding.tag1.setVisibility(View.VISIBLE);
                                tags.add(dumpTag);
                                return true;
                            }
                            if (i == 1){
                                binding.tag2.setText(dumpTag);
                                binding.tag2.setVisibility(View.VISIBLE);
                                tags.add(dumpTag);
                                return true;
                            }
                            if (i == 2){
                                binding.tag3.setText(dumpTag);
                                binding.tag3.setVisibility(View.VISIBLE);
                                tags.add(dumpTag);
                                return true;
                            }
                            if (i == 3){
                                binding.tag4.setText(dumpTag);
                                binding.tag4.setVisibility(View.VISIBLE);
                                tags.add(dumpTag);
                                return true;
                            }
                        }
                    }
                    
                    if (tags.size() == 4) {
                        Toast.makeText(this, "태그의 최대 개수는 4개로 제한됩니다.", Toast.LENGTH_SHORT).show();
                    }
                    
                    return true;
                } else {
                    search = binding.editText1.getText().toString();
                }

                binding.back.callOnClick();
            }
            return true;
        });

        binding.tag1.setOnClickListener(view -> {
            binding.tag1.setText("");
            tags.remove(tags.size()-1);
            binding.tag1.setVisibility(View.GONE);
        });

        binding.tag2.setOnClickListener(view -> {
            binding.tag2.setText("");
            tags.remove(tags.size()-1);
            binding.tag2.setVisibility(View.GONE);
        });

        binding.tag3.setOnClickListener(view -> {
            binding.tag3.setText("");
            tags.remove(tags.size()-1);
            binding.tag3.setVisibility(View.GONE);
        });

        binding.tag4.setOnClickListener(view -> {
            binding.tag4.setText("");
            tags.remove(tags.size()-1);
            binding.tag4.setVisibility(View.GONE);
        });

        binding.textCancle.setOnClickListener(v->{
            //binding.editText1.clearFocus();
            binding.editText1.setText("");
        });

        binding.cancle.setOnClickListener(v->{
            tagInput=false;
            binding.editText1.setHint("검색어를 입력해주세요");
            binding.cancle.setVisibility(View.GONE);
        });

        binding.addTag.setOnClickListener(view -> {
            tagCheck();
            tagInput=true;
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            binding.editText1.setHint("#태그를 입력해주세요");
            binding.editText1.setText("");
            binding.cancle.setVisibility(View.VISIBLE);
        });

        binding.back.setOnClickListener(v->{

            String resultTags = "";

            imm.hideSoftInputFromWindow(binding.editText1.getWindowToken(), 0);

            Intent intent = new Intent();

            for (int i = 0; i < tags.size(); i++){
                if (tags.get(i) != ""){
                    resultTags += tags.get(i);
                }
                if (i < tags.size()-1) {
                    resultTags += ",";
                }
            }

            intent.putExtra("search", search);
            intent.putExtra("tags", resultTags);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });


        setResult(Activity.RESULT_CANCELED);

    }

    protected void tagCheck() {

        if (binding.tag1.getText().toString()==""){
            binding.tag1.setVisibility(View.GONE);
            tagCheck[0] = false;
        }else {
            tagCheck[0] = true;
        }
        if (binding.tag2.getText().toString()==""){
            binding.tag2.setVisibility(View.GONE);
            tagCheck[1] = false;
        }else {
            tagCheck[1] = true;
        }
        if (binding.tag3.getText().toString()==""){
            binding.tag3.setVisibility(View.GONE);
            tagCheck[2] = false;
        }else {
            tagCheck[2] = true;
        }
        if (binding.tag4.getText().toString()==""){
            binding.tag4.setVisibility(View.GONE);
            tagCheck[3] = false;
        }else {
            tagCheck[3] = true;
        }

        if (tagCheck[0]){
            binding.tag1.setVisibility(View.VISIBLE);
        }
        if (tagCheck[1]){
            binding.tag2.setVisibility(View.VISIBLE);
        }
        if (tagCheck[2]){
            binding.tag3.setVisibility(View.VISIBLE);
        }
        if (tagCheck[3]){
            binding.tag4.setVisibility(View.VISIBLE);
        }
    }


}