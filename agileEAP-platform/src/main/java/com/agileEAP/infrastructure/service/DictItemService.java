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
import com.agileEAP.infrastructure.entity.DictItem;
import com.agileEAP.infrastructure.repository.DictItemRepository;
import com.agileEAP.utils.*;

/**
* 字典项Service
*
* @author trh
*/
@Component
@Transactional
public class DictItemService {
    @Autowired
    private DictItemRepository dictItemRepository;

    public DictItem get(String id) {
        return dictItemRepository.get(id);
    }

    public List<DictItem> search(Map<String, Object> parameters) {
        return dictItemRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(dictItemRepository.count(parameters));
        pageDataResult.setData(dictItemRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(DictItem dictItem) {
        dictItemRepository.update(dictItem);
    }

    public void save(DictItem dictItem) {
        dictItemRepository.save(dictItem);
    }

    public void delete(String id) {
        dictItemRepository.delete(id);
    }

    public void batchDelete(List<String> ids) {
        dictItemRepository.batchDelete(ids);
    }	
}
