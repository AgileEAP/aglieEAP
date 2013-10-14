package com.agileEAP.security.controller;

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
import com.agileEAP.infrastructure.service.ActionLogService;
import com.agileEAP.security.entity.Position;
import com.agileEAP.security.service.PositionService;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;
import com.agileEAP.utils.*;

/**
* 岗位定义表Controller
* restful风格的urls
* list page : GET /security/position/
* add page : GET /security/position/add
* add action : POST /security/position/add
* update page : GET /security/position/update/{id}
* update action : POST /security/position/update
* delete action : POST /security/position/delete
* 
* @author trh
*/
@Controller
@RequestMapping(value = "/security/position")
public class  PositionController {
    private static Logger logger = LoggerFactory.getLogger(PositionController.class);

    @Autowired
    private ActionLogService actionLogService;

    @Autowired
    private PositionService  positionService;

    @RequestMapping(value = "")
    public String list() {
    	return "security/position/positionList";
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
    	PageDataResult pageDataResult = positionService.searchByPage(searchParams);

    	return pageDataResult;
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String createForm(Model model) {
    	model.addAttribute("position", new Position ());
    	model.addAttribute("action", "add");
    	return "security/position/positionDetail";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String create(@Valid @ModelAttribute("position") Position position) {
    	String message = "新增岗位定义表成功";
    	try {
    		position.setId(UUID.randomUUID().toString());
    		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    		if (user != null)
    		{
    			position.setCreator(user.id);
    		}
    		position.setCreateTime(new Date());
    		positionService.save(position);
    		JsonConvert jsonConvert = new JsonConvert();
    		actionLogService.AddActionLog("新增", message,jsonConvert.toJson(position));
    	} catch (Exception ex) {
    		message = "新增岗位定义表出错，请检查输入是否正确！";
    		logger.error(String.format("新增岗位定义表id={0}出错，请检查输入是否正确", position.getId()),ex);
    	}

    	return message;
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") String id, Model model) {
    	model.addAttribute("position", positionService.get(id));
    	model.addAttribute("action", "update");
    	return "security/position/positionDetail";
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") String  id, Model model) {
    	model.addAttribute("position", positionService.get(id));
    	model.addAttribute("action", "view");
    	return "security/position/positionDetail";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@Valid @ModelAttribute("position") Position position,RedirectAttributes redirectAttributes) {
    	String message = "更新成功";
    	try {
    		 positionService.update(position);
    		JsonConvert jsonConvert = new JsonConvert();
    		actionLogService.AddActionLog("更新", String.format("更新岗位定义表id={0}", position.getId()),jsonConvert.toJson(position));
    	} catch (Exception ex) {
    		message = "更新出错，请检查输入是否正确！";
    		logger.error(String.format("更新岗位定义表id={0}出错，请检查输入是否正确", position.getId()),ex);
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
    		positionService.batchDelete(ids);
    		actionLogService.AddActionLog("删除",String.format("删除岗位定义表id={0}", id));
    	} catch (Exception ex) {
    		message = String.format("删除岗位定义表id={0}出错，请检查输入是否正确", id);
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
    		model.addAttribute("position", positionService.get(id));
    	} else {
    		model.addAttribute("position", new Position());
    	}
    }
}