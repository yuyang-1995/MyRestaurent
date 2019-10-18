package com.yuy.myrestaurent.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuy.myrestaurent.R;
import com.yuy.myrestaurent.bean.Order;
import com.yuy.myrestaurent.bean.Product;
import com.yuy.myrestaurent.biz.OrderBiz;
import com.yuy.myrestaurent.biz.ProductBiz;
import com.yuy.myrestaurent.net.CommonCallback;
import com.yuy.myrestaurent.ui.adapter.ProductListRvAdapter;
import com.yuy.myrestaurent.ui.view.refresh.SwipeRefresh;
import com.yuy.myrestaurent.ui.view.refresh.SwipeRefreshLayout;
import com.yuy.myrestaurent.ui.vo.ProductItem;
import com.yuy.myrestaurent.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 产品列表：
 */
public class ProductListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    @BindView(R.id.swrf)
    SwipeRefreshLayout swrf;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.btn_pay)
    Button btnPay;

    private int currentPage;

    private float mTotalPrice;  //界面的 订单总价
    private int mTotalCount;   //界面的 订单总数

    private Order mOrder = new Order();
    private OrderBiz mOrderBiz = new OrderBiz();
    private ProductBiz mProductBiz = new ProductBiz();

    private ProductListRvAdapter mProductListRvAdapter;
    private List<ProductItem> mProductItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);

        setUpToolBar();
        setTitle("点餐");
        loadDatas();

        initEvent();
    }

    private void initEvent() {

        //刷新控件
        swrf.setMode(SwipeRefresh.Mode.BOTH);
        //设置旋转颜色
        swrf.setColorSchemeColors(Color.RED, Color.BLACK, Color.GREEN, Color.YELLOW);

        mProductListRvAdapter = new ProductListRvAdapter(this, mProductItems);

        rvProduct.setAdapter(mProductListRvAdapter);

        rvProduct.setLayoutManager(new LinearLayoutManager(this));

        //下拉
        swrf.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDatas();

            }
        });

        //上拉
        swrf.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                loadMore();

            }
        });

        //数量按钮  注册监听
        mProductListRvAdapter.setOnProductListener(new ProductListRvAdapter.OnProductListener() {
            @Override
            public void onProductAdd(ProductItem productItem) {
                //更新ui
                mTotalCount++;
                tvNum.setText("数量：" + mTotalCount);

                mTotalPrice += productItem.getPrice();
                btnPay.setText(mTotalPrice + "元 立即支付");

                //修改 商品 数量 map
                //ProductItem 继承自 Product
                mOrder.addProduct(productItem);
            }

            @Override
            public void onProductSub(ProductItem productItem) {
                mTotalCount--;
                tvNum.setText("数量：" + mTotalCount);

                mTotalPrice -= productItem.getPrice();

                if (mTotalCount == 0) {
                    mTotalPrice = 0;
                }

                btnPay.setText(mTotalPrice + "元 立即支付");

                mOrder.removeProduct(productItem);
            }
        });

    }

    /**
     * 下拉
     */
    private void loadDatas() {

        startLoadingProgress();

        mProductBiz.listByPage(0, new CommonCallback<List<Product>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();

                if (swrf.isRefreshing()) {
                    swrf.setRefreshing(false);
                }

                ToastUtils.toastByNormal(ProductListActivity.this, e.getMessage());

            }

            @Override
            public void onSucess(List<Product> response) {

                stopLoadingProgress();

                swrf.setRefreshing(false);
                currentPage = 0;

                mProductItems.clear();

                //遍历获取的 商品集合 创建 mProductItems
                for (Product p : response) {
                    mProductItems.add(new ProductItem(p));
                }

                //刷新rv
                mProductListRvAdapter.notifyDataSetChanged();

                //
                mTotalCount = 0;
                mTotalPrice = 0;

                tvNum.setText("数量：" + mTotalCount);
                btnPay.setText(mTotalPrice + "元 立即支付");
            }
        });

    }

    //上拉
    private void loadMore() {

        startLoadingProgress();

        mProductBiz.listByPage(++currentPage, new CommonCallback<List<Product>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                swrf.setPullUpRefreshing(false);
                ToastUtils.toastByNormal(ProductListActivity.this, e.getMessage());
                currentPage--;
            }

            @Override
            public void onSucess(List<Product> response) {
                stopLoadingProgress();

                 swrf.setPullUpRefreshing(false);

                if (response.size() <= 0) {
                    ToastUtils.toastByNormal(ProductListActivity.this, "人家也是有底线的");
                    return;
                }

                ToastUtils.toastByNormal(ProductListActivity.this, "又找到了" + response.size() + "条数据！");

                for (Product p : response) {
                    mProductItems.add(new ProductItem(p));
                }

                //刷新 RecyclerView
                mProductListRvAdapter.notifyDataSetChanged();
            }
        });


    }


    @OnClick(R.id.btn_pay)
    public void onViewClicked() {

        if (mTotalCount <= 0) {
            ToastUtils.toastByNormal(ProductListActivity.this, "未选择菜品！");
            return;
        }

        //创建订单
        mOrder.setCount(mTotalCount);
        mOrder.setPrice(mTotalPrice);
        mOrder.setRestaurant(mProductItems.get(0).getRestaurant());

        startLoadingProgress();

        mOrderBiz.add(mOrder, new CommonCallback<String>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                ToastUtils.toastByNormal(ProductListActivity.this, e.getMessage());
                toLoginActivity();
            }

            @Override
            public void onSucess(String response) {
                stopLoadingProgress();
                ToastUtils.toastByNormal(ProductListActivity.this, "订单支付成功");
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProductBiz.onDestory();
        mOrderBiz.onDestory();
    }

}
