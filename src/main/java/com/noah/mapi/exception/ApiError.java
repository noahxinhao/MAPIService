package com.noah.mapi.exception;

/**
 * Created by jacobdong on 15/7/15.
 */
public enum ApiError {

    COMMON_GEN_CAPTCHA_ERROR("生成验证码失败", 100000),
    SYS_USER_IS_NOT_EXIT("用户不存在", 100001),
    SYS_USER_SIGN_UP_ERROR("用户注册失败", 100002);
    //机构相关代码
    private String desc;
    private Integer code;


    ApiError(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

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
}
