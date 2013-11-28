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
import com.agileEAP.workflow.entity.ProcessInst;
import com.agileEAP.workflow.repository.ProcessInstRepository;

/**
* 流程实例Service
*
* @author trh
*/
@Component
@Transactional
public class ProcessInstService {
    @Autowired
    private ProcessInstRepository processInstRepository;

    public ProcessInst get(String id) {
        return processInstRepository.get(id);
    }

    public List<ProcessInst> search(Map<String, Object> parameters) {
        return processInstRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(processInstRepository.count(parameters));
        pageDataResult.setData(processInstRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(ProcessInst processInst) {
        processInstRepository.update(processInst);
    }
    
    void updateByWhere(ProcessInst processInst,Map<String, Object> parameters){
        processInstRepository.updateByWhere(processInst,parameters);
    }
    
    public void save(ProcessInst processInst) {
        processInstRepository.save(processInst);
    }

    public void delete(String id) {
        processInstRepository.delete(id);
    }
    
    public void deleteByWhere(Map<String, Object> parameters) {
        processInstRepository.deleteByWhere(parameters);
    }
    
    public void batchDelete(List<String> ids) {
        processInstRepository.batchDelete(ids);
    }	

}
