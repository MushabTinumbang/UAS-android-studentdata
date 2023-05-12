package com.example.uas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ThemedSpinnerAdapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashAdminActivity extends AppCompatActivity implements View.OnClickListener {
    Handler mHandler;
    ListView listview;
    AlertDialog.Builder dialog;
    List<Data> lists= new ArrayList<>();
    Adapter adapter;
    DataHelper db = new DataHelper(this);
    Button btnAdd;
    SessionManager sessionManager;
    String username;
    Button button1;
    TextView logout, etUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_admin2);


        EditText editText = findViewById(R.id.da_search);
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

        sessionManager = new SessionManager((DashAdminActivity.this));
        if(sessionManager.isLoggedIn() == false){
            Intent intent = new Intent(DashAdminActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();

            username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
            etUsername = findViewById(R.id.d_txt_1);
            etUsername.setText(username);

        }

        db = new DataHelper(getApplicationContext());
        listview = findViewById(R.id.list_item);
        adapter = new Adapter(DashAdminActivity.this, lists);
        listview.setAdapter(adapter);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);

        button1 = findViewById(R.id.da_btn1);
        button1.setOnClickListener(this);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String id = lists.get(i).getId();
                final String name = lists.get(i).getName();
                final String npm = lists.get(i).getNpm();
                final String jurusan = lists.get(i).getJurusan();

                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                dialog = new AlertDialog.Builder(DashAdminActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intent= new Intent(DashAdminActivity.this, EditDataActivity.class);
                                intent.putExtra("id", id);
                                intent.putExtra("name", name);
                                intent.putExtra("npm", npm);
                                intent.putExtra("jurusan", jurusan);

                                startActivity(intent);
                                break;
                            case 1:
                                AlertDialog.Builder altdial = new AlertDialog.Builder(DashAdminActivity.this);
                                altdial.setMessage("Are you sure you want to delete this?").setCancelable(false)
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        db.delete(Integer.parseInt(id));
                                                        lists.clear();
                                                        getData();
                                                    }
                                                })
                                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.cancel();
                                                                lists.clear();
                                                                getData();
                                                            }
                                                        });
                                AlertDialog alert = altdial.create();
                                alert.setTitle("Alert");
                                alert.show();
                                break;

                        }
                    }
                }).show();
                return false;
            }
        });
        getData();
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

    private void refresh(int x){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getData();
            }
        };
    }

    @Override
    protected void onResume(){
        super.onResume();
        lists.clear();
        getData();
    }


    public void onClick(View v) {
        switch(v.getId()){
            case R.id.logout:
                sessionManager.logoutSession();
                Intent intent = new Intent(DashAdminActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
                break;
            case R.id.da_btn1:
                Intent intent2 = new Intent(DashAdminActivity.this, EditDataActivity.class);
                startActivity(intent2);
        }
    }



}