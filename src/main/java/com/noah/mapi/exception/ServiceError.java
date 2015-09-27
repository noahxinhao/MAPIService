package com.noah.mapi.exception;

/**
 * Created by jacobdong on 15/9/1.
 */
public enum ServiceError {
    //对象已存在错误
    SYS_USER_ADD_ERROR("用户邮箱已存在", 10000),
    SYS_USER_ADD_IS_NOT_EXIT("用户不存在", 10001),
    SYS_USER_ADD_ACCOUNT_IS_EXIT("用户手机号码已存在", 10002),
    SYS_USER_IS_NOT_EXIT("用户不存在", 10003),
    SYS_USER_ACCOUNT_MUST_NOT_BLANK("用户名不能为空", 10004),
    SYS_USER_PASSWORD_MUST_NOT_BLANK("密码不能为空", 10005),
    //用户删除操作
    SYS_USER_DELETE_ERROR("删除用户失败", 10006),
    SYS_USER_ADD_EMAIL_IS_EXIT("用户邮箱已存在", 10007);


    private String desc;
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    ServiceError(String desc, Integer code) {
        this.code = code;
        this.desc = desc;
    }
}
