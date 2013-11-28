package com.agileEAP.workflow.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;  
import org.apache.ibatis.annotations.Insert;  
import org.apache.ibatis.annotations.Select;  
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

import com.agileEAP.data.MyBatisRepository;
import com.agileEAP.workflow.entity.AgentCandidate;

/**
* 代理候选人Repository
* 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
* 方法名称必须与Mapper.xml中保持一致.
* 
* @author trh
*/
@MyBatisRepository
public interface AgentCandidateRepository {
    AgentCandidate get(String id);

    List<AgentCandidate> search(Map<String, Object> parameters);

    List<AgentCandidate> searchByPage(Map<String, Object> parameters);

    long count(Map<String, Object> parameters);

    void save(AgentCandidate agentCandidate);

    void update(AgentCandidate agentCandidate);
    
    void updateByWhere(@Param("entity")AgentCandidate agentCandidate,@Param("parameters")Map<String, Object> parameters);

    void delete(String id);
    
    void deleteByWhere(Map<String, Object> parameters);	
    
    void batchDelete(List<String> ids);
}
