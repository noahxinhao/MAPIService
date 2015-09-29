package com.noah.mapi.task;

import com.noah.mapi.services.OnlineUserStorage;
import com.noah.mapi.services.OnlineUserStorageService;
import com.noah.mapi.services.impl.LocalOnLineUserStorage;
import com.noah.mapi.services.impl.RedisOnLineUserStorage;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by noahli on 15/9/28.
 */
@Component
public class CheckOutRedis {
    private static final Logger LOG = Logger.getLogger(CheckOutRedis.class);

    @Scheduled(cron = "0/15 * * * * ?")
    public void checkOut() {
        LOG.info("===========检查缓存=========");
        if (null != OnlineUserStorageService.onlineUserStorage) {
            //检查storage是否为redis
            if (!(OnlineUserStorageService.onlineUserStorage instanceof RedisOnLineUserStorage)) {
                OnlineUserStorage onlineUserStorage = RedisOnLineUserStorage.getRedisOnLineUserStorage();
                if (onlineUserStorage.isAlive()) {
                    //TODO将本地缓存中的记录复制到redis中
                    OnlineUserStorageService.onlineUserStorage = onlineUserStorage;
                    LOG.info("#切换到redis记录");
                }
            } else {
                LOG.info("#REDIS缓存正常");
            }
        } else {
            LOG.warn("#用户身份缓存失败，正在重启缓存");
            OnlineUserStorage onlineUserStorage = RedisOnLineUserStorage.getRedisOnLineUserStorage();
            if (onlineUserStorage.isAlive()) {
                LOG.info("#启用redis缓存");
                OnlineUserStorageService.onlineUserStorage = onlineUserStorage;
            } else {
                LOG.info("#启用本地缓存");
                OnlineUserStorageService.onlineUserStorage = LocalOnLineUserStorage.getLocalOnLineUserStorage();
            }
        }
        LOG.info("=========缓存检查完成========");
    }
}
