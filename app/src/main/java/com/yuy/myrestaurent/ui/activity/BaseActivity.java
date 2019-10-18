package com.yuy.myrestaurent.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.yuy.myrestaurent.R;
import com.zhy.http.okhttp.OkHttpUtils;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xff000000);
        }

        //
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("加载中。。。");

    }

    /**
     * ToolBar 设置
     */
    protected void setUpToolBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    protected void toLoginActivity() {

        Intent login = new Intent(this, LoginActivity.class);
        //清除LoginActivity 上面的活动  并以栈内复用
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);
        finish();

    }


    protected void startLoadingProgress() {

      mProgressDialog.show();

    }


    protected void stopLoadingProgress() {

        if (mProgressDialog.isShowing() && mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        stopLoadingProgress();
        mProgressDialog = null;

    }


}
