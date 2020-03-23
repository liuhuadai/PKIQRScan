package cn.com.login.token;

import javax.servlet.http.HttpServletRequest;


/**
 * Token的工具类
 *
 */
public class TokenTools {


    public void createToken(String tokenServerkey){
        String token = TokenProccessor.getInstance().makeToken();
        System.out.println(token);
        //request.getSession().setAttribute(tokenServerkey, token);
    }
    public static void main(String[]args){
        TokenTools tt = new TokenTools();
        tt.createToken("asdhjadhjadhja");
    }


}