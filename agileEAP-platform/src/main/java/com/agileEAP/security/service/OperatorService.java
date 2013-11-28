package  com.agileEAP.security.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.agileEAP.security.entity.ObjectRole;
import com.agileEAP.security.entity.Operator;
import com.agileEAP.security.entity.RolePrivilege;
import com.agileEAP.security.repository.ObjectRoleRepository;
import com.agileEAP.security.repository.OperatorRepository;
import com.agileEAP.utils.*;

/**
 * 
 * @author trh
 */
@Component
@Transactional
public class OperatorService {
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	
    @Autowired
    private OperatorRepository operatorRepository;
    
    @Autowired
    private ObjectRoleRepository objectRoleRepository;
    

	public Operator get(String  id) {
		return operatorRepository.get(id);
    }

	public List<Operator> search(Map<String, Object> parameters) {
		return operatorRepository.search(parameters);
	}
        
    public PageDataResult searchByPage(Map<String, Object> parameters) {        
    	PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(operatorRepository.count(parameters));
        pageDataResult.setData(operatorRepository.searchByPage(parameters));
        
        return pageDataResult;
	}

	public void update(Operator operator) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		operator.setSalt(Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(operator.getPassword().getBytes(), salt, HASH_INTERATIONS);
		operator.setPassword((Encodes.encodeHex(hashPassword)));
		operatorRepository.update(operator);
	}

	public void save(Operator operator) {
		//登录密码加密
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		operator.setSalt(Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(operator.getPassword().getBytes(), salt, HASH_INTERATIONS);
		operator.setPassword((Encodes.encodeHex(hashPassword)));
		operatorRepository.save(operator);
	}
    
	public void  saveOperatorRole(String operatorId, List<ObjectRole> operatorRoles)
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("roleID", operatorId);
		
		List<ObjectRole> existRolePrivileges = objectRoleRepository
				.search(parameters);
		List<String> ids = new ArrayList<>();
		for (ObjectRole objectRole : existRolePrivileges) {
			ids.add(objectRole.getId());
		}

		if (ids.size() > 0)
			objectRoleRepository.batchDelete(ids);

		for (ObjectRole operatorRole : operatorRoles) {
			objectRoleRepository.save(operatorRole);
		}		
	}
	
	public void delete(String  id) {
		operatorRepository.delete(id);
	}
    public void batchDelete(List<String> ids) {
		operatorRepository.batchDelete(ids);
	}

	public void updatePwd(String id, String password) {
		byte[] newSalt = Digests.generateSalt(SALT_SIZE);
		String salt = Encodes.encodeHex(newSalt);
		
		byte[] hashPassword = Digests.sha1(password.getBytes(), newSalt, HASH_INTERATIONS);
		String loginPassword = Encodes.encodeHex(hashPassword);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("password", loginPassword);
		map.put("salt", salt);
		
		try {
			operatorRepository.updatePwd(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
