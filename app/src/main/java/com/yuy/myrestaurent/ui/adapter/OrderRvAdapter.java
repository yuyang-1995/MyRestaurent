package com.yuy.myrestaurent.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yuy.myrestaurent.R;
import com.yuy.myrestaurent.bean.Order;
import com.yuy.myrestaurent.config.Config;
import com.yuy.myrestaurent.ui.activity.OrderDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: yuyang
 * Date:2019/10/17 21:34
 * Description:
 * Version:
 */
public class OrderRvAdapter extends RecyclerView.Adapter<OrderRvAdapter.OrderRvViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Order> mOrders;

    public OrderRvAdapter(Context context, List<Order> orders) {
        mContext = context;
        mOrders = orders;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OrderRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mLayoutInflater.inflate(R.layout.item_order_list, parent, false);

        return new OrderRvViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRvViewHolder holder, int position) {

        Order order = mOrders.get(position);

        Picasso.with(mContext)
                .load(Config.baseUrl + order.getRestaurant().getIcon())
                .placeholder(R.drawable.icon)
                .into(holder.ivIamge);

        if (order.ps.size() > 0) {
            holder.tvLabel.setText(order.ps.get(0).product.getName() + "等" + order.getCount() + "件商品");

        }else {

            holder.tvLabel.setText("无消费");
        }

        holder.tvPrice.setText(order.getRestaurant().getName());
        holder.tvResname.setText("共消费：" + order.getPrice() + "元");
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }


    /**
     * VH
     */
    class OrderRvViewHolder extends RecyclerView.ViewHolder {

        //这样就不要 findViewById 了
        @BindView(R.id.iv_iamge)
        ImageView ivIamge;
        @BindView(R.id.tv_resname)
        TextView tvResname;
        @BindView(R.id.tv_label)
        TextView tvLabel;
        @BindView(R.id.tv_price)
        TextView tvPrice;


        public OrderRvViewHolder(@NonNull View itemView) {
            super(itemView);
            //RV 中使用ButterKnife
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 跳到订单详情
                    int position = getLayoutPosition();
                    Order order = mOrders.get(position);

                    OrderDetailActivity.launch(mContext, order);

                }
            });
        }
    }
}
