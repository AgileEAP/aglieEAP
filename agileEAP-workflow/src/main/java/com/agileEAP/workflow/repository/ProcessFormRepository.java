package com.agileEAP.workflow.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;  
import org.apache.ibatis.annotations.Insert;  
import org.apache.ibatis.annotations.Select;  
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

import com.agileEAP.data.MyBatisRepository;
import com.agileEAP.workflow.entity.ProcessForm;

/**
* 工作流表单Repository
* 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
* 方法名称必须与Mapper.xml中保持一致.
* 
* @author trh
*/
@MyBatisRepository
public interface ProcessFormRepository {
    ProcessForm get(String id);

    List<ProcessForm> search(Map<String, Object> parameters);

    List<ProcessForm> searchByPage(Map<String, Object> parameters);

    long count(Map<String, Object> parameters);

    void save(ProcessForm processForm);

    void update(ProcessForm processForm);
    
    void updateByWhere(@Param("entity")ProcessForm processForm,@Param("parameters")Map<String, Object> parameters);

    void delete(String id);
    
    void deleteByWhere(Map<String, Object> parameters);	
    
    void batchDelete(List<String> ids);
}
