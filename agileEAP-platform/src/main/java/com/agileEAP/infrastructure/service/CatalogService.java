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
import com.agileEAP.infrastructure.entity.Catalog;
import com.agileEAP.infrastructure.repository.CatalogRepository;
import com.agileEAP.utils.*;

/**
* 文件目录Service
*
* @author trh
*/
@Component
@Transactional
public class CatalogService {
    @Autowired
    private CatalogRepository catalogRepository;

    public Catalog get(String id) {
        return catalogRepository.get(id);
    }

    public List<Catalog> search(Map<String, Object> parameters) {
        return catalogRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(catalogRepository.count(parameters));
        pageDataResult.setData(catalogRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(Catalog catalog) {
        catalogRepository.update(catalog);
    }

    public void save(Catalog catalog) {
        catalogRepository.save(catalog);
    }

    public void delete(String id) {
        catalogRepository.delete(id);
    }

    public void batchDelete(List<String> ids) {
        catalogRepository.batchDelete(ids);
    }	
}
