package zzw.bmobtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoLayoutHelper;

import zzw.bmobtest.R;

/**
 * Created by lilingzhi on 2018/6/5.
 */

public class InputView extends AutoLinearLayout {


    LinearLayout ll_input;
    TextView tv_input;
    EditText et_input;



    public InputView(Context context) {
        super(context);
    }

    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 加载当前这个组合控件的布局文件
        View.inflate(context, R.layout.layout_inputview, this);


        // 获取当前组合控件中所有的元素
        ll_input = (LinearLayout) findViewById(R.id.ll_input);
        tv_input = (TextView) findViewById(R.id.tv_input);
        et_input = (EditText) findViewById(R.id.et_input);


        // 加载自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InputView);
        // 获取自定义属性的值
        String leftText = a.getString(R.styleable.InputView_left_text);
        Boolean isPassword = a.getBoolean(R.styleable.InputView_is_password, false);
        Boolean isNumber = a.getBoolean(R.styleable.InputView_is_number, false);
        a.recycle();

        tv_input.setText(leftText);
        if (isPassword) {
            et_input.setTransformationMethod(PasswordTransformationMethod.getInstance()); //设置为密码输入框
        }

        if (isNumber) {
            et_input.setInputType(InputType.TYPE_CLASS_NUMBER);

        }

        et_input.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    ll_input.setBackground(getContext().getResources().getDrawable(R.drawable.login_et_press) );
                } else {

                    ll_input.setBackground(getContext().getResources().getDrawable(R.drawable.login_et_normal) );
                }
            }
        });

    }

    public String getInputString(){
        return et_input.getText().toString();
    }

    public void setInputString(String s) {
        et_input.setText(s);
    }


}