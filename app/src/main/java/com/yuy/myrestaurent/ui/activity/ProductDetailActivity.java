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
import com.yuy.myrestaurent.bean.Product;
import com.yuy.myrestaurent.config.Config;
import com.yuy.myrestaurent.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailActivity extends BaseActivity {

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

    public static final String KEY_PRODUCT = "key_product";

    private Product mProduct;

    public static void launch(Context context, Product product) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(KEY_PRODUCT, product);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);

        setUpToolBar();
        setTitle("详情");

        initIntent();
    }

    private void initIntent() {

        Intent intent = getIntent();

        if (null == intent) {
            ToastUtils.toastByNormal(this, "空数据！");

        }else {

            mProduct = (Product) intent.getSerializableExtra(KEY_PRODUCT);

        }

        if (mProduct == null) {
            ToastUtils.toastByNormal(this, "参数传递错误！");
            return;
        }





        Picasso.with(this)
                .load(Config.baseUrl + mProduct.getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(idIvIcon);

        idTvTitle.setText(mProduct.getName());
        idTvDesc.setText(mProduct.getDescription());
        idTvDesc.setText(mProduct.getPrice() + "元");
    }

}
