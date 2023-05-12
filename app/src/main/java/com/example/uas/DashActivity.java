package com.example.uas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashActivity extends AppCompatActivity implements View.OnClickListener{

    TextView etUsername, logout;
    String username, nama;
    Adapter adapter;
    ListView listview;
    AlertDialog.Builder dialog;
    List<Data> lists= new ArrayList<>();
    SessionManager sessionManager;
    DataHelper db = new DataHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        EditText editText = findViewById(R.id.d_search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search(s.toString());
            }
        });



        sessionManager = new SessionManager((DashActivity.this));
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        etUsername = findViewById(R.id.d_txt_1);

        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        nama = sessionManager.getUserDetail().get(SessionManager.NAME);

        try {
            if(username.equals("admin")) {
                moveToAdmin();
            }
        }
        catch (Exception e){
            moveToLogin();
        }
        db = new DataHelper(getApplicationContext());
        listview = findViewById(R.id.list_item);
        adapter = new Adapter(DashActivity.this, lists);
        listview.setAdapter(adapter);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);

        etUsername.setText(username);
        etUsername.setOnClickListener(this);

        getData();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.logout:
                sessionManager.logoutSession();
                moveToLogin();
                break;
        }
    }

    public void search(String x){
        lists.clear();
        ArrayList<HashMap<String, String>> rows = db.getSearch(x);
        for(int i = 0; i < rows.size(); ++i){
            String id = rows.get(i).get("id");
            String name = rows.get(i).get("name");
            String npm = rows.get(i).get("npm");
            String jurusan = rows.get(i).get("jurusan");

            Data data = new Data();
            data.setId(id);
            data.setName(name);
            data.setNpm(npm);
            data.setJurusan(jurusan);

            lists.add(data);
        }
        adapter.notifyDataSetChanged();
        refresh(1000);
    }
    @Override
    protected void onResume(){
        super.onResume();
        lists.clear();
        getData();
    }

    private void refresh(int i) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getData();
            }
        };
    }

    private void getData(){
        ArrayList<HashMap<String, String>> rows = db.getAll();
        for(int i = 0; i < rows.size(); ++i){
            String id = rows.get(i).get("id");
            String name = rows.get(i).get("name");
            String npm = rows.get(i).get("npm");
            String jurusan = rows.get(i).get("jurusan");

            Data data = new Data();
            data.setId(id);
            data.setName(name);
            data.setNpm(npm);
            data.setJurusan(jurusan);

            lists.add(data);
        }
        adapter.notifyDataSetChanged();
        refresh(1000);
    }


    private void moveToLogin() {
        Intent intent = new Intent(DashActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    private void moveToAdmin(){
        Intent intent = new Intent(DashActivity.this, DashAdminActivity.class);
        startActivity(intent);
        finish();
    }
}