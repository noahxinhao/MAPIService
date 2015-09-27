package com.noah.mapi.services;

import com.noah.mapi.model.SysUser;
import org.hibernate.service.spi.ServiceException;

import java.util.List;

/**
 * Created by noahli on 15/9/25.
 */
public interface SysUserService {
    SysUser addSysUser(SysUser sysUser) throws ServiceException, com.noah.mapi.exception.ServiceException;

    boolean deleteSysUser(SysUser sysUser) throws ServiceException, com.noah.mapi.exception.ServiceException;

    SysUser updateSysUser(SysUser sysUser) throws ServiceException, com.noah.mapi.exception.ServiceException;

    SysUser getSysUserByAccount(String account) throws ServiceException;

    SysUser getSysUserByAccountAndPwd(String account, String pwd) throws ServiceException, com.noah.mapi.exception.ServiceException;

    List<SysUser> getSysUserByFilter() throws ServiceException;
}
