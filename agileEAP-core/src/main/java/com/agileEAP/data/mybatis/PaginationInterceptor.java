package com.agileEAP.data.mybatis;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map;

import javax.xml.bind.PropertyException;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.ResultHandler;
import java.sql.Statement;
import org.apache.log4j.Logger;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationInterceptor implements Interceptor {
	// 日志对象
	protected static Logger log = Logger.getLogger(PaginationInterceptor.class);
	public static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	public static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

	private static String dialect = "oracle";
	private static String sqlPattern = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin
	 * .Invocation)
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation
				.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(
				statementHandler, DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY);// invocation.getArgs()[0];
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler
				.getValue("delegate.mappedStatement");
		BoundSql boundSql = (BoundSql) statementHandler.getBoundSql();

		if (boundSql.getParameterObject() instanceof Map<?, ?>) {
			@SuppressWarnings("unchecked")
			Map<String, Object> parameterMap = (Map<String, Object>) (boundSql
					.getParameterObject());
			parameterMap.put("dialect", dialect.toLowerCase());

			if (mappedStatement.getId().matches(sqlPattern)) { // 拦截需要分页的SQL
				String originalSql = boundSql.getSql();

				if (!(originalSql.indexOf(",rownum rn") > 0)) {
					int pageIndex = (int) parameterMap.get("page");
					int pageSize = (int) parameterMap.get("pageSize");

					Object orderby = parameterMap.get("#orderby");
					if (orderby != null) {
						originalSql = originalSql + " order by " + orderby;
					}

					Dialect.Type databaseType = Dialect.Type.valueOf(dialect
							.toUpperCase());
					Dialect dialect = null;
					switch (databaseType) {
					case ORACLE:
						dialect = new OracleDialect();
						break;
					case DB2:
						dialect = new DB2Dialect();
						break;
					case MYSQL:
						dialect = new MysqlDialect();
						break;
					case MSSQL58:
						dialect = new Mssql58Dialect();
						break;
					case MSSQL12:
						dialect = new Mssql12Dialect();
						break;
					default:
						break;
					}
					metaStatementHandler.setValue(
							"delegate.boundSql.sql",
							dialect.getLimitString(originalSql, (pageIndex - 1)
									* pageSize, pageSize));
					metaStatementHandler.setValue("delegate.rowBounds.offset",
							RowBounds.NO_ROW_OFFSET);
					metaStatementHandler.setValue("delegate.rowBounds.limit",
							RowBounds.NO_ROW_LIMIT);
					if (log.isDebugEnabled()) {
						log.debug("生成分页SQL : "
								+ statementHandler.getBoundSql().getSql());
					}
				}
			}
		}
		return invocation.proceed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	@Override
	public Object plugin(Object target) {
		// return Plugin.wrap(target, this);
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 */
	@Override
	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		if (dialect == null || dialect.equals("")) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
		sqlPattern = p.getProperty("sqlPattern");
		if (sqlPattern == null || sqlPattern.equals("")) {
			try {
				throw new PropertyException("sqlPattern property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}

	}

}