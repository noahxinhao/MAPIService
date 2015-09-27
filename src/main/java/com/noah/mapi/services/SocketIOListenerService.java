package com.noah.mapi.services;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.noah.mapi.config.GlobalConfiguration;
import com.noah.mapi.dao.MessageDao;
import com.noah.mapi.model.EventEnum;
import com.noah.mapi.model.SocketRegisterUser;
import com.noah.mapi.model.mongo.Message;
import com.noah.mapi.model.mongo.MessageRecived;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

/**
 * Created by noahli on 15/9/23.
 */
@Service
public class SocketIOListenerService {
    public SocketIOServer server;
    private static final Logger LOG = Logger.getLogger(SocketIOListenerService.class);
    @Autowired
    MessageDao messageDaoImpl;

    public void start() {
        Configuration config = new Configuration();
        config.setHostname(GlobalConfiguration.LOCAL_IP);
        config.setPort(GlobalConfiguration.SOCKET_PORT);

        LOG.info("===========SOCKET服务启动==========");
        LOG.info("#启动IP:" + GlobalConfiguration.LOCAL_IP);
        LOG.info("#启动端口:" + GlobalConfiguration.SOCKET_PORT);
        server = new SocketIOServer(config);
        LOG.info("===========SOCKET服务完成==========");

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                System.out.println(client.getSessionId());
                //注册用户信息
            }
        });

        //监听用户身份注册，身份信息将更新到redis或者本地缓存中存储，检查当前用户是否有未发送的消息，如果有，就发送出去
        server.addEventListener(EventEnum.EVENT.socketRegister.name(), SocketRegisterUser.class, new DataListener<SocketRegisterUser>() {
            @Override
            public void onData(SocketIOClient client, SocketRegisterUser socketRegister, AckRequest ackRequest) {
                socketRegister.setSessionId(client.getSessionId().toString());
                //更新用户信息到redis或者本地缓存中
                OnlineUserStorage onlineUserStorage = OnlineUserStorageService.getStorage();

                onlineUserStorage.saveOrUpdateSocketRegisterUser(socketRegister);

                //检查缓存中是否有消息未接收
                List<Message> messageList = messageDaoImpl.getMessageBy(socketRegister.getUserId());
                if (null != messageList) {
                    for (Message message : messageList) {
                        postMessage(client, EventEnum.EVENT.sendMessage.name(), message);
                        message.setStatus(Message.STATUS.已发送);
                        messageDaoImpl.updateMessage(message);
                    }
                }
            }
        });

        //监听用户发送消息
        server.addEventListener(EventEnum.EVENT.sendMessage.name(), Message.class, new DataListener<Message>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Message message, AckRequest ackRequest) throws Exception {
                OnlineUserStorage onlineUserStorage = OnlineUserStorageService.getStorage();
                SocketRegisterUser socketRegisterUser = onlineUserStorage.getSocketRegisterUserByUserId(message.getToUserId());
                if (null != socketRegisterUser) {
                    SocketIOClient client = server.getClient(UUID.fromString(socketRegisterUser.getSessionId()));
                    if (null != client) {
                        postMessage(client, EventEnum.EVENT.sendMessage.name(), message);
                        message.setStatus(Message.STATUS.已发送);
                    } else {
                        //将节点删除
                        onlineUserStorage.removeSocketRegisterUser(message.getToUserId());
                        //设置消息为未发送
                        message.setStatus(Message.STATUS.未发送);
                    }
                } else {
                    message.setStatus(Message.STATUS.未发送);
                }
                message.setType(Message.TYPE.聊天消息);
                //将消息保存
                messageDaoImpl.addMessage(message);
            }
        });

        //监听收到消息回执信息
        server.addEventListener(EventEnum.EVENT.messageRecived.name(), MessageRecived.class, new DataListener<MessageRecived>() {
            @Override
            public void onData(SocketIOClient socketIOClient, MessageRecived messageRecived, AckRequest ackRequest) throws Exception {

            }
        });

        server.start();
    }

    public void postMessage(SocketIOClient client, String event, Message message) {
        client.sendEvent(event, message);
    }
}
