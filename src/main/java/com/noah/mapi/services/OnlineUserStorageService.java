package com.noah.mapi.services;


import com.noah.mapi.services.impl.LocalOnLineUserStorage;
import com.noah.mapi.services.impl.RedisOnLineUserStorage;

/**
 * Created by noahli on 15/9/22.
 */
public class OnlineUserStorageService {
    public static OnlineUserStorage onlineUserStorage = null;

    static {
        onlineUserStorage = RedisOnLineUserStorage.getRedisOnLineUserStorage();
    }

    //检查当前storage是本地还是redis
    public static OnlineUserStorage getStorage() {
        //获取storage时检测是否存活
        if (!onlineUserStorage.isAlive()) {
            onlineUserStorage = LocalOnLineUserStorage.getLocalOnLineUserStorage();
        }
        return onlineUserStorage;
    }
}
