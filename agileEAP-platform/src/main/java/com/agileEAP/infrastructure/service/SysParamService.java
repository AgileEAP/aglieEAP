package  com.agileEAP.infrastructure.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agileEAP.data.PageDataResult;
import com.agileEAP.infrastructure.entity.SysParam;
import com.agileEAP.infrastructure.repository.SysParamRepository;

import org.springframework.cache.annotation.Cacheable;

/**
* 系统参数Service
*
* @author trh
*/
@Component
@Transactional
public class SysParamService {
    @Autowired
    private SysParamRepository sysParamRepository;

    public SysParam get(String id) {
        return sysParamRepository.get(id);
    }
    
    @Cacheable("sysParamValues")
    public String getSysParamValue(String paramName) {
        return sysParamRepository.getSysParamValue(paramName);
    }

    public List<SysParam> search(Map<String, Object> parameters) {
        return sysParamRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(sysParamRepository.count(parameters));
        pageDataResult.setData(sysParamRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(SysParam sysParam) {
        sysParamRepository.update(sysParam);
    }

    public void save(SysParam sysParam) {
        sysParamRepository.save(sysParam);
    }

    public void delete(String id) {
        sysParamRepository.delete(id);
    }

    public void batchDelete(List<String> ids) {
        sysParamRepository.batchDelete(ids);
    }	
}
