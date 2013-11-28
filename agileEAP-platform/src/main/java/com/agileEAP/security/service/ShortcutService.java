package  com.agileEAP.security.service;

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
import com.agileEAP.security.entity.Shortcut;
import com.agileEAP.security.repository.ShortcutRepository;
import com.agileEAP.utils.*;

/**
 * 
 * @author trh
 */
@Component
@Transactional
public class ShortcutService {
    @Autowired
    private ShortcutRepository shortcutRepository;

	public Shortcut get(String id) {
		return shortcutRepository.get(id);
    }

	public List<Shortcut> search(Map<String, Object> parameters) {
		return shortcutRepository.search(parameters);
	}
        
    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(shortcutRepository.count(parameters));
		pageDataResult.setData(shortcutRepository.searchByPage(parameters));
        
        return pageDataResult;
	}

	public void update(Shortcut shortcut) {
		shortcutRepository.update(shortcut);
	}

	public void save(Shortcut shortcut) {
		shortcutRepository.save(shortcut);
	}
    
	public void delete(String id) {
		shortcutRepository.delete(id);
	}
    
    public void batchDelete(List<String> ids) {
		shortcutRepository.batchDelete(ids);
	}	
}
