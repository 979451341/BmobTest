package zzw.bmobtest.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by ASUS on 2018/6/6.
 */

public class UserBean extends BmobObject{

    private String name;
    private String password;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
