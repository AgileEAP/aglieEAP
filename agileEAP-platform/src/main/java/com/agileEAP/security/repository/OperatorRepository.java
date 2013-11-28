package com.agileEAP.security.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;  
import org.apache.ibatis.annotations.Insert;  
import org.apache.ibatis.annotations.Select;  
import org.apache.ibatis.annotations.Update;

import com.agileEAP.data.MyBatisRepository;
import com.agileEAP.data.PageDataResult;
import com.agileEAP.security.entity.Operator;
/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author trh
 */
@MyBatisRepository
public interface OperatorRepository {
	Operator get(String  id);
	
	@Select("select * from ac_operator where loginName = #{loginName}")
	Operator getByLoginName(String  loginName);
	
    long count(Map<String, Object> parameters);
    
	List<Operator> search(Map<String, Object> parameters);
    
	List<Operator> searchByPage(Map<String, Object> parameters);

	void save(Operator operator);
	
	void update(Operator operator);

	void delete(String  id);
	
    public void batchDelete(List<String> ids);

	void updatePwd(Map<String, Object> map);
}
