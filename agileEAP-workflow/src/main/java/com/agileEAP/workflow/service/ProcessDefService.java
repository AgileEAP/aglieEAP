package  com.agileEAP.workflow.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agileEAP.utils.*;
import com.agileEAP.data.PageDataResult;
import com.agileEAP.workflow.entity.ProcessDef;
import com.agileEAP.workflow.repository.ProcessDefRepository;

/**
* 流程定义Service
*
* @author trh
*/
@Component
@Transactional
public class ProcessDefService {
    @Autowired
    private ProcessDefRepository processDefRepository;

    public ProcessDef get(String id) {
        return processDefRepository.get(id);
    }

    public List<ProcessDef> search(Map<String, Object> parameters) {
        return processDefRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(processDefRepository.count(parameters));
        pageDataResult.setData(processDefRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(ProcessDef processDef) {
        processDefRepository.update(processDef);
    }
    
    void updateByWhere(ProcessDef processDef,Map<String, Object> parameters){
        processDefRepository.updateByWhere(processDef,parameters);
    }
    
    public void save(ProcessDef processDef) {
        processDefRepository.save(processDef);
    }

    public void delete(String id) {
        processDefRepository.delete(id);
    }
    
    public void saveOrUpdate(ProcessDef processDef) {
    	if(processDefRepository.get(processDef.getId())==null)
    	{
    		processDefRepository.save(processDef);
    	}
    	else
    	{
    		processDefRepository.update(processDef);
    	}
    }
    
    public void deleteByWhere(Map<String, Object> parameters) {
        processDefRepository.deleteByWhere(parameters);
    }
    
    public void batchDelete(List<String> ids) {
        processDefRepository.batchDelete(ids);
    }	

}
