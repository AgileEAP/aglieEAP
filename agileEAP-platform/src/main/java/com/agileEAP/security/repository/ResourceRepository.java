package com.agileEAP.security.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;  
import org.apache.ibatis.annotations.Insert;  
import org.apache.ibatis.annotations.Select;  
import org.apache.ibatis.annotations.Update;

import com.agileEAP.data.MyBatisRepository;
import com.agileEAP.data.PageDataResult;
import com.agileEAP.security.entity.Operator;
import com.agileEAP.security.entity.Resource;
/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author trh
 */
@MyBatisRepository
public interface ResourceRepository {
	Resource get(String  id);
	
	List<Resource> search(Map<String, Object> parameters);
    
	List<Resource> searchByPage(Map<String, Object> parameters);
	
	 long count(Map<String, Object> parameters);

	void save(Resource resource);
	
	void update(Resource resource);

	void delete(String  id);
	
/*	@Select("select * from ac_resource where parentid = '0' order by sortorder")
	List<Resource> getNumOneMenu();
	
	@Select("select * from ac_resource where parentid =#{id} order by sortorder")
	List<Resource> getSecondMenu(Map<String, Object> map);
	
	@Select("select * from ac_resource where id not in(select parentid from ac_resource) order by sortorder ")
	List<Resource> getThirdMenu(String moduleID);*/
	
	public void batchDelete(List<String> ids);
}
