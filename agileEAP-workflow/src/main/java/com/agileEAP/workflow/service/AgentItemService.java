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
import com.agileEAP.workflow.entity.AgentItem;
import com.agileEAP.workflow.repository.AgentItemRepository;

/**
* 代理项Service
*
* @author trh
*/
@Component
@Transactional
public class AgentItemService {
    @Autowired
    private AgentItemRepository agentItemRepository;

    public AgentItem get(String id) {
        return agentItemRepository.get(id);
    }

    public List<AgentItem> search(Map<String, Object> parameters) {
        return agentItemRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(agentItemRepository.count(parameters));
        pageDataResult.setData(agentItemRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(AgentItem agentItem) {
        agentItemRepository.update(agentItem);
    }
    
    void updateByWhere(AgentItem agentItem,Map<String, Object> parameters){
        agentItemRepository.updateByWhere(agentItem,parameters);
    }
    
    public void save(AgentItem agentItem) {
        agentItemRepository.save(agentItem);
    }

    public void delete(String id) {
        agentItemRepository.delete(id);
    }
    
    public void deleteByWhere(Map<String, Object> parameters) {
        agentItemRepository.deleteByWhere(parameters);
    }
    
    public void batchDelete(List<String> ids) {
        agentItemRepository.batchDelete(ids);
    }	

}
