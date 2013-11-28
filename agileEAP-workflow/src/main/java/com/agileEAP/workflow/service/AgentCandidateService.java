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
import com.agileEAP.workflow.entity.AgentCandidate;
import com.agileEAP.workflow.repository.AgentCandidateRepository;

/**
* 代理候选人Service
*
* @author trh
*/
@Component
@Transactional
public class AgentCandidateService {
    @Autowired
    private AgentCandidateRepository agentCandidateRepository;

    public AgentCandidate get(String id) {
        return agentCandidateRepository.get(id);
    }

    public List<AgentCandidate> search(Map<String, Object> parameters) {
        return agentCandidateRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(agentCandidateRepository.count(parameters));
        pageDataResult.setData(agentCandidateRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(AgentCandidate agentCandidate) {
        agentCandidateRepository.update(agentCandidate);
    }
    
    void updateByWhere(AgentCandidate agentCandidate,Map<String, Object> parameters){
        agentCandidateRepository.updateByWhere(agentCandidate,parameters);
    }
    
    public void save(AgentCandidate agentCandidate) {
        agentCandidateRepository.save(agentCandidate);
    }

    public void delete(String id) {
        agentCandidateRepository.delete(id);
    }
    
    public void deleteByWhere(Map<String, Object> parameters) {
        agentCandidateRepository.deleteByWhere(parameters);
    }
    
    public void batchDelete(List<String> ids) {
        agentCandidateRepository.batchDelete(ids);
    }	

}
