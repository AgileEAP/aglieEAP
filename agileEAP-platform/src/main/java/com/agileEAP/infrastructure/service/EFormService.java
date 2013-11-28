package  com.agileEAP.infrastructure.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agileEAP.data.PageDataResult;
import com.agileEAP.infrastructure.entity.EForm;
import com.agileEAP.infrastructure.repository.EFormRepository;
import com.agileEAP.utils.*;

/**
* 电子表单Service
*
* @author trh
*/
@Component
@Transactional
public class EFormService {
    @Autowired
    private EFormRepository eFormRepository;

    public EForm get(String id) {
        return eFormRepository.get(id);
    }

    public List<EForm> search(Map<String, Object> parameters) {
        return eFormRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(eFormRepository.count(parameters));
        pageDataResult.setData(eFormRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(EForm eForm) {
        eFormRepository.update(eForm);
    }

    public void save(EForm eForm) {
        eFormRepository.save(eForm);
    }

    public void delete(String id) {
        eFormRepository.delete(id);
    }

    public void batchDelete(List<String> ids) {
        eFormRepository.batchDelete(ids);
    }	
}
