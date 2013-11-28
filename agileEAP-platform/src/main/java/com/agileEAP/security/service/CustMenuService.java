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
import com.agileEAP.security.entity.CustMenu;
import com.agileEAP.security.repository.CustMenuRepository;
import com.agileEAP.utils.*;

/**
 * 
 * @author trh
 */
@Component
@Transactional
public class CustMenuService {
    @Autowired
    private CustMenuRepository custMenuRepository;

	public CustMenu get(String id) {
		return custMenuRepository.get(id);
    }

	public List<CustMenu> search(Map<String, Object> parameters) {
		return custMenuRepository.search(parameters);
	}
        
    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(custMenuRepository.count(parameters));
		pageDataResult.setData(custMenuRepository.searchByPage(parameters));
        
        return pageDataResult;
	}

	public void update(CustMenu custMenu) {
		custMenuRepository.update(custMenu);
	}

	public void save(CustMenu custMenu) {
		custMenuRepository.save(custMenu);
	}
    
	public void delete(String id) {
		custMenuRepository.delete(id);
	}
    
    public void batchDelete(List<String> ids) {
		custMenuRepository.batchDelete(ids);
	}	
}
