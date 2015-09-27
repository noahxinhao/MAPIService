package com.noah.mapi.util;

import com.noah.mapi.exception.ApiError;
import org.apache.log4j.Logger;


/**
 * Created by jacobdong on 15/6/3.
 */
public class JsonResponse<T> {

    private static final Logger LOG = Logger.getLogger(JsonResponse.class);

    /**
     * 访问接口是否成功
     */
    private boolean success;

    /**
     * 返回数据
     */
    private T data;
    private Integer code;
    private String error;
    private String errorDesc;

    public JsonResponse(T data) {
        this.success = true;
        this.data = data;
    }

    public JsonResponse() {
        //super
    }

    public static JsonResponse createError(ApiError error) {
        JsonResponse jsonResponse = new JsonResponse();

        jsonResponse.success = false;
        jsonResponse.error = error.name();
        jsonResponse.errorDesc = error.getDesc();
        jsonResponse.code = error.getCode();

        LOG.debug(error.toString());
        return jsonResponse;
    }

    public static JsonResponse createError(String error) {
        JsonResponse jsonResponse = new JsonResponse();

        jsonResponse.success = false;
        jsonResponse.error = "api接口请求失败";
        jsonResponse.errorDesc = error;
        LOG.debug(error.toString());
        return jsonResponse;
    }

    public static JsonResponse createError(ApiError error, String detail) {
        JsonResponse jsonResponse = new JsonResponse();

        jsonResponse.success = false;
        jsonResponse.error = error.name();
        jsonResponse.errorDesc = error.getDesc() + " , " + detail;
        jsonResponse.code = error.getCode();

        LOG.debug(error.toString());
        return jsonResponse;
    }


    //get-set
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
