package com.example.jiyoung.firebaseexample;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.jiyoung.firebaseexample.util.Log;
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

                    Log.d( "onAuthStateChanged:signed_in:" + user.getUid());
                } else {

                    Log.d( "onAuthStateChanged:signed_out");
                }
            }

        };

        mAuth.addAuthStateListener(mAuthListener);

    }

    /**
     * 로딩바 보여주기
     * @TODO dim 처리 추가하기 알럿창과 공용으로 사용할 수 있도록 따로 빼놓기
     */
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

    /**
     * 로딩바 숨기기
     */
    public void hideLoadingBar(){
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    public void signOut(){

        Log.d(this.getClass().getName());
        mAuth.signOut();
    }


    @Override
    protected void onStop() {
        super.onStop();
        hideLoadingBar(); // onStop 인경우 로딩바를 숨겨준다
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }
}
