package cn.ucai.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.dao.SharePrefrenceUtils;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OnSetAvatarListener;
import cn.ucai.fulicenter.utils.ResultUtils;
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
    OnSetAvatarListener mOnSetAvatarListener;

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
        if(user==null){
            finish();
            return;
        }
        showInfo();
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.layout_user_profile_avatar, R.id.layout_user_profile_username, R.id.layout_user_profile_nickname, R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_user_profile_avatar:
                mOnSetAvatarListener = new OnSetAvatarListener(mContext,R.id.layout_upload_avatar,
                        user.getMuserName(),I.AVATAR_TYPE_USER_PATH);
                break;
            case R.id.layout_user_profile_username:
                CommonUtils.showLongToast(R.string.username_connot_be_modify);
                break;
            case R.id.layout_user_profile_nickname:
                MFGT.gotoUpdateNick(mContext);
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

    @Override
    protected void onResume() {
        super.onResume();
        showInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e("onActivityResult,requestCode="+requestCode+",resultCode="+resultCode);
        if(resultCode!=RESULT_OK){
            return;
        }
        mOnSetAvatarListener.setAvatar(requestCode,data,mIvUserProfileAvatar);
        if(requestCode== I.REQUEST_CODE_NICK){
            CommonUtils.showLongToast(R.string.update_user_nick_success);
        }
        if(requestCode==OnSetAvatarListener.REQUEST_CROP_PHOTO){
            updateAvatar();
        }
    }

    private void updateAvatar() {
        //file=/storage/emulated/0/Android/data/cn.ucai.fulicenter/files/Pictures/a952700
        //file=/storage/emulated/0/Android/data/cn.ucai.fulicenter/files/Pictures/user_avatar/a952700.jpg
        File file = new File(OnSetAvatarListener.getAvatarPath(mContext,
                user.getMavatarPath()+"/"+user.getMuserName()
                +I.AVATAR_SUFFIX_JPG));
        L.e("file="+file.exists());
        L.e("file="+file.getAbsolutePath());
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.update_user_avatar));
        pd.show();
        NetDao.updateAvatar(mContext, user.getMuserName(), file, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                L.e("s="+s);
                Result result = ResultUtils.getResultFromJson(s,User.class);
                L.e("result="+result);
                if(result==null){
                    CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                }else{
                    User u = (User) result.getRetData();
                    if(result.isRetMsg()){
                        FuLiCenterApplication.setUser(u);
                        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(u),mContext,mIvUserProfileAvatar);
                        CommonUtils.showLongToast(R.string.update_user_avatar_success);
                    }else{
                        CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                L.e("error="+error);
            }
        });
    }

    private void showInfo(){
        user = FuLiCenterApplication.getUser();
        if(user!=null){
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext,mIvUserProfileAvatar);
            mTvUserProfileName.setText(user.getMuserName());
            mTvUserProfileNick.setText(user.getMuserNick());
        }
    }
}
