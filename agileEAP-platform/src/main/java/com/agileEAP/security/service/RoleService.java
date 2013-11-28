package com.agileEAP.security.service;

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
import com.agileEAP.security.entity.Role;
import com.agileEAP.security.entity.RolePrivilege;
import com.agileEAP.security.repository.RolePrivilegeRepository;
import com.agileEAP.security.repository.RoleRepository;
import com.agileEAP.utils.*;

/**
 * 
 * @author trh
 */
@Component
@Transactional
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RolePrivilegeRepository rolePrivilegeRepository;

	public Role get(String id) {
		return roleRepository.get(id);
	}

	public List<Role> search(Map<String, Object> parameters) {
		return roleRepository.search(parameters);
	}

	public List<String> getRolePrivileges(String roleId) {
		return roleRepository.getRolePrivileges(roleId);
	}
	
	public PageDataResult searchByPage(Map<String, Object> parameters) {
		PageDataResult pageDataResult = new PageDataResult();
		pageDataResult.setTotal(roleRepository.count(parameters));
		pageDataResult.setData(roleRepository.searchByPage(parameters));

		return pageDataResult;
	}

	public void update(Role role) {
		roleRepository.update(role);
	}

	public void save(Role role) {
		roleRepository.save(role);
	}

	public void saveRolePrivilege(String roleId, List<RolePrivilege> rolePrivileges) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("roleID", roleId);
		
		List<RolePrivilege> existRolePrivileges = rolePrivilegeRepository.search(parameters);
		List<String> ids = new ArrayList<>();
		for (RolePrivilege rolePrivilege : existRolePrivileges) {
			ids.add(rolePrivilege.getId());
		}

		if (ids.size() > 0)
			rolePrivilegeRepository.batchDelete(ids);
		

		for (RolePrivilege rolePrivilege : rolePrivileges) {
			rolePrivilegeRepository.save(rolePrivilege);
		}
	}
	
	public List<String> getOperatorRoles(String operatorId) {
		return roleRepository.getOperatorRoles(operatorId);
	}

	public void delete(String id) {
		roleRepository.delete(id);
	}

	public void batchDelete(List<String> ids) {
		roleRepository.batchDelete(ids);
	}
}
