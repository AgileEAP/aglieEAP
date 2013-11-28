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
import com.agileEAP.workflow.entity.Participant;
import com.agileEAP.workflow.repository.ParticipantRepository;

/**
* 流程参与者Service
*
* @author trh
*/
@Component
@Transactional
public class ParticipantService {
    @Autowired
    private ParticipantRepository participantRepository;

    public Participant get(String id) {
        return participantRepository.get(id);
    }

    public List<Participant> search(Map<String, Object> parameters) {
        return participantRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(participantRepository.count(parameters));
        pageDataResult.setData(participantRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(Participant participant) {
        participantRepository.update(participant);
    }
    
    void updateByWhere(Participant participant,Map<String, Object> parameters){
        participantRepository.updateByWhere(participant,parameters);
    }
    
    public void save(Participant participant) {
        participantRepository.save(participant);
    }

    public void delete(String id) {
        participantRepository.delete(id);
    }
    
    public void deleteByWhere(Map<String, Object> parameters) {
        participantRepository.deleteByWhere(parameters);
    }
    
    public void batchDelete(List<String> ids) {
        participantRepository.batchDelete(ids);
    }	

}
