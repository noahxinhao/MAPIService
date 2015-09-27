package com.noah.mapi.services.impl;
import com.noah.mapi.model.SocketRegisterUser;
import com.noah.mapi.services.OnlineUserStorage;
import com.noah.mapi.services.OnlineUserStorageService;
import com.noah.mapi.util.LRULinkedHashMap;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by noahli on 15/9/22.
 */
public class LocalOnLineUserStorage implements OnlineUserStorage {
    private LRULinkedHashMap<String, AtomicLong> map = new LRULinkedHashMap(500000);
    private static LocalOnLineUserStorage localOnLineUserStorage;

    public LocalOnLineUserStorage() {
        Timer timer;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                //判断redis连接是否成功
                if (RedisOnLineUserStorage.getRedisOnLineUserStorage().isAlive()) {
                    OnlineUserStorageService.onlineUserStorage = RedisOnLineUserStorage.getRedisOnLineUserStorage();
                    System.out.println("获取redis连接成功，取消timer");
                    localOnLineUserStorage = null;
                    timer.cancel();
                } else {
                    System.err.println("获取redis限流器失败");
                }
            }
        }, 1000, 5 * 1000);
    }

    public static synchronized LocalOnLineUserStorage getLocalOnLineUserStorage() {
        if (localOnLineUserStorage == null) {
            localOnLineUserStorage = new LocalOnLineUserStorage();
        }
        return localOnLineUserStorage;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public SocketRegisterUser addSocketRegisterUser() {
        return null;
    }

    @Override
    public void removeSocketRegisterUser(String uid) {
        
    }

    @Override
    public SocketRegisterUser saveOrUpdateSocketRegisterUser(SocketRegisterUser socketRegisterUser) {
        return null;
    }

    @Override
    public SocketRegisterUser getSocketRegisterUserByUserId(String uid) {
        return null;
    }
}
