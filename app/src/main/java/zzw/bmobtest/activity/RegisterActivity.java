package zzw.bmobtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import zzw.bmobtest.R;
import zzw.bmobtest.base.BaseActivity;
import zzw.bmobtest.bean.UserBean;
import zzw.bmobtest.constant.Constant;
import zzw.bmobtest.util.LogUtil;
import zzw.bmobtest.util.SPUtils;
import zzw.bmobtest.util.ToastUtil;
import zzw.bmobtest.view.InputView;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.inputv_username)
    InputView inputvUsername;
    @BindView(R.id.inputv_password)
    InputView inputvPassword;
    @BindView(R.id.inputv_two_password)
    InputView inputvTwoPassword;

    @Override
    public int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("注册");
        }
    }

    @Override
    public void initData() {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {

        if (TextUtils.isEmpty(inputvUsername.getInputString()) ) {
            ToastUtil.showToast("账号不能为空");
            return ;
        }
        if (TextUtils.isEmpty(inputvPassword.getInputString()) ) {
            ToastUtil.showToast("密码不能为空");
            return ;
        }
        if (TextUtils.isEmpty(inputvTwoPassword.getInputString()) ) {
            ToastUtil.showToast("需要二次输入密码");
            return ;
        }
        if (!inputvPassword.getInputString().equals(inputvTwoPassword.getInputString())) {
            ToastUtil.showToast("两次输入密码不一致");
            return ;
        }

        //查询该用户名是否已经注册
        isRegistered();



    }

    private void isRegistered(){
        BmobQuery<UserBean> query = new BmobQuery<UserBean>();
        query.addWhereEqualTo("name", inputvUsername.getInputString());
        query.setLimit(1);
        query.findObjects(new FindListener<UserBean>() {
            @Override
            public void done(List<UserBean> object, BmobException e) {
                if(e==null){
                    if (object == null || object.size() ==0) {

                       addNewUser();
                    } else {
                        ToastUtil.showToast("该用户已注册");
                    }
                }else{
                    ToastUtil.showToast("注册失败");
                    //添加新用户
                    //addNewUser();
                   // LogUtil.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private void addNewUser(){
        UserBean p2 = new UserBean();
        p2.setName(inputvUsername.getInputString());
        p2.setPassword(inputvPassword.getInputString());
        p2.save(new SaveListener<String>()
        {
            @Override
            public void done(String objectId, BmobException e)
            {
                if (e == null)
                {
                    ToastUtil.showToast("添加用户成功" );
                    SPUtils.put(RegisterActivity.this, Constant.USERNAME, inputvUsername.getInputString());
                    SPUtils.put(RegisterActivity.this, Constant.PASSWORD, inputvPassword.getInputString());
                    SPUtils.put(RegisterActivity.this, Constant.OBJECTID, objectId);

                    Intent result = new Intent();
                    result.putExtra(Constant.USERNAME, inputvUsername.getInputString());
                    result.putExtra(Constant.PASSWORD, inputvPassword.getInputString());
                    setResult(1, result);
                    finish();
                }
                else
                {
                    ToastUtil.showToast("添加用户失败：" + e.getMessage());
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
