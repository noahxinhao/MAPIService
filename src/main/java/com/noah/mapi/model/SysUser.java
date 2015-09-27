package com.noah.mapi.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by noahli on 15/9/18.
 */
@Table(name = "sysUser")
@Entity
public class SysUser {
    @Id
    @Column(name = "id", length = 64, nullable = false)
    private String id = UUID.randomUUID().toString().replace("-", "");

    /**
     * 账号 手机号码
     */
    @Column(unique = true, updatable = false, nullable = false, length = 45)
    private String account;

    /**
     * 账号 手机号码
     */
    @Column(nullable = false, length = 45)
    private String password;

    /**
     * 用户昵称
     */
    @Column(nullable = true, length = 45)
    private String nickName;

    /**
     * 用户邮箱
     */
    @Column(unique = true, nullable = true, length = 45)
    private String email;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime = new Date();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
