package com.example.cococ.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.cococ.R;
import com.example.cococ.utils.SharedPrefs;

public class DetailActivity extends AppCompatActivity {

    private WebView webView;
    private String url;
    private boolean isDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initData();

        initView();
    }

    private void initData() {
        Bundle bundle = getIntent().getBundleExtra("data");
        if(bundle != null){
            if(bundle.containsKey("url")){
                url = bundle.getString("url");
            }
        }

        isDarkMode = SharedPrefs.getInstance().get("IS_DARK_MODE", Boolean.class);
    }

    private void initView(){
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        Log.d("DevDebug", "url "+url);
        webView.loadUrl(url);

        if(WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)){
            if(isDarkMode){
                WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
            }else {
                WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_OFF);
            }
        }
    }

    private class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }
}