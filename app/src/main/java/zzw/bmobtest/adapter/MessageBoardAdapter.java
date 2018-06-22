package zzw.bmobtest.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import zzw.bmobtest.R;
import zzw.bmobtest.bean.MessageBean;

/**
 * Created by ASUS on 2018/6/9.
 */

public class MessageBoardAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {

    public MessageBoardAdapter(@LayoutRes int layoutResId, @Nullable List<MessageBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
/*        //可链式调用赋值
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_content, item.getContent())
                .addOnClickListener(R.id.iv_img)    //给图标添加点击事件
                .setImageResource(R.id.iv_img, R.mipmap.ic_launcher);

        //获取当前条目position
        //int position = helper.getLayoutPosition();*/
        helper.setText(R.id.tv_content, item.getContent());
        helper.setText(R.id.tv_username, item.getUsername());



    }
}
