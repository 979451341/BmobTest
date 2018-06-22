package zzw.bmobtest.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import zzw.bmobtest.R;
import zzw.bmobtest.adapter.MessageBoardAdapter;
import zzw.bmobtest.base.BaseActivity;
import zzw.bmobtest.bean.MessageBean;
import zzw.bmobtest.constant.Constant;
import zzw.bmobtest.util.LogUtil;
import zzw.bmobtest.util.SPUtils;
import zzw.bmobtest.util.ToastUtil;

public class MessageBoardActivity extends BaseActivity {


    @BindView(R.id.recyc)
    RecyclerView recyc;

    public static final String TAG = "MessageBoardActivity";

    RecyclerView.LayoutManager layoutManager;
    MessageBoardAdapter adapter;

    List<MessageBean> dataList = new ArrayList<>();
    @BindView(R.id.fbtn)
    FloatingActionButton fbtn;

    AlertDialog.Builder builder;
    AlertDialog alertDialog ;
    EditText et;
    Button btn_send;
    View view;
    @Override
    public int getLayout() {
        return R.layout.activity_message_board;
    }

    @Override
    public void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("主界面");
        }

        //循环视图
        layoutManager = new LinearLayoutManager(MessageBoardActivity.this);
        adapter = new MessageBoardAdapter(R.layout.item_message_board, dataList);
        recyc.setLayoutManager(layoutManager);
        recyc.setAdapter(adapter);

        //输入对话框
        builder = new AlertDialog.Builder(MessageBoardActivity.this);
        alertDialog = builder.create();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        view = View
                .inflate(MessageBoardActivity.this , R.layout.layout_input_dialog, null);
        alertDialog.setView(view);
        et = (EditText) view.findViewById(R.id.et);
        btn_send = (Button) view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MessageBean messageBean = new MessageBean();
                messageBean.setContent(et.getText().toString());
                messageBean.setUsername(SPUtils.get(MessageBoardActivity.this, Constant.USERNAME, "").toString());

                messageBean.save(new SaveListener<String>()
                {
                    @Override
                    public void done(String objectId, BmobException e)
                    {
                        if (e == null)
                        {
                            addOne(messageBean);
                        }
                        else
                        {
                            ToastUtil.showToast("提交留言失败");
                        }
                        alertDialog.dismiss();
                    }
                });
            }
        });


        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.show();
            }
        });

    }

    @Override
    public void initData() {

        queryAll();


    }

    public void queryAll() {
        BmobQuery<MessageBean> query = new BmobQuery<MessageBean>();
        query.setLimit(100);
        query.findObjects(new FindListener<MessageBean>() {
            @Override
            public void done(List<MessageBean> list, BmobException e) {
                if (e == null) {
                    if (list != null) {
                        dataList.addAll(list);
                        adapter.notifyDataSetChanged();

                    }
                } else {
                    LogUtil.i(TAG, "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });


    }

    public void addOne(MessageBean messageBean){
        dataList.add(messageBean);
        adapter.notifyDataSetChanged();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
