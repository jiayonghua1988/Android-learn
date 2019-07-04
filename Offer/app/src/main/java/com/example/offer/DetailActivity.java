package com.example.offer;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 问题详情
 */
public class DetailActivity extends AppCompatActivity {
    private TextView question;
    private TextView answer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);

        question.setText(getIntent().getStringExtra("question"));
        answer.setText(getIntent().getStringExtra("answer"));
    }
}
