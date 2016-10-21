package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.view.DisplayUtils;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.nick)
    EditText mNick;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.confirm_password)
    EditText mConfirmPassword;
    @BindView(R.id.btn_register)
    Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(this,"账户注册");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
