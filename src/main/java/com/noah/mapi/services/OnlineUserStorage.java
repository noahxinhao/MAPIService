package com.noah.mapi.services;


import com.noah.mapi.model.SocketRegisterUser;

import java.util.List;

/**
 * Created by noahli on 15/9/22.
 */
public interface OnlineUserStorage {
    boolean isAlive();

    void removeSocketRegisterUser(String uid);

    SocketRegisterUser saveOrUpdateSocketRegisterUser(SocketRegisterUser socketRegisterUser);

    SocketRegisterUser getSocketRegisterUserByUserId(String userId);

    List<SocketRegisterUser> getAllSocketRegisterUser();
}
