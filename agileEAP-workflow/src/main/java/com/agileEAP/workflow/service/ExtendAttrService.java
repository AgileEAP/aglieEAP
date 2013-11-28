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
import com.agileEAP.workflow.entity.ExtendAttr;
import com.agileEAP.workflow.repository.ExtendAttrRepository;

/**
* 扩展属性Service
*
* @author trh
*/
@Component
@Transactional
public class ExtendAttrService {
    @Autowired
    private ExtendAttrRepository extendAttrRepository;

    public ExtendAttr get(String id) {
        return extendAttrRepository.get(id);
    }

    public List<ExtendAttr> search(Map<String, Object> parameters) {
        return extendAttrRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(extendAttrRepository.count(parameters));
        pageDataResult.setData(extendAttrRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(ExtendAttr extendAttr) {
        extendAttrRepository.update(extendAttr);
    }
    
    void updateByWhere(ExtendAttr extendAttr,Map<String, Object> parameters){
        extendAttrRepository.updateByWhere(extendAttr,parameters);
    }
    
    public void save(ExtendAttr extendAttr) {
        extendAttrRepository.save(extendAttr);
    }

    public void delete(String id) {
        extendAttrRepository.delete(id);
    }
    
    public void deleteByWhere(Map<String, Object> parameters) {
        extendAttrRepository.deleteByWhere(parameters);
    }
    
    public void batchDelete(List<String> ids) {
        extendAttrRepository.batchDelete(ids);
    }	

}
