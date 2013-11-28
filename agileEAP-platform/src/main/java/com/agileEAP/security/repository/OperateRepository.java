package com.agileEAP.security.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;  
import org.apache.ibatis.annotations.Insert;  
import org.apache.ibatis.annotations.Select;  
import org.apache.ibatis.annotations.Update;

import com.agileEAP.data.MyBatisRepository;
import com.agileEAP.security.entity.Operate;
/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author trh
 */
@MyBatisRepository
public interface OperateRepository {
	Operate get(String id);
	
	List<Operate> findByPage(Map<String, Object> parameters);
	
	List<Operate> search(Map<String, Object> parameters);
    
    List<Operate> searchByPage(Map<String, Object> parameters);
    
    long count(Map<String, Object> parameters);

	void save(Operate operate);
	
	void update(Operate operate);

	void delete(String id);
    
    void batchDelete(List<String> ids);	
    
    @Select("select a.* from ac_operate a inner join ac_privilege b on a.id=b.operateID where b.resourceid=#{resourceID} order by b.SortOrder")
    List<Operate> findOperatesByResourceID(String resourceID);  
    
    @Select("select a.* from ac_operate a inner join ac_privilege b on a.id=b.operateID inner join AC_RolePrivilege c on b.ID=c.PrivilegeID inner join OM_ObjectRole d on d.roleID=c.roleID where b.resourceid=#{resourceID} and d.objectid=#{operatorID} order by b.sortOrder")
    List<Operate> getAuthorizeOperates(Map<String,Object> parameters); 
}
