package com.example.constraint_layout;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.constraint_layout.databinding.ConstraintLayoutBinding;

public class ConstraintActivity extends AppCompatActivity {
    private ConstraintLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ConstraintLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    boolean isSelected = false;

    public void toggleLogin(View view) {
        if (!isSelected) {
            binding.txtLoginSwitch.setText("ON");
            binding.txtLoginSwitch.setTextColor(Color.RED);
            isSelected = true;
        } else {
            binding.txtLoginSwitch.setText("OFF");
            binding.txtLoginSwitch.setTextColor(Color.GRAY);
            isSelected = false;
        }
    }

    public void togglePush(View view){
        if (!isSelected) {
            binding.btnSetPush.setBackgroundResource(R.drawable.btn_toggleon_nor);
            isSelected = true;
        } else {
            binding.btnSetPush.setBackgroundResource(R.drawable.btn_toggleoff_nor);
            isSelected = false;
        }
    }

    public void goLogin(View view) {
        String url = "https://nid.naver.com/nidlogin.login";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }

    public void goPlaystore(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=kr.co.elandmall.elandmall"));
        startActivity(intent);
    }

    public void goDial(View view) {

        Intent intent = new Intent(Intent.ACTION_CALL);
        int id = view.getId();
        String dialNum = "";

//        String viewId = view.getResources().getResourceName(view.getId()).split("/")[1];
//        dialNum = binding.viewId.getText().toString());
//        java는 동적으로 변수명 할당 불가..

        if (id == R.id.callcenter1Tel) {
            dialNum = binding.callcenter1Tel.getText().toString();
        } else if (id == R.id.callcenter2Tel) {
            dialNum = binding.callcenter2Tel.getText().toString();
        }



        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // 퍼미션 요청
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CALL_PHONE}, 100);
        } else {
            intent.setData(Uri.parse("tel:" + dialNum));
            startActivity(intent);
        }

    }

}
