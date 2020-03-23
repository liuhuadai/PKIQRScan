package cn.com.service;

import com.google.gson.JsonObject;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class TokenService {
    static long timeOut = 1000*60*60*24;//即24小时
    static Key key = new SecretKeySpec("tC0bL0m$SDKE7tgp6@u$ZGcoYv0wN*O#BUZU*GoJmqVqXt#S&R".getBytes(), SignatureAlgorithm.HS512.getJcaName());
    public static String generateToken(String id ,String usage){
        Map<String,Object> header = new HashMap<String,Object>();
        header.put("usage",usage);
        JsonObject payLoad =new JsonObject();
        payLoad.addProperty("id",id);
        long currentTime = System.currentTimeMillis();
        payLoad.addProperty("timeOut",currentTime+timeOut);
        String token = Jwts.builder().setHeader(header).setPayload(payLoad.toString()).signWith(SignatureAlgorithm.HS512,key).compact();
        return token;
    }
    public static String parseToken(String token){
        Jws<Claims> jws =Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        if(jws == null){
            return null;
        }
        JwsHeader jwsHeader = jws.getHeader();
        String usage = String.valueOf(jwsHeader.get("usage"));
        Claims body = jws.getBody();
        long timeOut = body.get("timeOut",Long.class);
        long currentTime = System.currentTimeMillis();
        if(currentTime>timeOut){
            return null;
        }
        String id = body.get("id",String.class);
        return id;
    }
}
