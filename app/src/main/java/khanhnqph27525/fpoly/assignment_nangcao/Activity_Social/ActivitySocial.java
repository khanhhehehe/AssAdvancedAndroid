package khanhnqph27525.fpoly.assignment_nangcao.Activity_Social;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.List;

import khanhnqph27525.fpoly.assignment_nangcao.MainActivity;
import khanhnqph27525.fpoly.assignment_nangcao.R;

public class ActivitySocial extends AppCompatActivity {
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private Button btn_share;
    private ImageView img_share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.setClientToken("1548871032015163");
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_social);
        loginButton = findViewById(R.id.id_btnlogin);
        btn_share = findViewById(R.id.btn_share);
        img_share = findViewById(R.id.img_share);
        callbackManager = CallbackManager.Factory.create();

//        btn_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requestPermission();
//            }
//        });


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getBaseContext(), "login thanh cong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(@NonNull FacebookException e) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(resultCode,requestCode,data);
    }
}