package com.agileEAP.infrastructure.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;  
import org.apache.ibatis.annotations.Insert;  
import org.apache.ibatis.annotations.Select;  
import org.apache.ibatis.annotations.Update;

import com.agileEAP.data.MyBatisRepository;
import com.agileEAP.infrastructure.entity.ActionLog;
/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author trh
 */
@MyBatisRepository
public interface ActionLogRepository {
	ActionLog get(String id);
	
	List<ActionLog> search(Map<String, Object> parameters);
    
    List<ActionLog> searchByPage(Map<String, Object> parameters);
    
    long count(Map<String, Object> parameters);

	void save(ActionLog actionLog);
	
	void update(ActionLog actionLog);

	void delete(String id);
    
    void batchDelete(List<String> ids);	
}
