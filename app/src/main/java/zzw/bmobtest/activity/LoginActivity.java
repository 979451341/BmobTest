package zzw.bmobtest.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import zzw.bmobtest.R;
import zzw.bmobtest.base.BaseActivity;
import zzw.bmobtest.bean.UserBean;
import zzw.bmobtest.constant.Constant;
import zzw.bmobtest.util.LogUtil;
import zzw.bmobtest.util.SPUtils;
import zzw.bmobtest.util.ToastUtil;
import zzw.bmobtest.view.InputView;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.inputv_username)
    InputView inputvUsername;
    @BindView(R.id.inputv_password)
    InputView inputvPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;


    public static final int result_register = 1001;
    public static final int result_perm = 1002;
    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("登录");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.WAKE_LOCK}, result_perm);
        }

        String username = (String)SPUtils.get(this, Constant.USERNAME, "");
        String password = (String)SPUtils.get(this, Constant.PASSWORD, "");
        inputvUsername.setInputString(username);;
        inputvPassword.setInputString(password);



    }

    @Override
    public void initData() {

    }



    @OnClick({R.id.btn_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(inputvUsername.getInputString()) ) {
                    ToastUtil.showToast("账号不能为空");
                    return ;
                }
                if (TextUtils.isEmpty(inputvPassword.getInputString()) ) {
                    ToastUtil.showToast("密码不能为空");
                    return ;
                }
                login();
                break;
            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                startActivityForResult(intent, result_register);
                break;
        }
    }

    private void login(){
        BmobQuery<UserBean> query = new BmobQuery<UserBean>();
        query.addWhereEqualTo("name", inputvUsername.getInputString());
        query.addWhereEqualTo("password", inputvPassword.getInputString());
        query.setLimit(1);
        query.findObjects(new FindListener<UserBean>() {
            @Override
            public void done(List<UserBean> object, BmobException e) {
                if(e==null){
                    if (object == null || object.size() ==0) {
                        //添加新用户
                        ToastUtil.showToast("用户名或密码错误");
                    } else {
                        ToastUtil.showToast("成功登陆");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }else{
                    LogUtil.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 1) {
            return ;
        }

        if (requestCode == result_register) {
            String username = data.getStringExtra(Constant.USERNAME);
            String password = data.getStringExtra(Constant.PASSWORD);
            inputvUsername.setInputString(username);
            inputvPassword.setInputString(password);

        }

    }
}
