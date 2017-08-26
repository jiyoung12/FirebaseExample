package com.example.jiyoung.firebaseexample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();
    private FirebaseAuth mAuth;
    private Button btn_sign_up, btn_sign_in, btn_sign_out;
    private EditText et_email, et_pwd;
    private ConstraintLayout cl_user_info;
    private TextView tv_user_email, tv_user_name;


    //// TODO: 2017. 8. 25. signListener 추가하기 **

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    /**
     * 초기 레이아웃 설정
     */
    private void initLayout() {

        btn_sign_in = (Button) findViewById(R.id.btn_sign_in);
        btn_sign_up = (Button) findViewById(R.id.btn_sign_up);
        btn_sign_out = (Button) findViewById(R.id.btn_sign_out);

        et_email = (EditText) findViewById(R.id.et_email);
        et_pwd = (EditText) findViewById(R.id.et_pwd);

        tv_user_email = (TextView) findViewById(R.id.tv_user_email);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);

        cl_user_info = (ConstraintLayout) findViewById(R.id.cl_user_info);

        btn_sign_in.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);
        btn_sign_out.setOnClickListener(this);

    }

    /**
     * 사용자 정보를 레이아웃에 업데이트 해준다
     *
     * @param user 유저정보
     */
    private void updateUI(FirebaseUser user) {
        if (user != null) {

            cl_user_info.setVisibility(View.VISIBLE);

            tv_user_name.setText(user.getDisplayName());
            tv_user_email.setText(user.getEmail());

            Log.d(TAG, user.getEmail());
            Log.d(TAG, user.getUid());

        }else{
            cl_user_info.setVisibility(View.GONE);
        }
    }

    /**
     * 사용자 회원가입
     * 현재는 아이디 패스워드 UID 만 넘겨주는듯 함 확인 필요
     */
    private void userSignUp() {
        if (checkEmailAndPwd()) {
            mAuth.createUserWithEmailAndPassword(et_email.getText().toString(), et_pwd.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signUp :::: Success");
                                updateUI(mAuth.getCurrentUser());

                            } else {
                                Log.w(TAG, "signUp :::: Fail", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }

    /**
     * 사용자 로그인
     */
    private void userSignIn(){
        if (checkEmailAndPwd()){
            mAuth.signInWithEmailAndPassword(et_email.getText().toString(), et_pwd.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signUp :::: Success");
                                updateUI(mAuth.getCurrentUser());

                            } else {
                                Log.w(TAG, "signUp :::: Fail", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    /**
     * 로그아웃
     * UI를 업데이트 해준다
     */
    private void userSignOut() {
        mAuth.signOut();
        updateUI(null);
    }

    /**
     * 이메일 패스워드 Null 체크
     * @// TODO: 2017. 8. 25. 유효성 체크추가하기
     * @return 이메일 패스워드가 유효한 경우 true
     */
    private boolean checkEmailAndPwd() {
        if (!TextUtils.isEmpty(et_email.getText().toString()) && !TextUtils.isEmpty(et_email.getText().toString())) {
            return true;
        } else {
            Toast.makeText(this, "아이디 또는 패스워드가 비어있습니다.", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                userSignIn();
                break;
            case R.id.btn_sign_up:
                userSignUp();
                break;
            case R.id.btn_sign_out:
                userSignOut();
                break;

        }

    }


}
