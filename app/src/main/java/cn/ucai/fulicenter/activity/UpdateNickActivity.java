package cn.ucai.fulicenter.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.dao.UserDao;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.ResultUtils;
import cn.ucai.fulicenter.view.DisplayUtils;

public class UpdateNickActivity extends BaseActivity {
    private static final String TAG =  UpdateNickActivity.class.getSimpleName();

    @BindView(R.id.et_update_user_name)
    EditText mEtUpdateUserName;
    UpdateNickActivity mContext;
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_update_nick);
        ButterKnife.bind(this);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext,getResources().getString(R.string.update_user_nick));
    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();
        if(user!=null){
            mEtUpdateUserName.setText(user.getMuserNick());
            mEtUpdateUserName.setSelectAllOnFocus(true);
        }else{
            finish();
        }
    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.btn_save)
    public void checkNick() {
        if(user!=null){
            String nick = mEtUpdateUserName.getText().toString().trim();
            if(nick.equals(user.getMuserNick())){
                CommonUtils.showLongToast(R.string.update_nick_fail_unmodify);
            }else if(TextUtils.isEmpty(nick)){
                CommonUtils.showLongToast(R.string.nick_name_connot_be_empty);
            }else{
                updateNick(nick);
            }
        }
    }

    private void updateNick(String nick) {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.update_user_nick));
        pd.show();
        NetDao.updateNick(mContext, user.getMuserName(), nick, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s,User.class);
                L.e(TAG,"result="+result);
                if(result==null){
                    CommonUtils.showLongToast(R.string.update_fail);
                }else{
                    if(result.isRetMsg()){
                        User u = (User) result.getRetData();
                        L.e(TAG,"user="+u);
                        UserDao dao = new UserDao(mContext);
                        boolean isSuccess = dao.updateUser(u);
                        if(isSuccess){
                            FuLiCenterApplication.setUser(u);
                            setResult(RESULT_OK);
                            MFGT.finish(mContext);
                        }else{
                            CommonUtils.showLongToast(R.string.user_database_error);
                        }
                    }else{
                        if(result.getRetCode()== I.MSG_USER_SAME_NICK){
                            CommonUtils.showLongToast(R.string.update_nick_fail_unmodify);
                        }else if(result.getRetCode()==I.MSG_USER_UPDATE_NICK_FAIL){
                            CommonUtils.showLongToast(R.string.update_fail);
                        }else{
                            CommonUtils.showLongToast(R.string.update_fail);
                        }
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showLongToast(error);
                L.e(TAG,"error="+error);
            }
        });
    }
}
