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
import com.agileEAP.infrastructure.entity.Dict;
import com.agileEAP.infrastructure.repository.DictRepository;
import com.agileEAP.utils.*;

/**
* 数据字典Service
*
* @author trh
*/
@Component
@Transactional
public class DictService {
    @Autowired
    private DictRepository dictRepository;

    public Dict get(String id) {
        return dictRepository.get(id);
    }

    public List<Dict> search(Map<String, Object> parameters) {
        return dictRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(dictRepository.count(parameters));
        pageDataResult.setData(dictRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(Dict dict) {
        dictRepository.update(dict);
    }

    public void save(Dict dict) {
        dictRepository.save(dict);
    }

    public void delete(String id) {
        dictRepository.delete(id);
    }

    public void batchDelete(List<String> ids) {
        dictRepository.batchDelete(ids);
    }	
}
