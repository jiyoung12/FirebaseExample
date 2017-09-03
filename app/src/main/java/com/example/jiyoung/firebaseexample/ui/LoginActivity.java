package com.example.jiyoung.firebaseexample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.jiyoung.firebaseexample.BaseActivity;
import com.example.jiyoung.firebaseexample.R;
import com.example.jiyoung.firebaseexample.util.Log;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = LoginActivity.class.getName();

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;

    private Button btn_ok;
    private SignInButton btn_google_sign_in;
    private EditText et_email, et_pwd;
    private ToggleButton tb_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initLayout();
        initGoogleLogin();

    }

    @Override
    protected void onStart() {
        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                //google 로그인 성공
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseWithGoogleLogin(account);
            } else {
                //google 로그인 실패
            }
        }
    }

    /**
     * 구글 로그인 한 결과로 FireBase에 저장해 준다
     * @param acc 구글 로그인 결과
     */
    private void firebaseWithGoogleLogin(GoogleSignInAccount acc) {
        Log.d( "ID : "+ acc.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acc.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        hideLoadingBar();

                        if (task.isSuccessful()){
                            // 구글 로그인 성공 메인 이동
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            // 구글 로그인 실패
                            Log.e( "signInWithCredential:failure"+ task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private void initGoogleLogin() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void signInWithGoogle() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    /**
     * 초기 레이아웃 설정
     */
    private void initLayout() {

        btn_ok = findViewById(R.id.btn_ok);
        btn_google_sign_in = findViewById(R.id.btn_google_sign_in);

        et_email = findViewById(R.id.et_email);
        et_pwd = findViewById(R.id.et_pwd);

        tb_switch = findViewById(R.id.tb_switch);

        btn_ok.setOnClickListener(this);
        btn_google_sign_in.setOnClickListener(this);
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

                            hideLoadingBar();

                            if (task.isSuccessful()) {
                                Log.d(TAG, "signUp :::: Success");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Log.e( "signUp :::: Fail"+ task.getException());
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

                            hideLoadingBar();

                            if (task.isSuccessful()) {
                                Log.d("signUp :::: Success");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Log.d("signUp :::: Fail" + task.getException().toString());
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
                    showLoadingBar();
                    if (tb_switch.isChecked()) {
                        Log.d( "signIn");
                        userSignIn();
                    } else {
                        Log.d( "signUp");
                        userSignUp();
                    }
                }
                break;

            case R.id.btn_google_sign_in:
                showLoadingBar();
                signInWithGoogle();
                break;

        }

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.tb_switch:
                if (b) {
                    Log.d( "true");
                } else {
                    Log.d( "false");
                }
                break;

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d( "connectionFailed : " + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_LONG).show();
    }
}
