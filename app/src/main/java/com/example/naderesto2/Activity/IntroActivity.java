package com.example.naderesto2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.naderesto2.R;

public class IntroActivity extends BaseActivity {
    //    ActivityIntroBinding binding;
    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_intro);
        initDisplay();
//        getWindow().setStatusBarColor(Color.parseColor("#FFE4B5"));
    }

    public void initDisplay() {
        startBtn = findViewById(R.id.start_button);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
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