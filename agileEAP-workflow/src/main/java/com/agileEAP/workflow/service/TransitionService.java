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
import com.agileEAP.workflow.entity.Transition;
import com.agileEAP.workflow.repository.TransitionRepository;

/**
* 流程迁移记录Service
*
* @author trh
*/
@Component
@Transactional
public class TransitionService {
    @Autowired
    private TransitionRepository transitionRepository;

    public Transition get(String id) {
        return transitionRepository.get(id);
    }

    public List<Transition> search(Map<String, Object> parameters) {
        return transitionRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(transitionRepository.count(parameters));
        pageDataResult.setData(transitionRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(Transition transition) {
        transitionRepository.update(transition);
    }
    
    void updateByWhere(Transition transition,Map<String, Object> parameters){
        transitionRepository.updateByWhere(transition,parameters);
    }
    
    public void save(Transition transition) {
        transitionRepository.save(transition);
    }

    public void delete(String id) {
        transitionRepository.delete(id);
    }
    
    public void deleteByWhere(Map<String, Object> parameters) {
        transitionRepository.deleteByWhere(parameters);
    }
    
    public void batchDelete(List<String> ids) {
        transitionRepository.batchDelete(ids);
    }	

}
