package com.agileEAP.security.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;  
import org.apache.ibatis.annotations.Insert;  
import org.apache.ibatis.annotations.Select;  
import org.apache.ibatis.annotations.Update;

import com.agileEAP.data.MyBatisRepository;
import com.agileEAP.security.entity.ObjectRole;
/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author trh
 */
@MyBatisRepository
public interface ObjectRoleRepository {
	ObjectRole get(String id);
	
	List<ObjectRole> search(Map<String, Object> parameters);
    
    List<ObjectRole> searchByPage(Map<String, Object> parameters);
    
    long count(Map<String, Object> parameters);

	void save(ObjectRole objectRole);
	
	void update(ObjectRole objectRole);

	void delete(String id);
    
    void batchDelete(List<String> ids);	
}
