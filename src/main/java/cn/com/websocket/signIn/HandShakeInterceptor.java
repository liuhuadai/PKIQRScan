package cn.com.websocket.signIn;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;

public class HandShakeInterceptor extends HttpSessionHandshakeInterceptor {
	@Override  
    public boolean beforeHandshake(ServerHttpRequest request,  
            ServerHttpResponse response, WebSocketHandler wsHandler,  
            Map<String, Object> attributes) throws Exception {
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String id = servletRequest.getSession().getId();
        System.out.println("beforeHandshake: \n"+id);
        String code = servletRequest.getParameter("code");
        attributes.put("code",code);
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }  
  
    @Override  
    public void afterHandshake(ServerHttpRequest request,  
            ServerHttpResponse response, WebSocketHandler wsHandler,  
            Exception ex) {  
        System.out.println("After Handshake");
        super.afterHandshake(request, response, wsHandler, ex);  
    }  
}