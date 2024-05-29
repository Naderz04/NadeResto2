package com.example.naderesto2.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import com.example.naderesto2.R;

public class BaseActivity extends AppCompatActivity {

    public String TAG = "uilover";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
    }


}
