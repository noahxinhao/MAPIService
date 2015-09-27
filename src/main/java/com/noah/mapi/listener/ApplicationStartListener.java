package com.noah.mapi.listener;

import com.noah.mapi.services.SocketIOListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by noahli on 15/9/24.
 */
//监听spring容器启动完成之后执行初始化Socket Server初始化操作
@Component("ApplicationStartedListener")
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {
    //只有Spring容器初始化完成之后才能注入服务
    @Autowired
    SocketIOListenerService socketIOListenerService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (null == contextRefreshedEvent.getApplicationContext().getParent()) {
            socketIOListenerService.start();
        }
    }
}
