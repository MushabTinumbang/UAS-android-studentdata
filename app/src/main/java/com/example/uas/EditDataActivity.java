package com.example.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class EditDataActivity extends AppCompatActivity {

    private EditText editname, editnpm, editjurusan;
    private Button buttonsave;
    private DataHelper db = new DataHelper(this);
    private String id, name, npm, jurusan;
    private TextView toptext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);


        editname = findViewById(R.id.editname);
        editnpm = findViewById(R.id.editnpm);
        editjurusan = findViewById(R.id.editjurusan);
        toptext = findViewById(R.id.ed_txt_1);
        buttonsave = findViewById(R.id.edit_save);

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        npm = getIntent().getStringExtra("npm");
        jurusan = getIntent().getStringExtra("jurusan");

        if (id == null || id.equals("")){
            toptext.setText("Add Mahasiswa");
            buttonsave.setText("Add");

        }else{
            toptext.setText("Edit Mahasiswa");
            buttonsave.setText("Edit");
            editname.setText(name);
            editnpm.setText(npm);
            editjurusan.setText(jurusan);

        }
        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(id == null || id.equals("")){
                        save();
                    }else{
                        edit();
                    }
                } catch(Exception e){
                    Log.e("Saving", e.getMessage());
                }
            }
        });
    }

    private void save(){
        if(String.valueOf(editname.getText()).equals("")|| String.valueOf(editnpm.getText()).equals("")|| String.valueOf(editjurusan.getText()).equals("") ){
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
        }else{
            db.insert(editname.getText().toString(), editnpm.getText().toString(), editjurusan.getText().toString());
            finish();
        }
    }

    private void edit(){
        if(String.valueOf(editname.getText()).equals("")|| String.valueOf(editnpm.getText()).equals("")|| String.valueOf(editjurusan.getText()).equals("")){
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
        }else{
            db.update(Integer.parseInt(id), editname.getText().toString(), editnpm.getText().toString(), editjurusan.getText().toString());
            finish();
        }
    }
}