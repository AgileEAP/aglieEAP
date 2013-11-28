package com.agileEAP.security.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agileEAP.security.entity.Operator;
import com.agileEAP.security.repository.OperatorRepository;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;
import com.agileEAP.utils.*;

/**
 * 更高效的AccountService实现，基于MyBatis + Memcached的方案，以JSON格式存储Memcached中的内容。
 * 
 * @author trh
 */
@Component
@Transactional(readOnly = true)
public class AccountService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private DateProvider dateProvider = DateProvider.DEFAULT;
	
	private OperatorRepository operatorRepository;
	
/*	@Autowired
	public void setOperatorRepository(OperatorRepository operaterRepository) {
		this.operaterRepository = operaterRepository;
	}*/
	
	@Autowired
	public void setOperatorRepository(OperatorRepository operatorRepository) {
		this.operatorRepository = operatorRepository;
	}

	public void setDateProvider(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}

	/*
	 * @Autowired private SpyMemcachedClient memcachedClient;
	 */
	// private final JsonConvert jsonConvert = JsonConvert.nonDefaultMapper();

	/**
	 * 先访问Memcached, 使用JSON字符串存放对象以节约空间.
	 */
	public Operator get(String id) {
		// String key = MemcachedObjectType.operater.getPrefix() + id;

		Operator operater = null;
		// String jsonString = memcachedClient.get(key);

		/*
		 * if (jsonString == null) { operater = operaterDao.get(id); if (operater != null) {
		 * jsonString = JsonConvert.toJson(operater); memcachedClient.set(key,
		 * MemcachedObjectType.operater.getExpiredTime(), jsonString); } } else {
		 * operater = jsonConvert.fromJson(jsonString, operater.class); }
		 */

		operater = operatorRepository.get(id);
		return operater;
	}

	public Operator getByLoginName(String loginName) {
		return operatorRepository.getByLoginName(loginName);
	}


	@Transactional(readOnly = false)
	public void update(Operator operator) {
		if (StringUtils.isNotBlank(operator.getPassword())) {
			Operator oldOperator=operatorRepository.get(operator.getId());
			if(!oldOperator.getPassword().equals(operator.getPassword()))
			{
				entryptPassword(operator);
			}
		}
		operatorRepository.update(operator);
	}

	public void entryptPassword(Operator operater) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		operater.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(operater.getPassword().getBytes(),
				salt, HASH_INTERATIONS);
		operater.setPassword(Encodes.encodeHex(hashPassword));
	}

	@Transactional(readOnly = false)
	public void register(Operator operater) {
		entryptPassword(operater);
		operater.setCreateTime(dateProvider.getDate());
		operatorRepository.save(operater);
	}

	@Transactional
	public void delete(String id) {
		operatorRepository.delete(id);
	}
}
