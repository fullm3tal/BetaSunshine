package com.example.android.betasunshine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent receivedDataIntent=getIntent();
        if(receivedDataIntent!=null) {
            if (receivedDataIntent.hasExtra(Intent.EXTRA_TEXT)) {
                String data = receivedDataIntent.getStringExtra(Intent.EXTRA_TEXT);
                textView = (TextView) findViewById(R.id.textview_detail);
                textView.setText(data);
            }
        }
    }
}
