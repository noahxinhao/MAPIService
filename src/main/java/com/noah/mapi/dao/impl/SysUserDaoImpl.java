package com.noah.mapi.dao.impl;

import com.noah.mapi.dao.SysUserDao;
import com.noah.mapi.model.SysUser;
import com.noah.mapi.util.GlobTools;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by noahli on 15/9/25.
 */
@Repository
public class SysUserDaoImpl implements SysUserDao {

    @Resource
    SessionFactory sessionFactory;

    @Override
    public SysUser addUser(SysUser user) {
        try {
            sessionFactory.getCurrentSession().save(user);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SysUser updateUser(SysUser user) {
        try {
            sessionFactory.getCurrentSession().update(user);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean deleteUser(SysUser user) {
        try {
            sessionFactory.getCurrentSession().delete(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public SysUser getSysUserByAccount(String account) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SysUser.class);
        criteria.add(Restrictions.eq("account", account));
        Object object = criteria.uniqueResult();
        if (null != object) {
            return (SysUser) object;
        }
        return null;
    }

    @Override
    public SysUser getSysUserByAccountAndPwd(String account, String pwd) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SysUser.class);
        criteria.add(Restrictions.eq("account", account));
        criteria.add(Restrictions.eq("password", GlobTools.MD5(pwd, account)));
        Object object = criteria.uniqueResult();
        if (null != object) {
            return (SysUser) object;
        }
        return null;
    }

    @Override
    public SysUser getSysUserByEmail(String email) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SysUser.class);
        criteria.add(Restrictions.eq("email", email));
        Object object = criteria.uniqueResult();
        if (null != object) {
            return (SysUser) object;
        }
        return null;
    }

    @Override
    public List<SysUser> getAllSysUser() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SysUser.class);
        return criteria.list();
    }
}
