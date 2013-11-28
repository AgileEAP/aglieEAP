package com.agileEAP.security.service;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agileEAP.data.PageDataResult;
import com.agileEAP.security.entity.Operate;
import com.agileEAP.security.entity.Privilege;
import com.agileEAP.security.entity.Resource;
import com.agileEAP.security.repository.OperateRepository;
import com.agileEAP.security.repository.PrivilegeRepository;
import com.agileEAP.security.repository.ResourceRepository;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 
 * @author trh
 */
@Component
@Transactional
public class ResourceService {
/*	@Autowired 
	private RedisTemplate< String, Object > redisTemplate;*/

	@Autowired
	private ResourceRepository resourceRepository;

	@Autowired
	private OperateRepository operateRepository;

	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	private  static Map<String,Resource> cachedResources=new HashMap<>();		

	public Resource get(String id) {
		return resourceRepository.get(id);
	}

	//@Cacheable("Resource")
	public List<Resource> search(Map<String, Object> parameters) {
		return resourceRepository.search(parameters);
	}
	
	public String getDefaultMenu(String sysID) {
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("parentID",sysID);
		searchParams.put("$orderby", "sortOrder");
		List<Resource> headerMenus = resourceRepository.search(searchParams);
		
		return headerMenus.get(0).getId();
	}

	public List<String> getAuthorizedResources(final String operatorID)
	{
/*		final	String key=	operatorID+".authorizedResources";
		return redisTemplate.execute(new RedisCallback<List<String>>(){
			@Override
			public	List<String> doInRedis(RedisConnection connection) throws DataAccessException
			{
				byte[] rawkey=((RedisSerializer<String>)redisTemplate.getKeySerializer()).serialize(key);
				byte[] rawvalue=connection.get(rawkey);
				List<String> values=null;
				if(rawvalue!=null)
				{
					values=((RedisSerializer<List<String>>)redisTemplate.getValueSerializer()).deserialize(rawvalue);
					return values;
				}
				values =privilegeRepository.getAuthorizedResources(operatorID);
				connection.set(rawkey, ((RedisSerializer<List<String>>)redisTemplate.getValueSerializer()).serialize(values));
				return values;
			}
		});*/
		
	
		//redisTemplate.opsForList().leftPushAll(operatorID+".authorizedResources", values);
		return privilegeRepository.getAuthorizedResources(operatorID);
	}
	
	public PageDataResult searchByPage(Map<String, Object> parameters) {
		PageDataResult pageDataResult = new PageDataResult();
		pageDataResult.setTotal(resourceRepository.count(parameters));
		pageDataResult.setData(resourceRepository.searchByPage(parameters));
		return pageDataResult;
	}

	public void update(Resource resource) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("resourceID", resource.getId());
		parameters.put("operateID", "");
		List<Privilege> privileges = privilegeRepository.search(parameters);
		if (privileges != null && privileges.size() >0) {
			Privilege privilege = privileges.get(0);
			privilege.setName(resource.getName());
			privilege.setResourceID(resource.getId());
			privilege.setOperateID("");
			privilege.setSortOrder(resource.getSortOrder());
			privilege.setType(resource.getType());

			privilegeRepository.update(privilege);
		}
		resourceRepository.update(resource);

		List<String> exitsOperates = new ArrayList<String>();
		for (Operate operate : resource.getOperates()) {
			exitsOperates.add(operate.getId());
		}
		
		parameters.clear();
		parameters.put("resourceID", resource.getId());
		parameters.put("type", (short) 3);
		privileges = privilegeRepository.search(parameters);
		List<String> operateIds = new ArrayList<String>();
		List<String> privilegeIds = new ArrayList<String>();
		for (Privilege privilege : privileges) {
			if (!exitsOperates.contains(privilege.getOperateID())) {
				operateIds.add(privilege.getOperateID());
				privilegeIds.add(privilege.getId());
			}
		}

		if (operateIds.size() > 0) {
			//operateRepository.batchDelete(operateIds);
			privilegeRepository.batchDelete(privilegeIds);
		}
		
		if (resource.getOperates() != null && resource.getOperates().size() > 0) {
			int i=0;
			for (Operate operate : resource.getOperates()) {
				parameters.clear();
				parameters.put("resourceID", resource.getId());
				parameters.put("operateID", operate.getId());
				parameters.put("type", (short) 3);
				privileges = privilegeRepository.search(parameters);
				++i;
				if (privileges == null || privileges.size() == 0) {
					//operate.setId(UUID.randomUUID().toString());
					Privilege privilege = new Privilege();
					privilege.setId(UUID.randomUUID().toString());
					privilege.setName(operate.getName());
					privilege.setResourceID(resource.getId());
					privilege.setOperateID(operate.getId());
					privilege.setSortOrder(i);
					privilege.setMetaDataID("");
					privilege.setType((short) 3);

					privilegeRepository.save(privilege);
					//operateRepository.save(operate);
				} else {
					Privilege privilege = privileges.get(0);
					privilege.setName(operate.getName());
					privilege.setResourceID(resource.getId());
					privilege.setOperateID(operate.getId());
					privilege.setSortOrder(i);
					privilege.setMetaDataID("");
					privilege.setType((short) 3);

					privilegeRepository.update(privilege);
					//operateRepository.update(operate);
				}
			}
		}
		
		cachedResources.clear();
/*		else
		{
			parameters.clear();
			parameters.put("resourceID", resource.getId());
			parameters.put("type", (short) 3);
			privileges = privilegeRepository.search(parameters);
			List<String> operateIds = new ArrayList<String>();
			List<String> privilegeIds = new ArrayList<String>();
			for (Privilege privilege : privileges) {
					operateIds.add(privilege.getOperateID());
					privilegeIds.add(privilege.getId());
			}
			
			if (operateIds.size() > 0) {
				operateRepository.batchDelete(operateIds);
				privilegeRepository.batchDelete(privilegeIds);
			}
		}*/
	}

	public void save(Resource resource) {
		Privilege privilege = new Privilege();
		privilege.setId(UUID.randomUUID().toString());
		privilege.setName(resource.getName());
		privilege.setResourceID(resource.getId());
		privilege.setSortOrder(resource.getSortOrder());
		privilege.setOperateID("");
		privilege.setMetaDataID("");
		privilege.setType(resource.getType());

		privilegeRepository.save(privilege);

		if (resource.getOperates() != null && resource.getOperates().size() > 0) {
			int i=0;
			for (Operate operate : resource.getOperates()) {
				//operate.setId(UUID.randomUUID().toString());
				i++;
				privilege = new Privilege();
				privilege.setId(UUID.randomUUID().toString());
				privilege.setName(operate.getName());
				privilege.setResourceID(resource.getId());
				privilege.setOperateID(operate.getId());
				privilege.setSortOrder(i);
				privilege.setMetaDataID("");
				privilege.setType((short) 3);

				privilegeRepository.save(privilege);
				//operateRepository.save(operate);
			}
		}
		resourceRepository.save(resource);
		
		cachedResources.clear();
	}

	public void delete(String id) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("resourceID", id);
		List<Privilege> privileges = privilegeRepository.search(parameters);
		List<String> operateIds = new ArrayList<String>();
		List<String> privilegeIds = new ArrayList<String>();
		for (Privilege privilege : privileges) {
			privilegeIds.add(privilege.getId());
			if (privilege.getOperateID() != null&& privilege.getOperateID().length() > 0) {
				operateIds.add(privilege.getOperateID());
			}
		}
		if (operateIds.size() > 0) {
			//operateRepository.batchDelete(operateIds);
			privilegeRepository.batchDelete(privilegeIds);
		}

		resourceRepository.delete(id);
		
		cachedResources.clear();
	}

/*	public List<Resource> getNumOneMenu() {
		return resourceRepository.getNumOneMenu();
	}

	public List<Resource> getSecondMenu(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return resourceRepository.getSecondMenu(map);
	}

	public List<Resource> getThirdMenu(String moduleID) {
		// TODO Auto-generated method stub
		return resourceRepository.getThirdMenu(moduleID);
	}*/

	public void batchDelete(List<String> ids) {
		resourceRepository.batchDelete(ids);
	}

	private Map<String,Resource>  getCachedResources() {
		 Map<String,Resource> urls=new HashMap<>();
		 Map<String,Object> paramMap=new HashMap<String, Object>();
		 List<Resource> resources= resourceRepository.search(paramMap);
		 for(Resource resource :resources)
		 {
			 String url=(resource.getUrl()==null?resource.getId():resource.getUrl()+(resource.getEntry()==null||resource.getEntry().length()==0?"":"?entry="+resource.getEntry()));
			 urls.put(url,resource); 
		 }		 
		 
		 return urls;
	}
	
	public List<String> getAuthorizedPrivileges(String operatorID)
	{
		 return privilegeRepository.getAuthorizedPrivileges(operatorID);
	}
	
	public List<HashMap<String, String>> getPrivileges()
	{
		 return privilegeRepository.getPrivileges();
	}
	
	private String getNavigationTitle(Resource resource) {
		String navigationTitle="&gt;&gt; "+resource.getName();
		
		for(Map.Entry<String,Resource> entry :cachedResources.entrySet()){
			if (entry.getValue().getId().equals(resource.getParentID())) {				
				return getNavigationTitle(entry.getValue())+navigationTitle;
			}
		}
		
		return navigationTitle;
	}
	
	public Resource getAuthorizedResource(ShiroUser user,String requestURL,String entry)
	{
		if (cachedResources==null||cachedResources.size()==0) {
			cachedResources=getCachedResources();
		}
		
		if (cachedResources.containsKey(requestURL+(entry==null||entry.length()==0?"":"?entry="+entry))) {			
			Map<String, Object> paramMap=new HashMap<>();
			paramMap.put("url", requestURL);
			paramMap.put("entry",entry);			
			List<Resource> resources=resourceRepository.search(paramMap);			
			Resource resource=null;
			for (Resource res : resources) {
				if(((res.getEntry()==null||res.getEntry().length()==0)&&entry==null)|| res.getEntry().equals(entry))
				{
					resource=res;
					break;
				}
			}
	
			if(user.userType==0)
			{
				resource.setOperates(operateRepository.findOperatesByResourceID(resource.getId()));
			}
			else
			{
				paramMap.clear();
				paramMap.put("resourceID",resource.getId());
				paramMap.put("operatorID",user.id);
				
				resource.setOperates(operateRepository.getAuthorizeOperates(paramMap));
			}		
			
			resource.setNavigationTitle(getNavigationTitle(resource));
			
			return resource;
		}
		
		return null;
	}
}
