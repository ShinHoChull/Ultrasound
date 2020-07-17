package com.m2comm.ultrasound.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.m2comm.ultrasound.DTO.MenuDTO;
import com.m2comm.ultrasound.R;
import com.m2comm.ultrasound.databinding.ActivityContentBinding;
import com.m2comm.ultrasound.module.ChromeclientPower;
import com.m2comm.ultrasound.module.Custom_SharedPreferences;
import com.m2comm.ultrasound.module.Download;
import com.m2comm.ultrasound.module.Download_PDFViewerActivity;
import com.m2comm.ultrasound.module.Global;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ContentActivity extends AppCompatActivity {

    public static int GRIDVIEW_CODE = 0;

    ActivityContentBinding binding;
    private ChromeclientPower chromeclient;
    private Global g;

    private Bottom bottomActivity;
    private Top topActivity;
    private SubMenuTop subMenuTop;

    private String paramUrl;
    private Custom_SharedPreferences csp;



    private String subCode;
    private int subChildCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        this.binding = DataBindingUtil.setContentView(this , R.layout.activity_content);
        this.binding.setContent(this);

        this.init();
    }

    private void init () {

        this.g = new Global();
        Intent intent = new Intent(this.getIntent());
        this.subCode = intent.getStringExtra("subCode");
        this.subChildCode = intent.getIntExtra("subChildCode",-1);

        this.paramUrl =  this.g.getMenu.get(intent.getStringExtra("subCode")).get(this.subChildCode).getUrl();

        this.csp = new Custom_SharedPreferences(this);
        this.topActivity = new Top(getLayoutInflater(), R.id.top, this, this , this.subCode);
        this.subMenuTop = new SubMenuTop(getLayoutInflater(), R.id.subTop, this, this , this.subCode , this.subChildCode , this);
        this.bottomActivity = new Bottom(getLayoutInflater(), R.id.bottom, this, this);


        this.chromeclient = new ChromeclientPower(this,this,this.binding.webview);
        this.binding.webview.setWebViewClient(new WebviewCustomClient());
        this.binding.webview.setWebChromeClient(this.chromeclient);
        this.binding.webview.getSettings().setUseWideViewPort(true);
        this.binding.webview.getSettings().setJavaScriptEnabled(true);
        this.binding.webview.getSettings().setLoadWithOverviewMode(true);
        this.binding.webview.getSettings().setDefaultTextEncodingName("utf-8");
        this.binding.webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.binding.webview.getSettings().setSupportMultipleWindows(false);
        this.binding.webview.getSettings().setDomStorageEnabled(true);
        this.binding.webview.getSettings().setBuiltInZoomControls(true);
        this.binding.webview.getSettings().setDisplayZoomControls(false);
        this.binding.webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.binding.webview.getSettings().setTextZoom(90);

        this.setUrl( this.subCode , this.subChildCode );
        this.subMenuTop.setScrollPosition(this.subChildCode);
    }

    public void setUrl ( String subCode , int subChildCode ) {
        this.paramUrl = this.g.getMenu.get(subCode).get(subChildCode).getUrl();
        this.binding.webview.loadUrl(this.urlSetting(this.paramUrl));
    }

    public String urlSetting(String paramUrl) {
        String deviceid = csp.getValue("deviceid", "");
        String url = paramUrl;
        if (paramUrl.startsWith("http") || paramUrl.startsWith("https")) {
            url = paramUrl;
        }
        if (paramUrl.contains("?")) url += "&";
        else url += "?";
        url += "deviceid=" + deviceid + "&device=android&id=android&" +
                "login=" + csp.getValue("isLogin", false)+"&name="+csp.getValue("name","")
                +"&regist_sid="+csp.getValue("sid","");

        return url;
    }

    private class WebviewCustomClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            String[] urlCut = url.split("/");
            Log.d("NowUrl",url);

            if ( !url.contains(Global.main_url) ) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            } else if ( g.extPDFSearch(urlCut[urlCut.length - 1]) ) {
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
            } else if ( g.imgExtSearch(urlCut[urlCut.length - 1]) || url.contains("schedule/detail_list.php")) { //이미지 Search
                Intent content = new Intent(getApplicationContext(), PopWebviewActivity.class);
                content.putExtra("paramUrl", url);
                content.addFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(content);
                overridePendingTransition(R.anim.anim_slide_in_bottom_login,0);
                return true;
            }  else if (url.contains("app_question.php")) {

            } else if (url.contains("session/view.php")) {


            }  else if (urlCut[urlCut.length -1].equals("back.php")) {
                if (binding.webview.canGoBack()) {
                    binding.webview.goBack();
                } else {
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
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

    public void onBackPressed() {
        //super.onBackPressed();
        if (this.binding.webview.canGoBack()) {
            this.binding.webview.goBack();
        } else {
            finish();
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            assert data != null;

            if (requestCode == ContentActivity.GRIDVIEW_CODE) {
                this.subChildCode = data.getIntExtra("subChildCode",-1);
                setUrl(this.subCode , this.subChildCode);
                this.subMenuTop.setScrollPosition(this.subChildCode);
            }
        }
    }
}