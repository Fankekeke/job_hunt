package cc.mrbird.febs.common.utils;

import cc.mrbird.febs.cos.entity.ChatInfo;
import cc.mrbird.febs.cos.entity.NotifyInfo;
import cc.mrbird.febs.cos.entity.vo.NotifyVo;
import cc.mrbird.febs.cos.service.IChatInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{key}")
public class WebSocketUtil {

    @Autowired
    private IChatInfoService chatInfoService;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private static CopyOnWriteArraySet<WebSocketUtil> webSockets =new CopyOnWriteArraySet<>();
    //用来存放每个客户端对应的WebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static Map<String,Session> sessionPool = new HashMap<String,Session>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value="key")String key) {
        this.session = session;
        webSockets.add(this);
        sessionPool.put(key, session);
        System.out.println("【websocket消息】有新的连接，总数为:"+webSockets.size());
        sendAllMessage("欢迎您，客户端");
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        System.out.println("【websocket消息】连接断开，总数为:"+webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        if (StrUtil.isEmpty(message)) {
            return;
        }
        NotifyVo notifyInfoVo = JSONUtil.toBean(message, NotifyVo.class);
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setEnterpriseName(notifyInfoVo.getEnterpriseName());
        chatInfo.setExpertName(notifyInfoVo.getExpertName());
        chatInfo.setContent(notifyInfoVo.getContent());
        chatInfo.setEnterpriseCode(notifyInfoVo.getEnterpriseCode());
        chatInfo.setExpertCode(notifyInfoVo.getExpertCode());
        chatInfo.setType(notifyInfoVo.getFlag());
        chatInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        chatInfoService.save(chatInfo);
        if (chatInfo.getType() == 1) {
            this.sendTextMessage(JSONUtil.toJsonStr(chatInfo), chatInfo.getEnterpriseCode());
        } else {
            this.sendTextMessage(JSONUtil.toJsonStr(chatInfo), chatInfo.getExpertCode());
        }

        System.out.println("【websocket消息】收到客户端消息:"+message);
    }

    // 此为广播消息
    public void sendAllMessage(String message) {
        for(WebSocketUtil webSocket : webSockets) {
            System.out.println("【websocket消息】广播消息:"+message);
            try {
                webSocket.session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // 此为单点消息 (发送文本)
    public void sendTextMessage(String key, String message) {
        Session session = sessionPool.get(key);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
