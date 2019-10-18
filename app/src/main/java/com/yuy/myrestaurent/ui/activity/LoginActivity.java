package com.yuy.myrestaurent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.yuy.myrestaurent.R;
import com.yuy.myrestaurent.bean.User;
import com.yuy.myrestaurent.biz.UserBiz;
import com.yuy.myrestaurent.net.CommonCallback;
import com.yuy.myrestaurent.utils.SPStaticUtils;
import com.yuy.myrestaurent.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ed_username)
    EditText edUsername;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_regist)
    TextView tvRegist;

    private UserBiz mUserBiz = new UserBiz();
    String name, password;

    public static final String KEY_USERNAME = "key_username";
    public static final String KEY_PASSWORD = "key_password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        ButterKnife.bind(this);
        //
        initIntent(getIntent());

    }


    //如果注册成功从注册携带注册数据到登录界面
    public static void launch(Context context, String name, String password) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_USERNAME, name);
        intent.putExtra(KEY_PASSWORD, password);
        context.startActivity(intent);
    }


    /**
     *如果此Activity实例还存在在栈内 则
     * 已singleTop 和 singleTask 启动的Activity 不会回调onCreate onStart 方法
     *  其会回调 onNewIntent 方法
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //处理 发送过来的 数据
        initIntent(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //由于在Application中PersistentCookieStore 持久化了Cookie
        //所以此处需要将Cookie 清除， 清除方式如下
        CookieJarImpl cookieJar = (CookieJarImpl) OkHttpUtils.getInstance().getOkHttpClient()
                .cookieJar();
        cookieJar.getCookieStore().removeAll();
    }

    private void initIntent(Intent intent) {
        if (null == intent) {
            return;
        }

        String username = intent.getStringExtra(KEY_USERNAME);
        String password = intent.getStringExtra(KEY_PASSWORD);

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            return;
        }

        edPassword.setText(password);
        edUsername.setText(username);
    }

    @OnClick({R.id.tv_regist, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_regist:
                //
                toRegisterActivity();

                break;
            case R.id.btn_login:

                getUserInfo();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                    ToastUtils.toastByNormal(this, "name和password不能为空！");
                    return;
                }

                startLoadingProgress();

                //登陆
                mUserBiz.login(name, password, new CommonCallback<User>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        ToastUtils.toastByNormal(LoginActivity.this, e.getMessage());

                    }

                    @Override
                    public void onSucess(User response) {
                        stopLoadingProgress();
                        //保存用户信息
                        SPStaticUtils.put(KEY_USERNAME, name);
                        SPStaticUtils.put(KEY_PASSWORD, password);

                        ToastUtils.toastByNormal(LoginActivity.this, "登陆成功！");

                        //跳转到 订单页面
                        toOrderActivity();

                        finish();

                    }
                });

                break;
        }
    }

    private void toOrderActivity() {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
        finish();

    }

    private void toRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }

    private void getUserInfo() {
        name = edUsername.getText().toString();
        password = edPassword.getText().toString();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserBiz.onDestory();
    }
}
