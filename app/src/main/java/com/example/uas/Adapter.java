package com.example.uas;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Data> lists;

    public Adapter(Activity activity, List<Data> lists){
        this.activity = activity;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if(inflater==null){
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null && inflater != null){
            convertView = inflater.inflate(R.layout.listusers, null);
        }
        if(convertView != null){
            TextView name = convertView.findViewById(R.id.textname);
            TextView npm = convertView.findViewById(R.id.textnpm);
            TextView jurusan = convertView.findViewById(R.id.textjurusan);
            Data data = lists.get(i);
            name.setText(data.getName());
            npm.setText(data.getNpm());
            jurusan.setText(data.getJurusan());
        }
        return convertView;
    }
}
