package com.agileEAP.workflow.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;  
import org.apache.ibatis.annotations.Insert;  
import org.apache.ibatis.annotations.Select;  
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

import com.agileEAP.data.MyBatisRepository;
import com.agileEAP.workflow.entity.WorkItem;
import com.agileEAP.security.entity.Operator;

/**
* 工作项Repository
* 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
* 方法名称必须与Mapper.xml中保持一致.
* 
* @author trh
*/
@MyBatisRepository
public interface WorkItemRepository {
    WorkItem get(String id);

    List<WorkItem> search(Map<String, Object> parameters);

    List<WorkItem> searchByPage(Map<String, Object> parameters);

    long count(Map<String, Object> parameters);

    void save(WorkItem workItem);

    void update(WorkItem workItem);
    
    void updateByWhere(@Param("entity")WorkItem workItem,@Param("parameters")Map<String, Object> parameters);

    void delete(String id);
    
    void deleteByWhere(Map<String, Object> parameters);	
    
    void batchDelete(List<String> ids);
    
    long countMyWorkItems(Map<String, Object> parameters);
    
    List<WorkItem> getMyWorkItemsByPage(Map<String, Object> parameters);
    
	List<Operator> getWorkItemParticipant(String workItemID);
}
