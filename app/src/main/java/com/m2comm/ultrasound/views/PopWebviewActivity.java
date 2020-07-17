package com.m2comm.ultrasound.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.m2comm.ultrasound.R;
import com.m2comm.ultrasound.module.ChromeclientPower;
import com.m2comm.ultrasound.module.Custom_SharedPreferences;
import com.m2comm.ultrasound.module.Download;
import com.m2comm.ultrasound.module.Download_PDFViewerActivity;
import com.m2comm.ultrasound.module.Global;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class PopWebviewActivity extends Activity implements View.OnClickListener {

    private WebView wv;
    private String paramUrl = "";
    private Global g;
    private boolean isPdf = false;
    private Custom_SharedPreferences csp;
    private Context longCLickContext = this;
    private ImageView closeBt;
    private Activity activity;

    private ChromeclientPower chromeclient;

    //Content
    public void viewReset() {
        this.wv =  findViewById(R.id.webpop_webview);
        this.closeBt = findViewById(R.id.webpop_close);
        this.g = new Global();
        this.csp = new Custom_SharedPreferences(this);


        this.wv.setLongClickable(true);
        this.wv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                WebView.HitTestResult hitTestResult = wv.getHitTestResult();
                switch (hitTestResult.getType()) {

                    case WebView.HitTestResult.IMAGE_TYPE:
                        final String typeUrl = hitTestResult.getExtra();
                        String[] temps = typeUrl.split("/");
                        final String fileNames = temps[temps.length - 1];
                        new MaterialDialog.Builder(longCLickContext).title("Image")
                                .content("Do you want to download the image?")
                                .positiveText("OK").negativeText("Cancel").theme(Theme.LIGHT).onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick( MaterialDialog dialog,  DialogAction which) {
                                new Download(typeUrl,longCLickContext,fileNames);
                            }
                        }).show();
                        break;
                }
                return false;
            }
        });
    }

    public String urlSetting(String paramUrl) {
        String deviceid = csp.getValue("deviceid","");
        String url = Global.main_url + paramUrl;


        if (paramUrl.startsWith("http") || paramUrl.startsWith("https")) {
            url = paramUrl;
        }
        if ( paramUrl.contains("?") )url += "&";
        else url += "?";
        url += "deviceid="+deviceid+"&device=android";

        return url;
    }

    private void listenerRegster() {
        this.closeBt.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_webview);
        this.viewReset();
        this.listenerRegster();

        Intent intent = new Intent(this.getIntent());
        this.paramUrl = intent.getStringExtra("paramUrl");
        Log.d("PoPUrl_param",this.paramUrl);

        this.chromeclient = new ChromeclientPower(this,this,this.wv);
        this.wv.setWebViewClient(new WebviewCustomClient());
        this.wv.getSettings().setUseWideViewPort(true);
        this.wv.getSettings().setJavaScriptEnabled(true);
        this.wv.getSettings().setLoadWithOverviewMode(true);
        this.wv.getSettings().setDefaultTextEncodingName("utf-8");
        this.wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.wv.getSettings().setSupportMultipleWindows(false);
        this.wv.getSettings().setDomStorageEnabled(true);
        this.wv.getSettings().setBuiltInZoomControls(true);
        this.wv.getSettings().setDisplayZoomControls(false);
        this.wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.wv.loadUrl(this.urlSetting(this.paramUrl));

    }


    private class WebviewCustomClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            String[] urlCut = url.split("/");
            Log.d("NowUrl",url);
            if (url.contains(Global.main_url) == false) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            } else if ( g.extPDFSearch(urlCut[urlCut.length - 1]) ) {
                isPdf = true;
                Intent content = new Intent(getApplicationContext(), Download_PDFViewerActivity.class);
                content.putExtra("url", url);
                content.addFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(content);
                overridePendingTransition(R.anim.anim_slide_in_bottom_login,0);
                // view.loadUrl(doc);
                return true;
            } else if ( g.extSearch(urlCut[urlCut.length - 1]) ) { //기타 문서 Search
                new Download(url,getApplicationContext(),urlCut[urlCut.length - 1]);
                return true;
            } else if ( g.imgExtSearch(urlCut[urlCut.length - 1]) ) { //이미지 Search
                Intent content = new Intent(getApplicationContext(), PopWebviewActivity.class);
                content.putExtra("paramUrl", url);
                content.addFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(content);
                overridePendingTransition(R.anim.anim_slide_in_bottom_login,0);
                return true;
            }  else if ( url.contains("glance.php")) {
//                Intent setting = new Intent(getApplicationContext(), GlanceActivity.class);
//                startActivity(setting);
                return true;
            }  else if (urlCut[urlCut.length -1].equals("back.php")) {
                if (wv.canGoBack()) {
                    wv.goBack();
                } else {
                    finish();
                }
                return true;
            }
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("onPageStarted",url);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            Log.d("onLoadResource",url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d("onPageFinished",url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Toast.makeText(getApplicationContext(), "서버와 연결이 끊어졌습니다", Toast.LENGTH_SHORT ).show();
            view.loadUrl("about:blank");
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.webpop_close :
                finish();
                break;
        }

    }
}
