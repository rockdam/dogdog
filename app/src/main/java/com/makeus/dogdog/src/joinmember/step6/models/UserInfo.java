package com.makeus.dogdog.src.joinmember.step6.models;

import java.io.Serializable;

public class UserInfo implements Serializable {
    String email ;
    String password;
    String nickName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
