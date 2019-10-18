package com.yuy.myrestaurent.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.yuy.myrestaurent.R;
import com.yuy.myrestaurent.bean.User;
import com.yuy.myrestaurent.biz.UserBiz;
import com.yuy.myrestaurent.net.CommonCallback;
import com.yuy.myrestaurent.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_repassword)
    EditText etRepassword;
    @BindView(R.id.btn_regist)
    Button btnRegist;

    UserBiz mUserBiz = new UserBiz();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setUpToolBar();
        setTitle("注册");

    }

    @OnClick(R.id.btn_regist)
    public void onViewClicked() {

        String nickname = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String repassword = etRepassword.getText().toString();


        if (TextUtils.isEmpty(nickname)) {
            ToastUtils.toastByNormal(this, "名字为空！");
            return;
        }


        if (!password.equals(repassword) || TextUtils.isEmpty(password)) {
            ToastUtils.toastByNormal(this, "两次输入的密码不同！");
            return;
        }


        startLoadingProgress();
        //注册
        mUserBiz.register(nickname, password, new CommonCallback<User>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                ToastUtils.toastByNormal(RegisterActivity.this, e.getMessage());


            }

            @Override
            public void onSucess(User response) {
                stopLoadingProgress();
                ToastUtils.toastByNormal(RegisterActivity.this, "注册成功！");

                LoginActivity.launch(RegisterActivity.this, nickname, password);

                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserBiz.onDestory();
    }
}
