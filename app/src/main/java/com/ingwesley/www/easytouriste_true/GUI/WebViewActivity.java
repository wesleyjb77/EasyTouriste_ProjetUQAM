package com.ingwesley.www.easytouriste_true.GUI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.ingwesley.www.easytouriste_true.R;


public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        //Toolbar toolbar=findViewById(R.id.toolbarWeb);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        WebView helpview=findViewById(R.id.helpview);
        helpview.loadUrl("file:///android_asset/About.html");

    }

}
