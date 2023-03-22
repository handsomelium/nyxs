/**
 * <p>Title: WebSocketServer.java<／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) AKE 2019<／p>
 * <p>Company: AKE<／p>
 * @author GuoJM
 * @date 2019年1月18日
 * @version 1.0
 */
package com.liu.nyxs.webSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocket服务端
 * 
 * @author GuoJM
 */

@ServerEndpoint(value = "/websocket/{channel}")
@Component
public class WebSocketServer {

	private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

	private static final AtomicInteger onlineCount = new AtomicInteger(0);
	
	private static final Map<String, AtomicInteger> channelOnlines = new ConcurrentHashMap<String, AtomicInteger>();
	private static final Map<String, Set<Session>> channels = new ConcurrentHashMap<>();
	// concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
	//private static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();

	/**
	 *	 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(@PathParam("channel") String channelName, Session session) {
		// 将session按照频道来存储，将各个频道的用户隔离
		if(!channels.containsKey(channelName)) {
			//频道不存在的话，创建频道
			Set<Session> channel = new CopyOnWriteArraySet<>();
			channel.add(session);
			channels.put(channelName, channel);
			channelOnlines.put(channelName, new AtomicInteger(1));
		} else {
			channels.get(channelName).add(session);
			channelOnlines.get(channelName).incrementAndGet();
		}
		
		//SessionSet.add(session);
		int cnt = onlineCount.incrementAndGet(); // 在线数加1
		log.info("频道：“"+ channelName +"”有连接加入，当前总连接数为：{}", cnt);
		sendMessage(session, "恭喜您连接成功");
	}

	/**
	 *	 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(@PathParam("channel") String channelName, Session session) {
		channels.get(channelName).remove(session);
		channelOnlines.get(channelName).decrementAndGet();
		//SessionSet.remove(session);
		int cnt = onlineCount.decrementAndGet();
		log.info("频道：“"+ channelName +"”有连接关闭，当前总连接数为：{}", cnt);
	}

	/**
	 * 	收到客户端消息后调用的方法
	 * 
	 * @param message 客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(@PathParam("channel") String channelName, String message, Session session) {
		log.info("频道：“"+ channelName +"”来自客户端的消息：{}", session.getId()+":"+message);
		sendMessage(session, "收到消息，消息内容：" + message);
	}

	/**
	 * 	出现错误
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(@PathParam("channel") String channelName, Session session, Throwable error) {
		log.error("频道：“"+ channelName +"”发生错误：{}，Session ID： {}", error.getMessage(), session.getId());
		error.printStackTrace();
	}

	/**
	 * 	发送消息，实践表明，每次浏览器刷新，session会发生变化。
	 * 
	 * @param session
	 * @param message
	 */
	public static void sendMessage(Session session, String message) {
//		session.getAsyncRemote()
//				.sendText(String.format("%s (From Server，Session ID=%s)", message, session.getId()));
//		session.getAsyncRemote().sendText(JSONObject.toJSONString(JSONResult.ok(String.format("%s (From Server，Session ID=%s)", message, session.getId()))));
		session.getAsyncRemote().sendText(message);
	}
	
	/**
	 *	 指定Session发送消息
	 * 
	 * @param sessionId
	 * @param message
	 * @throws IOException
	 */
	public static void sendMessage(String channelName, String sessionId, String message) throws IOException {
		Session session = null;
		
		for (Session s : channels.get(channelName)) {
			if (s.getId().equals(sessionId)) {
				session = s;
				break;
			}
		}
		if (session != null) {
			sendMessage(session, message);
		} else {
			log.warn("频道：“"+ channelName +"”没有找到你指定ID的会话：{}", sessionId);
		}
	}

	/**
	 * 	频道内群发消息
	 * 
	 * @param message
	 * @throws IOException
	 */
	public static void broadCastInfo(String channelName, String message) throws IOException {
		if(!channels.isEmpty()) {
			channels.get(channelName).forEach(s -> {
				if(s.isOpen()) {
					sendMessage(s, message);
				}
			});
		}
	}
	
	/**
	 * 	群发消息
	 * 
	 * @param message
	 * @throws IOException
	 */
	public static void broadCastInfoAll(String message) throws IOException {
		if(!channels.isEmpty()) {
			channels.values().forEach(sessions -> sessions.forEach(s -> {
				if(s.isOpen()) {
					sendMessage(s, message);
				}
			}));
		}
	}

}
