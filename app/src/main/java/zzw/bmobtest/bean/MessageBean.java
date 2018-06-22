package zzw.bmobtest.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by ASUS on 2018/6/9.
 */

public class MessageBean extends BmobObject{

    private String content;
    private String username;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
