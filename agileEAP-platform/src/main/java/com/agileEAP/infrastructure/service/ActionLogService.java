package  com.agileEAP.infrastructure.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agileEAP.data.PageDataResult;
import com.agileEAP.infrastructure.entity.ActionLog;
import com.agileEAP.infrastructure.repository.ActionLogRepository;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;
import com.agileEAP.utils.*;

/**
 * 
 * @author trh
 */
@Component
@Transactional
public class ActionLogService {
    @Autowired
    private ActionLogRepository actionLogRepository;

	public ActionLog get(String id) {
		return actionLogRepository.get(id);
    }

	public List<ActionLog> search(Map<String, Object> parameters) {
		return actionLogRepository.search(parameters);
	}
        
    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(actionLogRepository.count(parameters));
		pageDataResult.setData(actionLogRepository.searchByPage(parameters));
        
        return pageDataResult;
	}
    
	public void AddActionLog(String clientIP,String action,String message,String content)
	{
		ActionLog actionLog=new ActionLog();
		actionLog.setAppModule("");
		actionLog.setClientIP(clientIP);
		actionLog.setContent(content);
		actionLog.setMessage(message);
		actionLog.setId(UUID.randomUUID().toString());
		actionLog.setCreateTime(new Date());
		actionLog.setLogType((short)1);
		
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		actionLog.setUserID(user.id);
		actionLog.setUserName(user.name);
		actionLog.setResult((short)1);
		
		actionLogRepository.save(actionLog);
	}

	public void AddActionLog(String action,String message,String content)
	{
		AddActionLog("",action,message,content);
	}
	
	public void AddActionLog(String action,String message)
	{
		AddActionLog(action,message,"");
	}
	
	public void update(ActionLog actionLog) {
		actionLogRepository.update(actionLog);
	}

	public void save(ActionLog actionLog) {
		actionLogRepository.save(actionLog);
	}
    
	public void delete(String id) {
		actionLogRepository.delete(id);
	}
    
    public void batchDelete(List<String> ids) {
		actionLogRepository.batchDelete(ids);
	}	
}
