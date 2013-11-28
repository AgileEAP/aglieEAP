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
import com.agileEAP.workflow.entity.WorkItem;
import com.agileEAP.workflow.repository.WorkItemRepository;

/**
* 工作项Service
*
* @author trh
*/
@Component
@Transactional
public class WorkItemService {
    @Autowired
    private WorkItemRepository workItemRepository;

    public WorkItem get(String id) {
        return workItemRepository.get(id);
    }

    public List<WorkItem> search(Map<String, Object> parameters) {
        return workItemRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(workItemRepository.count(parameters));
        pageDataResult.setData(workItemRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(WorkItem workItem) {
        workItemRepository.update(workItem);
    }
    
    void updateByWhere(WorkItem workItem,Map<String, Object> parameters){
        workItemRepository.updateByWhere(workItem,parameters);
    }
    
    public void save(WorkItem workItem) {
        workItemRepository.save(workItem);
    }

    public void delete(String id) {
        workItemRepository.delete(id);
    }
    
    public void deleteByWhere(Map<String, Object> parameters) {
        workItemRepository.deleteByWhere(parameters);
    }
    
    public void batchDelete(List<String> ids) {
        workItemRepository.batchDelete(ids);
    }	

}
