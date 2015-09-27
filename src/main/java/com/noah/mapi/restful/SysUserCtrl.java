package com.noah.mapi.restful;

import com.noah.mapi.exception.ServiceException;
import com.noah.mapi.model.SysUser;
import com.noah.mapi.services.SysUserService;
import com.noah.mapi.exception.ApiError;
import com.noah.mapi.util.JsonResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by noahli on 15/9/25.
 */
@Controller
@RequestMapping(value = "/user")
public class SysUserCtrl {
    @Resource
    SysUserService sysUserServiceImpl;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public JsonResponse userLogin(@RequestBody SysUser sysUser) {
        if (null == sysUser) {
            return JsonResponse.createError("用户信息为空");
        }
        SysUser user = null;

        try {
            user = sysUserServiceImpl.getSysUserByAccountAndPwd(sysUser.getAccount(), sysUser.getPassword());
        } catch (ServiceException e) {
            return JsonResponse.createError(ApiError.SYS_USER_IS_NOT_EXIT, e.getError().getDesc());
        }

        return new JsonResponse(user);
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public JsonResponse userSignUp(@RequestBody SysUser sysUser) {
        SysUser user = null;
        try {
            user = sysUserServiceImpl.addSysUser(sysUser);
        } catch (ServiceException e) {
            return JsonResponse.createError(ApiError.SYS_USER_SIGN_UP_ERROR, e.getError().getDesc());
        }
        return new JsonResponse(user);
    }
}
