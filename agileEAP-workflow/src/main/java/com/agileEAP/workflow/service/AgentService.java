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
import com.agileEAP.workflow.entity.Agent;
import com.agileEAP.workflow.repository.AgentRepository;

/**
* 工作代理Service
*
* @author trh
*/
@Component
@Transactional
public class AgentService {
    @Autowired
    private AgentRepository agentRepository;

    public Agent get(String id) {
        return agentRepository.get(id);
    }

    public List<Agent> search(Map<String, Object> parameters) {
        return agentRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(agentRepository.count(parameters));
        pageDataResult.setData(agentRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(Agent agent) {
        agentRepository.update(agent);
    }
    
    void updateByWhere(Agent agent,Map<String, Object> parameters){
        agentRepository.updateByWhere(agent,parameters);
    }
    
    public void save(Agent agent) {
        agentRepository.save(agent);
    }

    public void delete(String id) {
        agentRepository.delete(id);
    }
    
    public void deleteByWhere(Map<String, Object> parameters) {
        agentRepository.deleteByWhere(parameters);
    }
    
    public void batchDelete(List<String> ids) {
        agentRepository.batchDelete(ids);
    }	

}
