package com.agileEAP.infrastructure.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;  
import org.apache.ibatis.annotations.Insert;  
import org.apache.ibatis.annotations.Select;  
import org.apache.ibatis.annotations.Update;

import com.agileEAP.data.MyBatisRepository;
import com.agileEAP.infrastructure.entity.EForm;

/**
* 电子表单Repository
* 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
* 方法名称必须与Mapper.xml中保持一致.
* 
* @author trh
*/
@MyBatisRepository
public interface EFormRepository {
    EForm get(String id);

    List<EForm> search(Map<String, Object> parameters);

    List<EForm> searchByPage(Map<String, Object> parameters);

    long count(Map<String, Object> parameters);

    void save(EForm eForm);

    void update(EForm eForm);

    void delete(String id);

    void batchDelete(List<String> ids);	
}
