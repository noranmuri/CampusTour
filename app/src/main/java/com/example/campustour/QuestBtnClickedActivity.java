package com.example.campustour;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

class QuestBtnClickedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        ListView listView = findViewById(R.id.listView);
        SingerAdapter adapter = new SingerAdapter();
        adapter.addItem(new SingerItem("IT4호관","2021-01-01",R.drawable.paw));
        adapter.addItem(new SingerItem("IT융복합관","2021-02-02",R.drawable.paw));

        listView.setAdapter(adapter);
    }

    class SingerAdapter extends BaseAdapter {
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();

        @Override
        public int getCount(){
            return items.size();
        }
        public void addItem(SingerItem item){
            items.add(item);
        }

        @Override
        public Object getItem(int position){
            return items.get(position);
        }

        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            SingerItemView singerItemView = null;
            if(convertView==null){
                singerItemView = new SingerItemView(getApplicationContext());
            } else {
                singerItemView = (SingerItemView)convertView;
            }
            SingerItem item = items.get(position);
            singerItemView.setName(item.getName());
            singerItemView.setMobile(item.getMobile());
            singerItemView.setImage(item.getResld());
            return singerItemView;
        }
    }
}