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
import com.agileEAP.workflow.entity.ActivityInst;
import com.agileEAP.workflow.repository.ActivityInstRepository;

/**
* 活动实例Service
*
* @author trh
*/
@Component
@Transactional
public class ActivityInstService {
    @Autowired
    private ActivityInstRepository activityInstRepository;

    public ActivityInst get(String id) {
        return activityInstRepository.get(id);
    }

    public List<ActivityInst> search(Map<String, Object> parameters) {
        return activityInstRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(activityInstRepository.count(parameters));
        pageDataResult.setData(activityInstRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(ActivityInst activityInst) {
        activityInstRepository.update(activityInst);
    }
    
    void updateByWhere(ActivityInst activityInst,Map<String, Object> parameters){
        activityInstRepository.updateByWhere(activityInst,parameters);
    }
    
    public void save(ActivityInst activityInst) {
        activityInstRepository.save(activityInst);
    }

    public void delete(String id) {
        activityInstRepository.delete(id);
    }
    
    public void deleteByWhere(Map<String, Object> parameters) {
        activityInstRepository.deleteByWhere(parameters);
    }
    
    public void batchDelete(List<String> ids) {
        activityInstRepository.batchDelete(ids);
    }	

}
