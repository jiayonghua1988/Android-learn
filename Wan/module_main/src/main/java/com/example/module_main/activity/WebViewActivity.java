package com.example.module_main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common_base.base.BaseMVPActivity;
import com.example.common_base.constants.Constants;
import com.example.common_base.mvp.IPresenter;
import com.example.common_base.widget.ProgressWebView;
import com.example.module_main.R;
import com.example.module_main.contract.WebContract;
import com.example.module_main.presenter.WebPresenter;

@Route(path = "/web/WebViewActivity")
public class WebViewActivity extends BaseMVPActivity<WebPresenter> implements WebContract.View {


    private ProgressWebView webView;
    private String url;
    private Toolbar toolbar;
    private int id;
    private String title;
    private String author;
    private MenuItem favoriteMenuItem;
    private MenuItem shareMenuItem;
    private boolean hadFavorited = false;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
        webView = findViewById(R.id.wv_web);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleWebViewBack();
            }
        });
        webView.setWebViewCallback(new ProgressWebView.WebViewCallback() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }

            @Override
            public void onLoadResource(WebView view, String url) {

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }

            @Override
            public void onPageLoadComplete() {

            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                return false;
            }

            @Override
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra(Constants.URL);
            id = intent.getIntExtra(Constants.ID, -1);
            title = intent.getStringExtra(Constants.TITLE);
            author = intent.getStringExtra(Constants.AUTHOR);
            toolbar.setTitle(title);
            webView.loadUrl(url);

        }
    }

    private void handleWebViewBack() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected WebPresenter createPresenter() {
        return new WebPresenter();
    }

    @Override
    public void onFavoriteAdded() {

    }

    @Override
    public void onFavoriteDeleted() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webView.removeAllViews();
        webView = null;
    }

    public static void startPage(Activity context, String url, int id, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, url);
        bundle.putInt(Constants.ID, id);
        bundle.putString(Constants.AUTHOR, null);
        bundle.putString(Constants.TITLE, title);

        ARouter.getInstance().build("/web/WebViewActivity")
                .with(bundle)
                .navigation();
        context.overridePendingTransition(R.anim.anim_web_enter, R.anim.anim_alpha);
    }
}
