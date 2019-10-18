package com.yuy.myrestaurent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;
import com.yuy.myrestaurent.R;
import com.yuy.myrestaurent.bean.Order;
import com.yuy.myrestaurent.config.Config;
import com.yuy.myrestaurent.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  接受订单列表 列表项数据， 展示 订单详情
 */
public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.id_iv_icon)
    ImageView idIvIcon;
    @BindView(R.id.id_tv_title)
    TextView idTvTitle;
    @BindView(R.id.id_tv_desc)
    TextView idTvDesc;
    @BindView(R.id.id_tv_price)
    TextView idTvPrice;
    @BindView(R.id.activity_detail)
    LinearLayout activityDetail;

    private static final String KEY_ORDER = "key_order";
    Order mOrder;

    //调用此方法, 传递序列化订单对象到 详情界面
    public static void launch(Context context, Order order) {

        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(KEY_ORDER,order);
        context.startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);

        setUpToolBar();
        setTitle("订单详情");

        //获取数据
        initIntent();
    }

    //获取解析数据
    private void initIntent() {
        Intent intent = getIntent();

        if (null == intent) {
            return;
        }

        mOrder = (Order) intent.getSerializableExtra(KEY_ORDER);

        if (mOrder == null) {
            ToastUtils.toastByNormal(this,"商品未取到");
            finish();
            return;
        }

        //注入数据
        Picasso.with(this)
                .load(Config.baseUrl + mOrder.getRestaurant().getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(idIvIcon);

        idTvTitle.setText(mOrder.getRestaurant().getName());

        //某商品的数量
        List<Order.ProductVo> ps = mOrder.ps;

        StringBuilder sb = new StringBuilder();

        for (Order.ProductVo productVo : ps) {

            sb.append(productVo.product.getName())
                    .append(" * " + productVo.count)
                    .append("\n");

        }

        idTvDesc.setText(sb.toString());
        idTvPrice.setText("共消费：" + mOrder.getPrice() + "元");
    }


}
