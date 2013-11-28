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
import com.agileEAP.security.entity.Position;
import com.agileEAP.security.repository.PositionRepository;
import com.agileEAP.utils.*;

/**
 * 
 * @author trh
 */
@Component
@Transactional
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;

	public Position get(String id) {
		return positionRepository.get(id);
    }

	public List<Position> search(Map<String, Object> parameters) {
		return positionRepository.search(parameters);
	}
        
    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(positionRepository.count(parameters));
		pageDataResult.setData(positionRepository.searchByPage(parameters));
        
        return pageDataResult;
	}

	public void update(Position position) {
		positionRepository.update(position);
	}

	public void save(Position position) {
		positionRepository.save(position);
	}
    
	public void delete(String id) {
		positionRepository.delete(id);
	}
    
    public void batchDelete(List<String> ids) {
		positionRepository.batchDelete(ids);
	}	
}
