package com.noah.mapi.listener;

import com.noah.mapi.config.GlobalConfiguration;
import com.noah.mapi.services.SocketIOListenerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by noahli on 15/9/17.
 */
public class initListener implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger(initListener.class);

//    @Autowired
//    SocketIOListenerService socketIOListenerService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
//            GlobalConfiguration.init();
        } catch (Exception e) {
            System.exit(1);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOG.info("#TOMCAT关闭，执行关闭APNS,socket服务");
        try {
            GlobalConfiguration.apnsService.stop();
//            socketIOListenerService.server.stop();
            LOG.info("#TOMCAT关闭，执行关闭APNS,socket服务关闭完成");
        } catch (Exception e) {
            LOG.info("#服务关闭失败");
        } finally {
            System.exit(1);
        }
    }
}
