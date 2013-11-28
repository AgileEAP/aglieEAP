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
import com.agileEAP.security.entity.Duty;
import com.agileEAP.security.repository.DutyRepository;
import com.agileEAP.utils.*;

/**
 * 
 * @author trh
 */
@Component
@Transactional
public class DutyService {
    @Autowired
    private DutyRepository dutyRepository;

	public Duty get(String id) {
		return dutyRepository.get(id);
    }

	public List<Duty> search(Map<String, Object> parameters) {
		return dutyRepository.search(parameters);
	}
        
    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(dutyRepository.count(parameters));
		pageDataResult.setData(dutyRepository.searchByPage(parameters));
        
        return pageDataResult;
	}

	public void update(Duty duty) {
		dutyRepository.update(duty);
	}

	public void save(Duty duty) {
		dutyRepository.save(duty);
	}
    
	public void delete(String id) {
		dutyRepository.delete(id);
	}
    
    public void batchDelete(List<String> ids) {
		dutyRepository.batchDelete(ids);
	}	
}
