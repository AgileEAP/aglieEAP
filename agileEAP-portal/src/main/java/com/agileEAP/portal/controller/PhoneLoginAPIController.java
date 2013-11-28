package com.agileEAP.portal.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agileEAP.security.entity.Operator;
import com.agileEAP.security.service.AccountService;
import com.agileEAP.utils.Digests;
import com.agileEAP.utils.Encodes;
import com.agileEAP.utils.JsonConvert;

@Controller
@RequestMapping(value = "/phone")
public class PhoneLoginAPIController {
	public static final int HASH_INTERATIONS = 1024;
	
	@Autowired
	private AccountService accountService;
	
	//根据用户名，密码，进行登录，返回该用户信息
	@RequestMapping(value = "api/login",method = RequestMethod.POST)
	@ResponseBody
	public Operator phoneLogin(Operator operator,HttpServletRequest request) throws IOException{
		InputStream is = request.getInputStream();
    	byte[] bytes = new byte[request.getContentLength()];
    	is.read(bytes);
    	String json = new String(bytes, request.getCharacterEncoding());
    	JsonConvert jsonConvert = new JsonConvert();
    	operator=jsonConvert.fromJson(json,Operator.class);
    	Operator operators = null;
		operators = accountService.getByLoginName(operator.getLoginName());
		if(operators==null){
			return null;
		}else{
			byte[] salt = Encodes.decodeHex(operators.getSalt());
			byte[] hashPassword = Digests.sha1(operator.getPassword().getBytes(), salt, HASH_INTERATIONS);
			if(operators.getPassword().equals(Encodes.encodeHex(hashPassword))){
				return operators;
			}else{
				return null;
			}
		}
	}
}
