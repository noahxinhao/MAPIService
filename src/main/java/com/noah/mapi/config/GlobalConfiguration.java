package com.noah.mapi.config;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Created by noahli on 15/9/27.
 */
public class GlobalConfiguration {
    private static final Logger LOGGER = Logger.getLogger(GlobalConfiguration.class.getName());
    public static final Properties GLOBAL_CONFIG = new Properties();
    public static Charset charset = StandardCharsets.UTF_8;
    public static ApnsService apnsService;
    public static String LOCAL_IP;
    public static Integer SOCKET_PORT = 9098;

    static {

        try {
            loadGlobalConfigProperties();
        } catch (IOException e) {
            LOGGER.info("#读取配置文件错误");
            System.exit(1);
        }
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

//        LOCAL_IP = addr.getHostAddress().toString();//获得本机IP
        LOCAL_IP = "121.42.172.20";//获得本机IP

        //startAPNService();
    }

    //初始化APN推送服务
    public static void startAPNService() {
        apnsService = APNS.newService()
                .withCert(GlobalConfiguration.GLOBAL_CONFIG.getProperty("p12.path") + "ADelivery.p12", "itlxh784533")
                .withSandboxDestination()
                .build();
    }

    //初始化配置文件
    public static void loadGlobalConfigProperties() throws IOException {
        try (InputStream resourceAsStream = GlobalConfiguration.class.getResourceAsStream("/configuration.properties")) {
            GLOBAL_CONFIG.load(resourceAsStream);
        } catch (Exception e) {
            LOGGER.error("初始化配置文件出错", e);
        }
    }
}
