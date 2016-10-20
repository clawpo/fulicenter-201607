package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;

/**
 * Created by clawpo on 2016/10/20.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;

    public CategoryAdapter(Context context, ArrayList<CategoryGroupBean> groupList,
                           ArrayList<ArrayList<CategoryChildBean>> childList) {
        mContext = context;
        mGroupList = new ArrayList<>();
        mGroupList.addAll(groupList);
        mChildList = new ArrayList<>();
        mChildList.addAll(childList);
    }

    @Override
    public int getGroupCount() {
        return mGroupList != null ? mGroupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ?
                mChildList.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return mGroupList != null ? mGroupList.get(groupPosition) : null;
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ?
                mChildList.get(groupPosition).get(childPosition) : null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpand, View view, ViewGroup viewGroup) {
        GroupViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_group, null);
            holder = new GroupViewHolder(view);
            view.setTag(holder);
        } else {
            view.getTag();
            holder = (GroupViewHolder) view.getTag();
        }
        CategoryGroupBean group = getGroup(groupPosition);
        if (group != null) {
            ImageLoader.downloadImg(mContext, holder.mIvGroupThumb, group.getImageUrl());
            holder.mTvGroupName.setText(group.getName());
            holder.mIvIndicator.setImageResource(isExpand ? R.mipmap.expand_off : R.mipmap.expand_on);
        }
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_child, null);
            holder = new ChildViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }
        final CategoryChildBean child = getChild(groupPosition, childPosition);
        if(child!=null){
            ImageLoader.downloadImg(mContext,holder.mIvCategoryChildThumb,child.getImageUrl());
            holder.mTvCategoryChildName.setText(child.getName());
            holder.mLayoutCategoryChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<CategoryChildBean> list = mChildList.get(groupPosition);
                    String groupName = mGroupList.get(groupPosition).getName();
                    MFGT.gotoCategoryChildActivity(mContext,child.getId(),groupName,list);
                }
            });
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public void initData(ArrayList<CategoryGroupBean> groupList,
                         ArrayList<ArrayList<CategoryChildBean>> childList) {
        if(mGroupList!=null){
            mGroupList.clear();
        }
        mGroupList.addAll(groupList);
        if(mChildList!=null){
            mChildList.clear();
        }
        mChildList.addAll(childList);
        notifyDataSetChanged();
    }

    class GroupViewHolder {
        @BindView(R.id.iv_group_thumb)
        ImageView mIvGroupThumb;
        @BindView(R.id.tv_group_name)
        TextView mTvGroupName;
        @BindView(R.id.iv_indicator)
        ImageView mIvIndicator;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ChildViewHolder {
        @BindView(R.id.iv_category_child_thumb)
        ImageView mIvCategoryChildThumb;
        @BindView(R.id.tv_category_child_name)
        TextView mTvCategoryChildName;
        @BindView(R.id.layout_category_child)
        RelativeLayout mLayoutCategoryChild;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
