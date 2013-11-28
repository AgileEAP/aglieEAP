package com.agileEAP.workflow.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;  
import org.apache.ibatis.annotations.Insert;  
import org.apache.ibatis.annotations.Select;  
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

import com.agileEAP.data.MyBatisRepository;
import com.agileEAP.workflow.entity.ProcessInst;
import com.agileEAP.workflow.viewModel.TransitionModel;

/**
* 流程实例Repository
* 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
* 方法名称必须与Mapper.xml中保持一致.
* 
* @author trh
*/
@MyBatisRepository
public interface ProcessInstRepository {
    ProcessInst get(String id);

    List<ProcessInst> search(Map<String, Object> parameters);

    List<ProcessInst> searchByPage(Map<String, Object> parameters);

    long count(Map<String, Object> parameters);

    void save(ProcessInst processInst);

    void update(ProcessInst processInst);
    
    void updateByWhere(@Param("entity")ProcessInst processInst,@Param("parameters")Map<String, Object> parameters);

    void delete(String id);
    
    void deleteByWhere(Map<String, Object> parameters);	
    
    void batchDelete(List<String> ids);
    
	/** 
	 获取流程实例迁移记录
	 
	 @param processInstID 流程实例ID
	 @return 
	*/
	List<TransitionModel> getProcessInstTransitions(String processInstID);
}
