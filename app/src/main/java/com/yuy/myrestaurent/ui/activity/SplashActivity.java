package com.yuy.myrestaurent.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.yuy.myrestaurent.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.btn_sp)
    Button btnSp;

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    removeMessages(2);
                    toLoginActivity();
                    break;
                case 2:
                    removeMessages(1);
                    toLoginActivity();
                    break;
                    default:break;
            }
        }
    };

    private void toLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        mHandler.sendEmptyMessageDelayed(2, 2000);
    }


    @OnClick(R.id.btn_sp)
    public void onViewClicked() {
        mHandler.sendEmptyMessage(1);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        // 外部类Activity生命周期结束时，同时清空消息队列 & 结束Handler生命周期

    }
}


