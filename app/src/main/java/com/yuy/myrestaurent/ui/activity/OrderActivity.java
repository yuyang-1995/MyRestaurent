package com.yuy.myrestaurent.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yuy.myrestaurent.R;
import com.yuy.myrestaurent.bean.Order;
import com.yuy.myrestaurent.bean.Product;
import com.yuy.myrestaurent.biz.OrderBiz;
import com.yuy.myrestaurent.net.CommonCallback;
import com.yuy.myrestaurent.ui.adapter.OrderRvAdapter;
import com.yuy.myrestaurent.ui.view.refresh.SwipeRefresh;
import com.yuy.myrestaurent.ui.view.refresh.SwipeRefreshLayout;
import com.yuy.myrestaurent.utils.SPStaticUtils;
import com.yuy.myrestaurent.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 1：展示 已购订单, 2: 下拉刷新； 3： 上拉刷新 4：跳转到商品活动
 */
public class OrderActivity extends BaseActivity {


    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.btn_order)
    Button btnOrder;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.sprl)
    SwipeRefreshLayout sprl;

    OrderBiz mOrderBiz = new OrderBiz();


    private List<Order> mOrders = new ArrayList<>();
    OrderRvAdapter mOrderRvAdapter;
    private int currentPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        loadDatas();

        initValue();

        initListener();

    }

    private void initValue() {
        String name = SPStaticUtils.getString(LoginActivity.KEY_USERNAME);

        tvUsername.setText(name);

        if (TextUtils.isEmpty(name)) {
            toLoginActivity();
            finish();
            return;
        }

        Picasso.with(this)
                .load(R.drawable.icon)
                .into(ivIcon);

    }

    private void initListener() {

        mOrderRvAdapter = new OrderRvAdapter(this, mOrders);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mOrderRvAdapter);


        sprl.setMode(SwipeRefresh.Mode.BOTH);
        sprl.setColorSchemeColors(Color.RED, Color.BLUE, Color.YELLOW);

        //下拉
        sprl.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDatas();
            }
        });


        //上拉
        sprl.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                loadMore();
            }
        });


        //跳转到产品界面
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, ProductListActivity.class);
                startActivityForResult(intent, 1001);

            }
        });
    }

    //上拉
    private void loadMore() {
        stopLoadingProgress();

        mOrderBiz.listByPage(++currentPage, new CommonCallback<List<Order>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                currentPage--;
                ToastUtils.toastByNormal(OrderActivity.this, e.getMessage());

            }

            @Override
            public void onSucess(List<Order> response) {

                stopLoadingProgress();

                if (response.size() == 0) {
                    ToastUtils.toastByNormal(OrderActivity.this, "木有订单了~");
                    sprl.setPullUpRefreshing(false);
                    return;
                }

                ToastUtils.toastByNormal(OrderActivity.this, "订单加载成功~");
                mOrders.addAll(response);
                mOrderRvAdapter.notifyDataSetChanged();
                sprl.setPullUpRefreshing(false);

            }
        });

    }

    //下拉 下拉清空
    private void loadDatas() {

        startLoadingProgress();

        mOrderBiz.listByPage(0, new CommonCallback<List<Order>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                ToastUtils.toastByNormal(OrderActivity.this,e.getMessage());

                if (sprl.isRefreshing()) {
                    sprl.setRefreshing(false);
                }

                if (e.getMessage().contains("用户未登录")) {
                    toLoginActivity();
                }

            }

            @Override
            public void onSucess(List<Order> response) {
                stopLoadingProgress();
                ToastUtils.toastByNormal(OrderActivity.this,"订单更新成功 : " + response.size());
                mOrders.clear();
                mOrders.addAll(response);
                mOrderRvAdapter.notifyDataSetChanged();

                if (sprl.isRefreshing()) {
                    sprl.setRefreshing(false);
                }
            }
        });
    }


    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 && resultCode == RESULT_OK) {
            loadDatas();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDatas();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrderBiz.onDestory();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                return true;
            } catch (Exception e) {

            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.btn_order)
    public void onViewClicked() {
    }
}
