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
    private LRULinkedHashMap<String, String> map = new LRULinkedHashMap(500000);
    private static LocalOnLineUserStorage localOnLineUserStorage;

    public LocalOnLineUserStorage() {
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
    public void removeSocketRegisterUser(String uid) {
        if (null != getSocketRegisterUserByUserId(uid)) {
            localOnLineUserStorage.map.remove(uid);
        }
    }

    @Override
    public SocketRegisterUser saveOrUpdateSocketRegisterUser(SocketRegisterUser socketRegisterUser) {
        localOnLineUserStorage.map.put(socketRegisterUser.getUserId(), socketRegisterUser.getSessionId());
        return socketRegisterUser;
    }

    @Override
    public SocketRegisterUser getSocketRegisterUserByUserId(String uid) {
        if (null != localOnLineUserStorage.map.get(uid)) {
            SocketRegisterUser socketRegisterUser = new SocketRegisterUser();
            socketRegisterUser.setSessionId(localOnLineUserStorage.map.get(uid).toString());
            socketRegisterUser.setUserId(uid);
            return socketRegisterUser;
        }
        return null;
    }
}
