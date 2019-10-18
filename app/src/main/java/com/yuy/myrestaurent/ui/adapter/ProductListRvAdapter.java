package com.yuy.myrestaurent.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yuy.myrestaurent.R;
import com.yuy.myrestaurent.bean.Product;
import com.yuy.myrestaurent.config.Config;
import com.yuy.myrestaurent.ui.activity.ProductDetailActivity;
import com.yuy.myrestaurent.ui.vo.ProductItem;
import com.yuy.myrestaurent.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: yuyang
 * Date:2019/10/17 23:02
 * Description:
 * Version:
 */
public class ProductListRvAdapter extends RecyclerView.Adapter<ProductListRvAdapter.ProductVHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<ProductItem> mProducts;

    public ProductListRvAdapter(Context context, List<ProductItem> products) {
        mContext = context;
        mProducts = products;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ProductVHolder(mLayoutInflater.inflate(R.layout.item_product_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductVHolder holder, int position) {

        ProductItem productItem = mProducts.get(position);

        Picasso.with(mContext)
                .load(Config.baseUrl + productItem.getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(holder.idIvImage);

        holder.idTvName.setText(productItem.getName());
        holder.idTvCount.setText(productItem.count + "");
        holder.idTvLabel.setText(productItem.getLabel());
        holder.idTvPrice.setText(productItem.getPrice() + "元/份");
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    /**
     * 在 VH 中设置点击事件
     */
     class ProductVHolder extends RecyclerView.ViewHolder {
        ImageView idIvImage;

        TextView idTvName;

        TextView idTvLabel;

        TextView idTvPrice;

        ImageView idIvSub;

        TextView idTvCount;

        ImageView idIvAdd;

        public ProductVHolder(@NonNull View itemView) {
            super(itemView);

            idIvImage = itemView.findViewById(R.id.id_iv_image);
            idIvAdd = itemView.findViewById(R.id.id_iv_add);
            idIvSub = itemView.findViewById(R.id.id_iv_sub);

            idTvPrice = itemView.findViewById(R.id.id_tv_price);
            idTvCount = itemView.findViewById(R.id.id_tv_count);
            idTvLabel = itemView.findViewById(R.id.id_tv_label);
            idTvName = itemView.findViewById(R.id.id_tv_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product product = mProducts.get(getLayoutPosition());

                    ProductDetailActivity.launch(mContext, product);

                }
            });

            // 控件监听 增加数量，
            idIvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   int position = getLayoutPosition();

                   //获取商品 数量，进行添加操作
                    ProductItem productItem = mProducts.get(position);//获取点击的 商品实例
                    productItem.count += 1;
                    idTvCount.setText(productItem.count + "");

                    // 将修改后的商品实例 回调到 活动
                    if (mOnProductListener != null) {
                        mOnProductListener.onProductAdd(productItem);
                    }
                }
            });

            //减少数量
            idIvSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getLayoutPosition();

                    //获取商品 数量，进行添加操作
                    ProductItem productItem = mProducts.get(position);//获取点击的 商品实例

                    if (productItem.count < 0) {
                        ToastUtils.toastByNormal(mContext, "已经是0了！");
                        return;
                    }

                    productItem.count -= 1;
                    idTvCount.setText(productItem.count);

                    // 将修改后的商品实例 回调到 活动
                    if (mOnProductListener != null) {
                        mOnProductListener.onProductSub(productItem);
                    }
                }
            });
        }
    }


    private OnProductListener mOnProductListener;

    public void setOnProductListener(OnProductListener listener) {
        this.mOnProductListener = listener;
    }

    /**
     *通过接口， 回调控件 增加 减少 菜品的数量
     */
    public interface OnProductListener{
        void onProductAdd(ProductItem productItem);
        void onProductSub(ProductItem productItem);
    }

}

