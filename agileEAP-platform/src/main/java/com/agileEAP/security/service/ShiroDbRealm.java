/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.agileEAP.security.service;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.agileEAP.security.entity.Operator;
import com.agileEAP.security.service.AccountService;
import com.agileEAP.utils.*;

public class ShiroDbRealm extends AuthorizingRealm {

	protected AccountService accountService;

	/**
	 * 璁よ瘉鍥炶皟鍑芥暟,鐧诲綍鏃惰皟鐢�
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		Operator user = accountService.getByLoginName(token.getUsername());
		if (user != null) {
			byte[] salt = Encodes.decodeHex(user.getSalt());
			return new SimpleAuthenticationInfo(new ShiroUser(user.getId(),user.getUserType(), user.getLoginName(), user.getName(),user.getCorpID(),user.getCorpName(),user.getOrgID()),
					user.getPassword(), ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
	}

	/**
	 * 鎺堟潈鏌ヨ鍥炶皟鍑芥暟, 杩涜閴存潈浣嗙紦瀛樹腑鏃犵敤鎴风殑鎺堟潈淇℃伅鏃惰皟鐢�
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		Operator user = accountService.getByLoginName(shiroUser.loginName);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(user.getRoleList());
		return info;
	}

	/**
	 * 璁惧畾Password鏍￠獙鐨凥ash绠楁硶涓庤凯浠ｆ鏁�
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(AccountService.HASH_ALGORITHM);
		matcher.setHashIterations(AccountService.HASH_INTERATIONS);

		setCredentialsMatcher(matcher);
	}

	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	/**
	 * 鑷畾涔堿uthentication瀵硅薄锛屼娇寰桽ubject闄や簡鎼哄甫鐢ㄦ埛鐨勭櫥褰曞悕澶栬繕鍙互鎼哄甫鏇村淇℃伅.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public String id;
		public String loginName;
		public String name;
		public short userType;
		public String corpId;
		public String orgId;
		public String corpName;

		public ShiroUser(String id,short userType, String loginName, String name, String corpId, String corpName,String orgId) {
			this.id = id;
			this.userType = userType;
			this.loginName = loginName;
			this.name = name;
			this.corpId = corpId;
			this.corpName = corpName;
			this.orgId=orgId;
		}

		public String getName() {
			return name;
		}

		/**
		 * 鏈嚱鏁拌緭鍑哄皢浣滀负榛樿鐨�shiro:principal/>杈撳嚭.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 閲嶈浇equals,鍙绠條oginName;
		 */
		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this, "loginName");
		}

		/**
		 * 閲嶈浇equals,鍙瘮杈僱oginName
		 */
		@Override
		public boolean equals(Object obj) {
			return EqualsBuilder.reflectionEquals(this, obj, "loginName");
		}

		public String getId() {
			return id;
		}

		public String getLoginName() {
			return loginName;
		}

		public short getUserType() {
			return userType;
		}

		public String getCorpId() {
			return corpId;
		}

		public String getOrgId() {
			return orgId;
		}

		public String getCorpName() {
			return corpName;
		}
		
		public boolean IsAdmin()
		{
			return userType==0;
		}
	}
}

