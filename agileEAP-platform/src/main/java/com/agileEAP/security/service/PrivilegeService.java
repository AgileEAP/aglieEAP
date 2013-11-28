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
import com.agileEAP.security.entity.Privilege;
import com.agileEAP.security.repository.PrivilegeRepository;
import com.agileEAP.utils.*;

/**
 * 
 * @author trh
 */
@Component
@Transactional
public class PrivilegeService {
    @Autowired
    private PrivilegeRepository privilegeRepository;

	public Privilege get(String id) {
		return privilegeRepository.get(id);
    }

	public List<Privilege> search(Map<String, Object> parameters) {
		return privilegeRepository.search(parameters);
	}
        
    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(privilegeRepository.count(parameters));
		pageDataResult.setData(privilegeRepository.searchByPage(parameters));
        
        return pageDataResult;
	}

	public void update(Privilege privilege) {
		privilegeRepository.update(privilege);
	}

	public void save(Privilege privilege) {
		privilegeRepository.save(privilege);
	}
    
	public void delete(String id) {
		privilegeRepository.delete(id);
	}
    
    public void batchDelete(List<String> ids) {
		privilegeRepository.batchDelete(ids);
	}	
}
