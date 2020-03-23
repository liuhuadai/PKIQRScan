package cn.com.websocket.bean;

import org.springframework.web.socket.WebSocketSession;
public class WebSocketClient {
	private WebSocketSession socketSession;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	private String code;
	private String clientIp;
	
	public WebSocketSession getSocketSession() {
		return socketSession;
	}
	
	public void setSocketSession(WebSocketSession socketSession) {
		this.socketSession = socketSession;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}


}
