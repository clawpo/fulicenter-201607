package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.NewGoodsBean;

/**
 * Created by clawpo on 2016/10/17.
 */

public class GoodsAdapter extends Adapter {
    Context mContext;
    List<NewGoodsBean> mList;

    public GoodsAdapter(Context context, List<NewGoodsBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new GoodsViewHolder(View.inflate(mContext, R.layout.item_goods, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(getItemViewType(position)==I.TYPE_FOOTER){

        }else{
            GoodsViewHolder vh = (GoodsViewHolder) holder;
            NewGoodsBean goods = mList.get(position);
            //set image
            vh.mTvGoodsName.setText(goods.getGoodsName());
            vh.mTvGoodsPrice.setText(goods.getCurrencyPrice());
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    static class GoodsViewHolder extends ViewHolder{
        @BindView(R.id.ivGoodsThumb)
        ImageView mIvGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView mTvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView mTvGoodsPrice;
        @BindView(R.id.layout_goods)
        LinearLayout mLayoutGoods;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FooterViewHolder extends ViewHolder{
        @BindView(R.id.tvFooter)
        TextView mTvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
