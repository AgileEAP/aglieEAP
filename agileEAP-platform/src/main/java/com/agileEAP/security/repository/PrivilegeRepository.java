package com.agileEAP.security.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;  
import org.apache.ibatis.annotations.Insert;  
import org.apache.ibatis.annotations.Select;  
import org.apache.ibatis.annotations.Update;

import com.agileEAP.data.MyBatisRepository;
import com.agileEAP.security.entity.Privilege;
/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author trh
 */
@MyBatisRepository
public interface PrivilegeRepository {
	Privilege get(String id);
	
	List<Privilege> search(Map<String, Object> parameters);
    
    List<Privilege> searchByPage(Map<String, Object> parameters);
    
    long count(Map<String, Object> parameters);

	void save(Privilege privilege);
	
	void update(Privilege privilege);

	void delete(String id);
    
    void batchDelete(List<String> ids);	
    
    @Select("select a.privilegeID from AC_RolePrivilege a inner join OM_ObjectRole b on a.roleID=b.roleID where b.objectid=#{operatorID}")
    List<String> getAuthorizedPrivileges(String operatorID);
    
    @Select("select p.resourceID from ac_privilege p inner join  AC_RolePrivilege  a on p.id=a.privilegeID inner join OM_ObjectRole b on a.roleID=b.roleID where b.objectid=#{operatorID} and (p.operateID='' or p.operateID is null)")
    List<String> getAuthorizedResources(String operatorID);
    
    @Select("select a.NAME,a.ID,a.PARENTID, b.ID as PRIVILEGEID,b.TYPE from ac_resource a inner join ac_privilege b on a.id=b.resourceid and(b.operateID='' or b.operateID is null) union select a.name,a.id,b.resourceid, b.id as privilegeid,b.type from ac_operate a inner join ac_privilege b on a.id=b.operateid")
    List<HashMap<String, String>> getPrivileges();
}
