package com.agileEAP.infrastructure.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.shiro.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agileEAP.data.PageDataResult;
import com.agileEAP.data.RequestPageData;
import com.agileEAP.infrastructure.entity.SysParam;
import com.agileEAP.infrastructure.service.ActionLogService;
import com.agileEAP.infrastructure.service.SysParamService;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;
import com.agileEAP.utils.*;

/**
* 系统参数Controller
* restful风格的urls
* list page : GET /infrastructure/sysparam/
* add page : GET /infrastructure/sysparam/add
* add action : POST /infrastructure/sysparam/add
* update page : GET /infrastructure/sysparam/update/{id}
* update action : POST /infrastructure/sysparam/update
* delete action : POST /infrastructure/sysparam/delete
* 
* @author trh
*/
@Controller
@RequestMapping(value = "/infrastructure/sysparam")
public class  SysParamController {
    private static Logger logger = LoggerFactory.getLogger(SysParamController.class);

    @Autowired
    private ActionLogService actionLogService;

    @Autowired
    private SysParamService  sysParamService;

    @RequestMapping(value = "")
    public String list() {
    	return "infrastructure/sysparam/sysParamList";
    }

    @RequestMapping(value = "ajaxList")
    @ResponseBody
    public PageDataResult ajaxDataResult(HttpServletRequest request)throws IOException {
    	InputStream is = request.getInputStream();
    	byte[] bytes = new byte[request.getContentLength()];
    	is.read(bytes);
    	String json = new String(bytes, request.getCharacterEncoding());

    	JsonConvert jsonConvert = new JsonConvert();
    	RequestPageData requestData = jsonConvert.fromJson(json,RequestPageData.class);

    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("page", requestData.getPage());
    	searchParams.put("pageSize", requestData.getPageSize());
    	searchParams.putAll(requestData.getData());
    	PageDataResult pageDataResult = sysParamService.searchByPage(searchParams);

    	return pageDataResult;
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String createForm(Model model) {
    	model.addAttribute("sysParam", new SysParam ());
    	model.addAttribute("action", "add");
    	return "infrastructure/sysparam/sysParamDetail";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String create(@Valid @ModelAttribute("sysParam") SysParam sysParam) {
    	String message = "新增系统参数成功";
    	try {
    		sysParam.setId(UUID.randomUUID().toString());
    		sysParamService.save(sysParam);
    		JsonConvert jsonConvert = new JsonConvert();
    		actionLogService.AddActionLog("新增", message,jsonConvert.toJson(sysParam));
    	} catch (Exception ex) {
    		message = "新增系统参数出错，请检查输入是否正确！";
    		logger.error(String.format("新增系统参数id={0}出错，请检查输入是否正确", sysParam.getId()),ex);
    	}

    	return message;
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") String id, Model model) {
    	model.addAttribute("sysParam", sysParamService.get(id));
    	model.addAttribute("action", "update");
    	return "infrastructure/sysparam/sysParamDetail";
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") String  id, Model model) {
    	model.addAttribute("sysParam", sysParamService.get(id));
    	model.addAttribute("action", "view");
    	return "infrastructure/sysparam/sysParamDetail";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@Valid @ModelAttribute("sysParam") SysParam sysParam,RedirectAttributes redirectAttributes) {
    	String message = "更新成功";
    	try {
    		 sysParamService.update(sysParam);
    		JsonConvert jsonConvert = new JsonConvert();
    		actionLogService.AddActionLog("更新", String.format("更新系统参数id={0}", sysParam.getId()),jsonConvert.toJson(sysParam));
    	} catch (Exception ex) {
    		message = "更新出错，请检查输入是否正确！";
    		logger.error(String.format("更新系统参数id={0}出错，请检查输入是否正确", sysParam.getId()),ex);
    	}

    	return message;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam(value = "id") String id){
    	String message = "删除成功";		
    	try {
    		List<String> ids=new ArrayList<String>();
    		String[] values=id.split(",");
    		if(values!=null)
    		{
    			for(String fid:values)
    			{
    				if(fid!=null&&fid.length()>0)
    				{
    					ids.add(fid);
    				}
    			}
    		}			
    		sysParamService.batchDelete(ids);
    		actionLogService.AddActionLog("删除",String.format("删除系统参数id={0}", id));
    	} catch (Exception ex) {
    		message = String.format("删除系统参数id={0}出错，请检查输入是否正确", id);
    		logger.error(message,ex);
    	}

    	return message;
    }

    /**
     * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
     * Preparable二次部分绑定的效果,先根据form的id从数据库查出id对象,再把Form提交的内容绑定到该对象上。
     * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
     */
    @ModelAttribute()
    public void get(@RequestParam(value = "id", required = false) String id,
    		Model model) {
    	if (id != null && id != "") {
    		model.addAttribute("sysParam", sysParamService.get(id));
    	} else {
    		model.addAttribute("sysParam", new SysParam());
    	}
    }
}