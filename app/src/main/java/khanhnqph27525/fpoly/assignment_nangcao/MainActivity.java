package khanhnqph27525.fpoly.assignment_nangcao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import khanhnqph27525.fpoly.assignment_nangcao.Activity_Course.QuanLySinhVien;
import khanhnqph27525.fpoly.assignment_nangcao.Activity_Map.ActivityMap;
import khanhnqph27525.fpoly.assignment_nangcao.Activity_News.ActivityNews;
import khanhnqph27525.fpoly.assignment_nangcao.Activity_Social.ActivitySocial;

public class MainActivity extends AppCompatActivity {
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    LinearLayout id_course,id_map,id_news,id_social;
    Button btn_test;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.setClientToken("1548871032015163");
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        id_course = findViewById(R.id.id_course);
        id_map = findViewById(R.id.id_map);
        id_news = findViewById(R.id.id_news);
        id_social = findViewById(R.id.id_social);
        id_course.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    id_course.setPadding(50,50,50,50);
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    id_course.setPadding(0,0,0,0);
                }
                return false;
            }
        });
        id_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), QuanLySinhVien.class));
            }
        });

        id_news.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    id_news.setPadding(50,50,50,50);
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    id_news.setPadding(0,0,0,0);
                }
                return false;
            }
        });
        id_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ActivityNews.class));
            }
        });

        id_map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    id_map.setPadding(50,50,50,50);
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    id_map.setPadding(0,0,0,0);
                }
                return false;
            }
        });
        id_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ActivityMap.class));
            }
        });

        id_social.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    id_social.setPadding(50,50,50,50);
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    id_social.setPadding(0,0,0,0);
                }
                return false;
            }
        });
        id_social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getBaseContext(), ActivitySocial.class));

                showDialogShare();
            }
        });
    }

    private void showDialogShare() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_choose_share);
        loginButton = dialog.findViewById(R.id.id_btnlogin);
        callbackManager = CallbackManager.Factory.create();
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
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(resultCode,requestCode,data);
    }
}