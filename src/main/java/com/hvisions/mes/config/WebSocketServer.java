package com.hvisions.mes.config;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author dpeng
 * @description
 * @date 2019-07-19 15:38
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{sessionId}")
public class WebSocketServer {

  // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
  private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
  // 与某个客户端的连接会话，需要通过它来给客户端发送数据
  private Session session;
  private String sessionId = "";
  private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

  /** 连接建立成功调用的方法 */
  @OnOpen
  public void onOpen(Session session, @PathParam("sessionId") String sessionId) {
    this.session = session;
    this.sessionId = sessionId;
    webSocketSet.add(this); // 加入set中
    ONLINE_COUNT.incrementAndGet();
    try {
      sendMessage("连接成功");
    } catch (IOException e) {
      log.error("websocket IO异常");
    }
  }

  /** 连接关闭调用的方法 */
  @OnClose
  public void onClose() {
    webSocketSet.remove(this);
    ONLINE_COUNT.decrementAndGet();
  }

  /** 收到客户端消息后调用的方法 */
  @OnMessage
  public void onMessage(String message, Session session) {
    log.info("收到来自" + sessionId + "的信息:" + message);
    // 群发消息
    for (WebSocketServer item : webSocketSet) {
      try {
        item.sendMessage(message);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @OnError
  public void onError(Session session, Throwable error) {
    log.error("发生错误", error);
  }

  /** 实现服务器主动推送 */
  public void sendMessage(String message) throws IOException {
    this.session.getBasicRemote().sendText(message);
  }

  /** 群发自定义消息 */
  public static void sendInfo(@PathParam("sessionId") String sessionId, String message) {
    log.info("推送消息到窗口" + sessionId + "，推送内容:" + message);
    for (WebSocketServer item : webSocketSet) {
      try {
        // 这里可以设定只推送给这个sessionId的，为null则全部推送
        if (sessionId == null) {
          item.sendMessage(message);
        } else if (item.sessionId.equals(sessionId)) {
          item.sendMessage(message);
        }
        item.sendMessage("当前在线人数：" + ONLINE_COUNT);
      } catch (IOException e) {
        continue;
      }
    }
  }
}
