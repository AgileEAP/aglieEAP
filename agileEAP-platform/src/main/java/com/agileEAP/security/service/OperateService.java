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
import com.agileEAP.security.entity.Operate;
import com.agileEAP.security.repository.OperateRepository;
import com.agileEAP.utils.*;

/**
 * 
 * @author trh
 */
@Component
@Transactional
public class OperateService {
    @Autowired
    private OperateRepository operateRepository;

	public Operate get(String id) {
		return operateRepository.get(id);
    }

	public List<Operate> search(Map<String, Object> parameters) {
		return operateRepository.search(parameters);
	}
        
    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(operateRepository.count(parameters));
		pageDataResult.setData(operateRepository.searchByPage(parameters));
        
        return pageDataResult;
	}
    
    public int getMaxSortOrder()
    {
    	return (int)operateRepository.count(null);
    }

	public void update(Operate operate) {
		operateRepository.update(operate);
	}

	public void save(Operate operate) {
		operateRepository.save(operate);
	}
    
	public void delete(String id) {
		operateRepository.delete(id);
	}
    
    public void batchDelete(List<String> ids) {
		operateRepository.batchDelete(ids);
	}	
    
    public List<Operate> findOperatesByResourceID(String resourceID)
    {
    	return operateRepository.findOperatesByResourceID(resourceID);
    }
}
