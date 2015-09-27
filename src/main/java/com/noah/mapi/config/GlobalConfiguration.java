package com.noah.mapi.config;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by noahli on 15/9/27.
 */
public class GlobalConfiguration {
    public static Charset charset = StandardCharsets.UTF_8;
    public static ApnsService apnsService;
    public static String LOCAL_IP;
    public static Integer SOCKET_PORT = 9098;

    static {
        startAPNService();
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        LOCAL_IP = addr.getHostAddress().toString();//获得本机IP
    }

    //初始化APN推送服务
    public static void startAPNService() {
        apnsService = APNS.newService()
                .withCert("/Users/noahli/workspace/NotificationServices/src/main/resources/证书.p12", "itlxh784533")
                .withSandboxDestination()
                .build();
    }
}
