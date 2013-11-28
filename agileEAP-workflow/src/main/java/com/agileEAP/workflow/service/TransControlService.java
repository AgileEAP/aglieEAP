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
import com.agileEAP.workflow.entity.TransControl;
import com.agileEAP.workflow.repository.TransControlRepository;

/**
* 流程迁移控制Service
*
* @author trh
*/
@Component
@Transactional
public class TransControlService {
    @Autowired
    private TransControlRepository transControlRepository;

    public TransControl get(String id) {
        return transControlRepository.get(id);
    }

    public List<TransControl> search(Map<String, Object> parameters) {
        return transControlRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(transControlRepository.count(parameters));
        pageDataResult.setData(transControlRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(TransControl transControl) {
        transControlRepository.update(transControl);
    }
    
    void updateByWhere(TransControl transControl,Map<String, Object> parameters){
        transControlRepository.updateByWhere(transControl,parameters);
    }
    
    public void save(TransControl transControl) {
        transControlRepository.save(transControl);
    }

    public void delete(String id) {
        transControlRepository.delete(id);
    }
    
    public void deleteByWhere(Map<String, Object> parameters) {
        transControlRepository.deleteByWhere(parameters);
    }
    
    public void batchDelete(List<String> ids) {
        transControlRepository.batchDelete(ids);
    }	

}
