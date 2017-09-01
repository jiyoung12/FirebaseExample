package com.example.jiyoung.firebaseexample.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jiyoung.firebaseexample.BaseActivity;
import com.example.jiyoung.firebaseexample.R;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, mAuth.getCurrentUser().getEmail());


        ((Button)findViewById(R.id.btn_sign_out)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

    }

}
