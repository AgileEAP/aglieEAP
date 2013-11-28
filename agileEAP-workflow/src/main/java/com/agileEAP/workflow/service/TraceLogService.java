package  com.agileEAP.workflow.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agileEAP.utils.*;
import com.agileEAP.data.PageDataResult;
import com.agileEAP.workflow.entity.TraceLog;
import com.agileEAP.workflow.repository.TraceLogRepository;

/**
* 流程跟踪日志Service
*
* @author trh
*/
@Component
@Transactional
public class TraceLogService {
    @Autowired
    private TraceLogRepository traceLogRepository;

    public TraceLog get(String id) {
        return traceLogRepository.get(id);
    }

    public List<TraceLog> search(Map<String, Object> parameters) {
        return traceLogRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(traceLogRepository.count(parameters));
        pageDataResult.setData(traceLogRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(TraceLog traceLog) {
        traceLogRepository.update(traceLog);
    }
    
    void updateByWhere(TraceLog traceLog,Map<String, Object> parameters){
        traceLogRepository.updateByWhere(traceLog,parameters);
    }
    
    public void save(TraceLog traceLog) {
        traceLogRepository.save(traceLog);
    }

    public void delete(String id) {
        traceLogRepository.delete(id);
    }
    
    public void deleteByWhere(Map<String, Object> parameters) {
        traceLogRepository.deleteByWhere(parameters);
    }
    
    public void batchDelete(List<String> ids) {
        traceLogRepository.batchDelete(ids);
    }	
    
	public PageDataResult searchTraceLogByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(traceLogRepository.countTraceLog(parameters));
        pageDataResult.setData(traceLogRepository.searchTraceLogByPage(parameters));

        return pageDataResult;
	}
}
