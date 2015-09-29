package com.noah.mapi.services.impl;

import com.noah.mapi.config.GlobalConfiguration;
import com.noah.mapi.model.SocketRegisterUser;
import com.noah.mapi.services.OnlineUserStorage;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by noahli on 15/9/22.
 */
public class RedisOnLineUserStorage implements OnlineUserStorage {
    private JedisPool pool = null;
    private Integer expire = Integer.valueOf('\ua8c0');
    private static RedisOnLineUserStorage redisOnLineUserStorage;

    public RedisOnLineUserStorage(JedisPool jedisPool) {
        this.pool = jedisPool;
    }

    public static void destroy() {
        redisOnLineUserStorage = null;
    }

    public Jedis take() {
        if (this.pool == null) {
            return null;
        } else {
            return this.pool.getResource();
        }
    }

    public void returnResource(Jedis redis) {
        this.pool.returnResourceObject(redis);
    }

    public static RedisOnLineUserStorage createRedisOnLineUserStorage() {
        try {
            JedisPool pool = null;
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(50);
            config.setMaxTotal(500);
            config.setMinIdle(10);
            config.setMaxWaitMillis(1000 * 100);
            config.setTestOnBorrow(true);

            pool = new JedisPool(config,
                    GlobalConfiguration.GLOBAL_CONFIG.getProperty("redis.ip"),
                    Integer.valueOf(GlobalConfiguration.GLOBAL_CONFIG.getProperty("redis.port")),
                    Integer.valueOf(GlobalConfiguration.GLOBAL_CONFIG.getProperty("redis.timeout")),
                    GlobalConfiguration.GLOBAL_CONFIG.getProperty("redis.password")
            );

            return new RedisOnLineUserStorage(pool);
        } catch (Exception e) {
            //处理redis启动失败事件，发送提醒邮件
            System.err.println("redis连接池创建失败");
        }
        return null;
    }

    public static synchronized RedisOnLineUserStorage getRedisOnLineUserStorage() {
        if (redisOnLineUserStorage == null) {
            redisOnLineUserStorage = createRedisOnLineUserStorage();
        }
        return redisOnLineUserStorage;
    }

    @Override
    public boolean isAlive() {
        try {
            SocketRegisterUser socketRegisterUser = new SocketRegisterUser();
            socketRegisterUser.setSessionId("4235r43534654654");
            socketRegisterUser.setUserId("test");
            redisOnLineUserStorage.saveOrUpdateSocketRegisterUser(socketRegisterUser);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void removeSocketRegisterUser(String uid) {
        Jedis take = this.take();
        SocketRegisterUser socketRegisterUser = getSocketRegisterUserByUserId(uid);
        if (null != socketRegisterUser) {
            take.del(socketRegisterUser.getUserId());
        }
    }

    @Override
    public SocketRegisterUser saveOrUpdateSocketRegisterUser(SocketRegisterUser socketRegisterUser) {
        Jedis take = this.take();
        take.set(socketRegisterUser.getUserId(), socketRegisterUser.getSessionId());
        take.expire(socketRegisterUser.getUserId(), expire);
        return socketRegisterUser;
    }

    @Override
    public SocketRegisterUser getSocketRegisterUserByUserId(String uid) {
        Jedis take = this.take();
        String sessionId = take.get(uid);
        if (null != sessionId) {
            SocketRegisterUser socketRegisterUser = new SocketRegisterUser();
            socketRegisterUser.setUserId(uid);
            socketRegisterUser.setSessionId(sessionId);
            return socketRegisterUser;
        }
        return null;
    }
}
