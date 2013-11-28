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
import com.agileEAP.workflow.entity.ProcessForm;
import com.agileEAP.workflow.repository.ProcessFormRepository;

/**
* 工作流表单Service
*
* @author trh
*/
@Component
@Transactional
public class ProcessFormService {
    @Autowired
    private ProcessFormRepository processFormRepository;

    public ProcessForm get(String id) {
        return processFormRepository.get(id);
    }

    public List<ProcessForm> search(Map<String, Object> parameters) {
        return processFormRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(processFormRepository.count(parameters));
        pageDataResult.setData(processFormRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(ProcessForm processForm) {
        processFormRepository.update(processForm);
    }
    
    void updateByWhere(ProcessForm processForm,Map<String, Object> parameters){
        processFormRepository.updateByWhere(processForm,parameters);
    }
    
    public void save(ProcessForm processForm) {
        processFormRepository.save(processForm);
    }

    public void delete(String id) {
        processFormRepository.delete(id);
    }
    
    public void deleteByWhere(Map<String, Object> parameters) {
        processFormRepository.deleteByWhere(parameters);
    }
    
    public void batchDelete(List<String> ids) {
        processFormRepository.batchDelete(ids);
    }	

}
