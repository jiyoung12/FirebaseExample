package com.example.jiyoung.firebaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = LoginActivity.class.getName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button btn_ok;
    private EditText et_email, et_pwd;
    private ToggleButton tb_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initLayout();
        initAuth();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
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
    }

    /**
     * 초기 레이아웃 설정
     */
    private void initLayout() {

        btn_ok = (Button) findViewById(R.id.btn_ok);

        et_email = (EditText) findViewById(R.id.et_email);
        et_pwd = (EditText) findViewById(R.id.et_pwd);

        tb_switch = (ToggleButton) findViewById(R.id.tb_switch);

        btn_ok.setOnClickListener(this);
        tb_switch.setOnCheckedChangeListener(this);

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
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Log.w(TAG, "signUp :::: Fail", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }

    /**
     * 사용자 로그인
     */
    private void userSignIn() {
        if (checkEmailAndPwd()) {
            mAuth.signInWithEmailAndPassword(et_email.getText().toString(), et_pwd.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signUp :::: Success");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Log.w(TAG, "signUp :::: Fail", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
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
    }

    /**
     * 이메일 패스워드 Null 체크
     *
     * @return 이메일 패스워드가 유효한 경우 true
     * @// TODO: 2017. 8. 25. 유효성 체크추가하기
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
            case R.id.btn_ok:
                if (checkEmailAndPwd()) {
                    if (tb_switch.isChecked()) {
                        Log.d(TAG, "signIn");
                        userSignIn();
                    } else {
                        Log.d(TAG, "signUp");
                        userSignUp();
                    }
                }
                break;

        }

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.tb_switch:
                if (b) {
                    Log.d(TAG, "true");
                } else {
                    Log.d(TAG, "false");
                }
                break;

        }
    }
}
