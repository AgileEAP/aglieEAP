package  com.agileEAP.security.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agileEAP.data.PageDataResult;
import com.agileEAP.security.entity.Employee;
import com.agileEAP.security.repository.EmployeeRepository;
import com.agileEAP.utils.*;

/**
 * 
 * @author trh
 */
@Component
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

	public Employee get(String id) {
		return employeeRepository.get(id);
    }

	public List<Employee> search(Map<String, Object> parameters) {
		return employeeRepository.search(parameters);
	}
        
    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(employeeRepository.count(parameters));
		pageDataResult.setData(employeeRepository.searchByPage(parameters));
        
        return pageDataResult;
	}

	public void update(Employee employee) {
		employeeRepository.update(employee);
	}

	public void save(Employee employee) {
		employeeRepository.save(employee);
	}
    
	public void delete(String id) {
		employeeRepository.delete(id);
	}
    
    public void batchDelete(List<String> ids) {
		employeeRepository.batchDelete(ids);
	}	
}
