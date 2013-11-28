package com.agileEAP.security.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.agileEAP.security.service.ResourceService;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;

@Component("actionFilter")  
public class ActionFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(ActionFilter.class);
	    
	@Resource(name="resourceService")  
	private ResourceService resourceService;

	    @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,ServletException {
			ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

			if(user!=null)
			{
				HttpServletRequest servletRequest=(HttpServletRequest)request;
				String requestURL=servletRequest.getServletPath();
				requestURL=requestURL.substring(1);
				String entry=servletRequest.getParameter("entry");
				
				com.agileEAP.security.entity.Resource resource=resourceService.getAuthorizedResource(user, requestURL, entry);

				if (resource!=null) {
					request.setAttribute("showToolBar", resource.getShowToolBar());
					request.setAttribute("showNavigation", resource.getShowNavigation());
					request.setAttribute("navigationTitle", resource.getNavigationTitle());
					request.setAttribute("operates", resource.getOperates());
				}
			}
			
	        chain.doFilter(request, response);
	    }

	    @Override
	    public void init(FilterConfig fc) throws ServletException {
	    	logger.info("(init the  action filter)");
	    }

	    @Override
	    public void destroy() {
	    	logger.info("(destroy the  action filter)");
	    }
}
