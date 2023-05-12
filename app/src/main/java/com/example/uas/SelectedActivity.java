package com.example.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class SelectedActivity extends AppCompatActivity {
    private TextView editname, editsubuh, editdzuhur, editashar, editmaghrib, editisya;
    private String id, name, subuh, dzuhur, ashar, maghrib, isya;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);

//        id = getIntent().getStringExtra("id");
//        name = getIntent().getStringExtra("name");
//        subuh = getIntent().getStringExtra("subuh");
//        dzuhur = getIntent().getStringExtra("dzuhur");
//        ashar = getIntent().getStringExtra("ashar");
//        maghrib = getIntent().getStringExtra("maghrib");
//        isya = getIntent().getStringExtra("isya");
//
//        editsubuh = findViewById(R.id.editSubuh);
//        editdzuhur = findViewById(R.id.editDzuhur);
//        editashar = findViewById(R.id.editAshar);
//        editmaghrib = findViewById(R.id.editMaghrib);
//        editisya = findViewById(R.id.editIsya);
//
//        editname.setText(name);
//        editsubuh.setText(subuh);
//        editdzuhur.setText(dzuhur);
//        editashar.setText(ashar);
//        editmaghrib.setText(maghrib);
//        editisya.setText(isya);
    }
}