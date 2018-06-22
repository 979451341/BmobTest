package zzw.bmobtest.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;

/**
 * Created by ASUS on 2018/6/3.
 */

public abstract class BaseActivity extends AutoLayoutActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        initView();
        initData();
    }



    public abstract int getLayout();
    public abstract void initView();
    public abstract void initData();

}
