package  com.agileEAP.security.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agileEAP.data.PageDataResult;
import com.agileEAP.security.entity.Organization;
import com.agileEAP.security.repository.OrganizationRepository;
import com.agileEAP.utils.*;

/**
* 组织机构 Service
*
* @author trh
*/
@Component
@Transactional
public class OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;

    public Organization get(String id) {
        return organizationRepository.get(id);
    }
    
    public String newOrgId(String parentId) {
    	String orgId=organizationRepository.getMaxChildId(parentId);     	
    	if((orgId==null||orgId.length()==0))
    	{
    		return String.format("%s001", parentId);
    	} 		
     	
    	return String.format("%1$0"+(parentId.length()+3)+"d",Integer.parseInt(orgId)+1);
    }

    public List<Organization> search(Map<String, Object> parameters) {
        return organizationRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(organizationRepository.count(parameters));
        pageDataResult.setData(organizationRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(Organization organization) {
        organizationRepository.update(organization);
    }

    public void save(Organization organization) {
        organizationRepository.save(organization);
    }

    public void delete(String id) {
        organizationRepository.delete(id);
    }

    public void batchDelete(List<String> ids) {
        organizationRepository.batchDelete(ids);
    }	
}
