package com.example.uni_info;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class WebViewActivity extends AppCompatActivity {

    public static final String EXTRA_WEB_PAGES = "extra_web_pages";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_webview);

        WebView webView = findViewById(R.id.webview);

        // Retrieve the web page URLs passed from MainActivity
        List<String> webPages = getIntent().getStringArrayListExtra(EXTRA_WEB_PAGES);

        if (webPages != null && !webPages.isEmpty()) {
            // If there are multiple web pages, you can choose to open the first one
            String firstWebPageUrl = webPages.get(0);

            // Configure WebView settings
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            // Set a WebViewClient to open links in the same WebView, rather than in the default browser
            webView.setWebViewClient(new WebViewClient());

            // Load the first web page URL in the WebView
            webView.loadUrl(firstWebPageUrl);
        } else {
            // Handle the case where there are no web pages or the list is empty
            // You can show a message to the user or take any other appropriate action
        }
    }
}
