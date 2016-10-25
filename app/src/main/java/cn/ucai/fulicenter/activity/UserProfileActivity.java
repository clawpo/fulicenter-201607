package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.dao.SharePrefrenceUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.view.DisplayUtils;

public class UserProfileActivity extends BaseActivity {

    @BindView(R.id.iv_user_profile_avatar)
    ImageView mIvUserProfileAvatar;
    @BindView(R.id.tv_user_profile_name)
    TextView mTvUserProfileName;
    @BindView(R.id.tv_user_profile_nick)
    TextView mTvUserProfileNick;

    UserProfileActivity mContext;
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext,getResources().getString(R.string.user_profile));
    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();
        if(user!=null){
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext,mIvUserProfileAvatar);
            mTvUserProfileName.setText(user.getMuserName());
            mTvUserProfileNick.setText(user.getMuserNick());
        }else{
            finish();
        }
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.layout_user_profile_avatar, R.id.layout_user_profile_username, R.id.layout_user_profile_nickname, R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_user_profile_avatar:
                break;
            case R.id.layout_user_profile_username:
                break;
            case R.id.layout_user_profile_nickname:
                break;
            case R.id.btn_logout:
                logout();
                break;
        }
    }

    private void logout() {
        if(user!=null){
            SharePrefrenceUtils.getInstence(mContext).removeUser();
            FuLiCenterApplication.setUser(null);
            MFGT.gotoLogin(mContext);
        }
        finish();
    }
}
