package com.noah.mapi.dao;

import com.noah.mapi.model.SysUser;

import java.util.List;

/**
 * Created by noahli on 15/9/25.
 */
public interface SysUserDao {
    SysUser addUser(SysUser user);

    SysUser updateUser(SysUser user);

    boolean deleteUser(SysUser user);

    SysUser getSysUserByAccount(String account);

    SysUser getSysUserByAccountAndPwd(String account, String pwd);

    SysUser getSysUserByEmail(String email);

    List<SysUser> getAllSysUser();
}
