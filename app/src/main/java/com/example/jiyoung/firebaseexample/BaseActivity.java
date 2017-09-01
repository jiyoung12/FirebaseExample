package com.example.jiyoung.firebaseexample;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by jiyoung on 2017. 9. 1..
 */

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getName();
    private  Dialog dialog;

    public FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAuth();
    }

    private void initAuth() {
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {

                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }

        };

        mAuth.addAuthStateListener(mAuthListener);

    }

    public void showLoadingBar(){
        if (dialog == null){
            dialog = new Dialog(this, android.R.style.Theme_Light_NoTitleBar);

            ProgressBar progress = new ProgressBar(this);
            LinearLayout layout = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(params);
            layout.setGravity(Gravity.CENTER);
            layout.setBackgroundColor(Color.parseColor("#00000000"));
            layout.addView(progress);

            dialog.setContentView(layout);
            dialog.setCancelable(false);

        }

        dialog.show();
    }

    public void hideLoadingBar(){
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    public void signOut(){
        mAuth.signOut();
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideLoadingBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }
}
