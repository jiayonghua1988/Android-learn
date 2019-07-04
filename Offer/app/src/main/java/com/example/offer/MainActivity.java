package com.example.offer;

import androidx.annotation.MainThread;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.offer.adapter.MainAdapter;
import com.example.offer.bean.Question;
import com.example.offer.widget.LinearItemDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView questList;
    private Context context;
    private MainAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        questList = findViewById(R.id.questList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        questList.setLayoutManager(linearLayoutManager);
        LinearItemDecoration itemDecoration = new LinearItemDecoration(context);
        itemDecoration.height(2f).color(Color.parseColor("#F0F1F1"));
        questList.addItemDecoration(new LinearItemDecoration(context));


        adapter = new MainAdapter(R.layout.item_main,new ArrayList<Question>());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Question question = (Question)adapter.getItem(position);
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("question",question.getQuestion());
                intent.putExtra("answer",question.getAnswer());
                startActivity(intent);
            }
        });
        questList.setAdapter(adapter);
        adapter.setNewData(parseJson());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        context = null;
    }


    private String getData() {
        StringBuilder builder = new StringBuilder();
        try {
            AssetManager assetManager = getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("data.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private List<Question> parseJson() {
        List<Question> list =  new ArrayList<>();
        //将读出的字符串转换成JSONobject
        JSONObject json = null;

        try {
            json = new JSONObject(getData());
            JSONArray jsonArray = json.getJSONArray("list");


            Type listType = new TypeToken<List<Question>>() {
            }.getType();
            //这里的json是字符串类型 = jsonArray.toString();
            list = new Gson().fromJson(jsonArray.toString(), listType );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //获取JSONObject中的数组数据
        return list;
    }

}
