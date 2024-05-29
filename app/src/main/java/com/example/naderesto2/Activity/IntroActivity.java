package com.example.naderesto2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.naderesto2.R;

public class IntroActivity extends BaseActivity {
    //    ActivityIntroBinding binding;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_intro);
        initDisplay();
//        getWindow().setStatusBarColor(Color.parseColor("#FFE4B5"));
    }

    public void initDisplay() {
        login = findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
            }
        });

    }

//    private void setVariable() {
//
//        binding.loginBtn.setOnClickListener(v -> {
//            if (mAuth.getCurrentUser() != null) {
//                startActivity(new Intent(IntroActivity.this, MainActivity.class));
//            } else {
//                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
//
//            }
//        });
//        binding.signupBtn.setOnClickListener(v -> startActivity(new Intent(IntroActivity.this, SignupActivity.class)));
//    }
}