package cn.com.websocket.signIn;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import cn.com.login.token.TokenProccessor;
import cn.com.service.TokenService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import cn.com.websocket.bean.WebSocketClient;
@Component
public class SignInHandler extends TextWebSocketHandler{
    long currentTime ;
    private long timeOut = 1000*60*3;
	private static Map<String,WebSocketClient> socketClients = new HashMap<String,WebSocketClient>();
//	//concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketSession对象
	//private static Map<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();

	//private static final ArrayList<WebSocketSession> users = null;//这个会出现性能问题，最好用Map来存储，key用userid
	//向特定用户发送信息
	public boolean sendMessageToUser(String code, TextMessage message) {
	    /*
	    此部分功能1.发送信息给客户端
	    2.判断是否超时
	    3.传token
	     */
	    Set<String> sessionIDs = socketClients.keySet();
	    long time = System.currentTimeMillis();
		Iterator<String> i = sessionIDs.iterator();
		while(i.hasNext()) {
			String sessionID = i.next();
			WebSocketClient client = socketClients.get(sessionID);
			WebSocketSession clientSession = client.getSocketSession();
			if(client.getCode().equals(code)&&(time-currentTime<=timeOut)){//判断随机数相同
			    String token = TokenService.generateToken(code,"web");
                try {
                    clientSession.sendMessage(new TextMessage("token:"+token));
                    clientSession.sendMessage(message);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
		return false;
	}


    @Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		//接收到客户端消息时调用
		//获取发送消息的昵称和ip
        long t1 = System.currentTimeMillis();
        System.out.println(t1);
        WebSocketClient sendClient = socketClients.get(session.getId());
        WebSocketSession clientSession = sendClient.getSocketSession();
        Map<String,Object> map = clientSession.getAttributes();
        String code = (String)map.get("code");
        //这里应该有一个根据code查询数据库是否存在的操作，如果成功则返回成功信息，这里先假设成功
        System.out.println("code"+code+"token"+code);
		String token = TokenProccessor.getInstance().makeToken();
        clientSession.sendMessage(new TextMessage("status:"+200+"\n"+"message:"+"success"+"data"+token));
//        while(System.currentTimeMillis()-t1<=3000){
//            if(true){//当收到安卓扫码信息
//                clientSession.sendMessage(new TextMessage("status:200"+"\n"+"message:success"+"\n"+"data:"+code));
//            }
//        }
//        clientSession.close();
		String clientIp = sendClient.getClientIp();
		sendClient.setCode(code);
		System.out.println("handleTextMessage: " + session.getId() + "-" + code + "-" + message.getPayload());

//		//向所有客户端群发收到的消息
//		Set<String> sessionIDs = socketClients.keySet();
//		Iterator<String> i = sessionIDs.iterator();
//		while(i.hasNext()) {
//			String sessionID = i.next();
//			WebSocketClient client = socketClients.get(sessionID);
//			WebSocketSession clientSession = client.getSocketSession();
//			try {
//				clientSession.sendMessage(new TextMessage((code + "(" + clientIp + ")\n" + message.getPayload()).getBytes("UTF-8")));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		// 与客户端完成连接后调用
		//获取连接的唯一session id
        currentTime = System.currentTimeMillis();
		String sessionID = session.getId();
		Map<String,Object> map = session.getAttributes();
		String code = (String)map.get("code");
//		String path = URLDecoder.decode(session.getUri().getPath(),"UTF-8");
//		String[] paths = path.split("\\/");
//		for (int i=0; i<paths.length; ++i) {
//			if (paths[i].equalsIgnoreCase("code")) {
//				if (i < paths.length-1)
//					code = paths[i+1];
//				break;
//			}
//		}

		System.out.println("afterConnectionEstablished: " + sessionID + "-" + code);

		//获取客户端ip地址
		String clientIp = session.getRemoteAddress().getAddress().getHostAddress();
		//将已连接的socket客户端保存
		WebSocketClient client = new WebSocketClient();
		client.setClientIp(clientIp);
		client.setCode(code);
		client.setSocketSession(session);
		//socketClients.put(code,client);
		//保存已连接的客户端信息
		socketClients.put(sessionID, client);
		//ssID.add(sessionID);
	}

	@Override
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		// 消息传输出错时调用
		System.out.println("handleTransportError");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		// 一个客户端连接断开时关闭
		System.out.println("afterConnectionClosed");

		//从保存的客户端集合中删除关闭的客户端
		String sessionID = session.getId();
		socketClients.remove(sessionID);
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}
}
