package com.noah.mapi.services.impl;

import com.noah.mapi.dao.SysUserDao;
import com.noah.mapi.exception.ServiceError;
import com.noah.mapi.exception.ServiceException;
import com.noah.mapi.model.SysUser;
import com.noah.mapi.services.SysUserService;
import com.noah.mapi.util.GlobTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by noahli on 15/9/25.
 */

@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

    @Resource
    SysUserDao sysUserDaoImpl;

    @Override
    public SysUser addSysUser(SysUser sysUser) throws ServiceException {
        if (null == sysUser) {
            throw new ServiceException(ServiceError.SYS_USER_ADD_IS_NOT_EXIT);
        }

        if (StringUtils.isBlank(sysUser.getAccount())) {
            throw new ServiceException(ServiceError.SYS_USER_ACCOUNT_MUST_NOT_BLANK);
        }

        if (StringUtils.isBlank(sysUser.getPassword())) {
            throw new ServiceException(ServiceError.SYS_USER_PASSWORD_MUST_NOT_BLANK);
        }

        SysUser checkUserAccount = sysUserDaoImpl.getSysUserByAccount(sysUser.getAccount());

        if (null != checkUserAccount) {
            throw new ServiceException(ServiceError.SYS_USER_ADD_ACCOUNT_IS_EXIT);
        }

        SysUser checkUserEmail = sysUserDaoImpl.getSysUserByEmail(sysUser.getEmail());

        if (null != checkUserEmail) {
            throw new ServiceException(ServiceError.SYS_USER_ADD_EMAIL_IS_EXIT);
        }

        sysUser.setPassword(GlobTools.MD5(sysUser.getPassword(), sysUser.getAccount()));

        SysUser user = sysUserDaoImpl.addUser(sysUser);

        if (null == user) {
            throw new ServiceException(ServiceError.SYS_USER_ADD_ERROR);
        }

        return user;
    }

    @Override
    public boolean deleteSysUser(SysUser sysUser) throws ServiceException {
        if (sysUserDaoImpl.deleteUser(sysUser)) {
            return true;
        } else {
            throw new ServiceException(ServiceError.SYS_USER_DELETE_ERROR);
        }
    }

    @Override
    public SysUser updateSysUser(SysUser sysUser) throws ServiceException {
        SysUser user = sysUserDaoImpl.getSysUserByAccount(sysUser.getAccount());
        if (null == user) {
            throw new ServiceException(ServiceError.SYS_USER_IS_NOT_EXIT);
        }
        return sysUserDaoImpl.updateUser(sysUser);
    }

    @Override
    public SysUser getSysUserByAccount(String account) {
        return sysUserDaoImpl.getSysUserByAccount(account);
    }

    @Override
    public SysUser getSysUserByAccountAndPwd(String account, String pwd) throws ServiceException {
        SysUser sysUser = sysUserDaoImpl.getSysUserByAccountAndPwd(account, pwd);
        if (null == sysUser) {
            throw new ServiceException(ServiceError.SYS_USER_IS_NOT_EXIT);
        }
        return sysUser;
    }

    @Override
    public List<SysUser> getSysUserByFilter() {
        return null;
    }
}
