package com.noah.mapi.restful;

import com.noah.mapi.dao.MessageDao;
import com.noah.mapi.dao.RelationshipDao;
import com.noah.mapi.dao.SysUserDao;
import com.noah.mapi.model.EventEnum;
import com.noah.mapi.model.mongo.Friend;
import com.noah.mapi.model.mongo.Message;
import com.noah.mapi.model.mongo.Relationship;
import com.noah.mapi.services.SocketIOListenerService;
import com.noah.mapi.util.JsonResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by noahli on 15/10/9.
 */
@RequestMapping(value = "/message")
public class MessageCtrl {
    @Resource
    SocketIOListenerService socketIOListenerService;

    @Autowired
    MessageDao messageDaoImpl;

    @Autowired
    RelationshipDao relationshipDaoImpl;

    @Autowired
    SysUserDao sysUserDaoImpl;

    /**
     * @param userId
     * @return
     */
    @RequestMapping(value = "/apply/{userId}", method = RequestMethod.GET, produces = "application/json")
    public JsonResponse getApply(@RequestParam String userId) {
        return null;
    }

    /**
     * @param message
     * @return
     */
    @RequestMapping(value = "/apply", method = RequestMethod.POST, produces = "application/json")
    public JsonResponse apply(@RequestBody Message message) {
        if (!message.getType().name().equals(EventEnum.EVENT.friendApply)) {
            return JsonResponse.createError("消息类型错误");
        }
        try {
            socketIOListenerService.postMessage(message.getToUserId(), EventEnum.EVENT.friendApply.name(), message);
            message.setStatus(Message.STATUS.已发送);
            messageDaoImpl.addMessage(message);

            //更新各自的friendShip
            Relationship userRelationShip = relationshipDaoImpl.getRelationShip(message.getFromUserId());
            Relationship toUserRelationShip = relationshipDaoImpl.getRelationShip(message.getToUserId());

            List<Friend> userFriends = userRelationShip.getFriends();
            List<Friend> toUserFriends = toUserRelationShip.getFriends();

            Friend userfriend = new Friend();
            Friend toUserfriend = new Friend();

            userfriend.setUserId(message.getFromUserId());
            userfriend.setStatus(Friend.Status.待验证);
            userfriend.setName(sysUserDaoImpl.getSysUserByAccount(message.getToUserId()).getNickName());
            userFriends.add(userfriend);

            toUserfriend.setUserId(message.getFromUserId());
            toUserfriend.setStatus(Friend.Status.待验证);
            toUserfriend.setName(sysUserDaoImpl.getSysUserByAccount(message.getFromUserId()).getNickName());
            toUserFriends.add(toUserfriend);

            relationshipDaoImpl.updateRelationship(userRelationShip);
            relationshipDaoImpl.updateRelationship(toUserRelationShip);

        } catch (Exception e) {
            return JsonResponse.createError("消息发送失败");
        }

        return new JsonResponse(message);
    }

    @RequestMapping(value = "/friend/status", method = RequestMethod.POST, produces = "application/json")
    public JsonResponse applyRep(@RequestBody Map<String, String> rep) {
        String userId = rep.get("userId");
        String friendId = rep.get("friendId");
        String status = rep.get("status");

        if (null == Friend.Status.valueOf(status)) {
            return JsonResponse.createError("status参数错误");
        }

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(friendId)) {
            return JsonResponse.createError("参数错误");
        }

        //增加friendShip
        Relationship userRelationShip = relationshipDaoImpl.getRelationShip(userId);
        Relationship toUserRelationShip = relationshipDaoImpl.getRelationShip(friendId);

        List<Friend> userFriends = userRelationShip.getFriends();
        List<Friend> toUserFriends = toUserRelationShip.getFriends();

        for (Friend friend : userFriends) {
            if (friend.getUserId().equals(friendId)) {
                friend.setStatus(Friend.Status.valueOf(status));
            }
        }

        for (Friend friend : toUserFriends) {
            if (friend.getUserId().equals(friendId)) {
                friend.setStatus(Friend.Status.valueOf(status));
            }
        }

        relationshipDaoImpl.updateRelationship(userRelationShip);
        relationshipDaoImpl.updateRelationship(toUserRelationShip);

        return new JsonResponse("更新成功");
    }
}
