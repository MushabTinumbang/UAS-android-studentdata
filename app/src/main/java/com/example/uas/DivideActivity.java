package com.example.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.uas.model.login.LoginData;

public class DivideActivity extends AppCompatActivity{
    String username, name, welcome, nama;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divide);

        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        nama = sessionManager.getUserDetail().get(SessionManager.NAME);

        Log.i(username, username);
        switch(sessionManager.getUserDetail().get(SessionManager.USERNAME)){
            case "admin":
                Intent intent = new Intent(DivideActivity.this, DashAdminActivity.class);
                startActivity(intent);
                break;
            default:
                Intent intent2 = new Intent(DivideActivity.this, DashActivity.class);
                startActivity(intent2);
                break;
        }
    }
}