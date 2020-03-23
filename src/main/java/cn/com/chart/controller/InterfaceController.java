package cn.com.chart.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.entity.ConFile;
import cn.com.entity.Contract;
import cn.com.entity.PhoneData;
import cn.com.login.token.TokenProccessor;
import cn.com.service.TokenService;
import cn.com.websocket.signIn.SignInHandler;
import jdk.nashorn.internal.parser.Token;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class InterfaceController {
	@RequestMapping(value ="/chart",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView addarticle(HttpServletRequest req, HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("chart/index");

		return mv;
	}
    @RequestMapping("/auth/code")
    @ResponseBody
    public Map getAuthorizationCode(){
	    String code = "";
        try {
            SecureRandom random1 = SecureRandom.getInstance("SHA1PRNG");
            code = random1.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //返回
        Map<String,Object> map = new HashMap();
        map.put("status",200);
        map.put("message","success");
        map.put("data",code);
        return map;
    }
    @RequestMapping("/auth")
    @ResponseBody
    public Map getAuthrizationStatus(@RequestParam("code") String code){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("status",200);
        map.put("message","success");
        map.put("data",code);
        return map;
    }

    @RequestMapping("/contracts/{contractId}")
    @ResponseBody
    public Map getContractById(@PathVariable("contractId") String constractId){
	    Map<String,Object> map = new HashMap<String, Object>();
	    //这里调用数据库取合同id对应的内容,select * from table where id = ?

        map.put("status",200);
        map.put("message","success");
        Contract contract=new Contract();
        contract.setId(constractId);
        contract.setTitle("二手房买卖合同2018");
        contract.setType(0);
        ConFile file =new ConFile();
        file.setFilename("test.pdf");
        file.setLink("http://downloadfile.orz/test.pdf");
        file.setSize(128);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        file.setLastModified(timestamp);
        contract.setFile(file);
        map.put("data",contract);
	    return map;
    }

    @RequestMapping(value = "/contracts/{contractId}/create",method = RequestMethod.POST)
    @ResponseBody
    public Map createContract(@PathVariable("contractId") String contractId,@RequestParam("file")File file
    ,@RequestParam("title")String title,@RequestParam("partBName")String partBName,@RequestParam("partBIDCard")String partBIDCard){
	    Map<String,Object> map = new HashMap<String, Object>();
	    map.put("id",contractId);
	    map.put("title",title);
	    map.put("partBName",partBName);
	    map.put("file",file);
	    map.put("partBIDCard",partBIDCard);
	    return map;
    }
    @RequestMapping(value = "/contracts/{contractId}/update",method = RequestMethod.POST)
    public void modifyContract(@PathVariable("contractId")String contractId,@RequestParam("file")File file){
        //更新数据库
    }
    @RequestMapping(value = "/contracts/{contractId}/accept",method = RequestMethod.POST)
    public int agreeContract(@PathVariable("contractId")String contractId,@RequestParam("file")File file){
        //更新数据库
        int type = 0;
        return type;
    }
    @RequestMapping(value = "/contracts/{contractId}/decline",method = RequestMethod.POST)
    public void declinetContract(@PathVariable("contractId")String contractId,@RequestParam("file")File file){
        //更新数据库,表明用户不同意合同内容，此时服务器直接删除合同相关数据
    }
    @RequestMapping(value = "/contracts/{contractId}/signCode",method = RequestMethod.POST)
    @ResponseBody
    public Map signCodeContract(@PathVariable("contractId")String contractId,@RequestParam("file")File file){
        //调用数据库
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("status",200);
        map.put("message","success");
        map.put("data","sda");//授权码
        return map;
    }
    @RequestMapping(value = "logs",method = RequestMethod.GET)
    @ResponseBody
    public Map getAllLogs(){
	    Map<String,Object> map = new HashMap<String, Object>();
	    map.put("status",200);
	    map.put("message","success");
        PhoneData pd = new PhoneData();
        pd.setDeviceId("SDFSDXV-345");
        pd.setDeviceModel("iPhoneX");
        pd.setDeviceType("mobile");
        java.sql.Timestamp time = new java.sql.Timestamp(System.currentTimeMillis());
        pd.setTime(time);
	    map.put("data",pd);
	    return map;
    }
    @Bean
    public SignInHandler infoHandler(){
	    return new SignInHandler();
    }
    ///send?code=xxx&&message=xxxx此路径用于长连接时发送消息
    @RequestMapping(value = "/send",method = RequestMethod.GET)
    @ResponseBody
    public boolean send(HttpServletRequest request) {
	    String code = request.getParameter("code");
	    String message = request.getParameter("message");
	    boolean res = infoHandler().sendMessageToUser(code,new TextMessage(message));
        return res;
    }
    @RequestMapping(value = "swipeData",method = RequestMethod.GET)
    @ResponseBody
    public Map makeData(){
	    Map<String,Object> map = new HashMap<String,Object>();
	    map.put("status",200);
	    return map;
    }

}