package com.noah.mapi.restful;

import com.noah.mapi.exception.ServiceException;
import com.noah.mapi.model.SysUser;
import com.noah.mapi.services.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/**
 * Created by noahli on 15/9/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:/Spring-db.xml")
public class SysUserCtrlTest {

    @Resource
    SysUserService sysUserServiceImpl;

    @Test
    public void userTest() throws ServiceException {
        SysUser user = new SysUser();
        user.setAccount("18721988563");
        user.setPassword("itlxh784533");
        sysUserServiceImpl.addSysUser(user);
    }
}
