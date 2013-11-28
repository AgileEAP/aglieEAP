package com.agileEAP.security.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;  
import org.apache.ibatis.annotations.Insert;  
import org.apache.ibatis.annotations.Select;  
import org.apache.ibatis.annotations.Update;

import com.agileEAP.data.MyBatisRepository;
import com.agileEAP.security.entity.Organization;

/**
* 组织机构 Repository
* 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
* 方法名称必须与Mapper.xml中保持一致.
* 
* @author trh
*/
@MyBatisRepository
public interface OrganizationRepository {
    Organization get(String id);
    
    @Select("select max(id) from om_organization where parentID=#{parentId}")
    String getMaxChildId(String parentId);

    List<Organization> search(Map<String, Object> parameters);

    List<Organization> searchByPage(Map<String, Object> parameters);

    long count(Map<String, Object> parameters);

    void save(Organization organization);

    void update(Organization organization);

    void delete(String id);

    void batchDelete(List<String> ids);	
}
