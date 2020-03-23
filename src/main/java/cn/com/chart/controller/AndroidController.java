package cn.com.chart.controller;

import cn.com.encrypt.GetPubKey;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class AndroidController {
    //返回服务器公钥
    @RequestMapping(value = "/key",method = RequestMethod.GET)
    @ResponseBody
    public String returnPubKey(){
        GetPubKey getPubKey = new GetPubKey();
        String publicKey = getPubKey.getPublicKey();
        System.out.println("publicKey=="+publicKey);
        return publicKey;
    }


}
