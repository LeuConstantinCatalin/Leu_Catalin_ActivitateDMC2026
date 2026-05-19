package leu.laboratoare.lab09;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    private WebView webViewDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webViewDetails = findViewById(R.id.webViewDetails);

        String webUrl = getIntent().getStringExtra("webUrl");

        WebSettings webSettings = webViewDetails.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webViewDetails.setWebViewClient(new WebViewClient());
        webViewDetails.loadUrl(webUrl);
    }
}