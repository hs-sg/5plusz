package com.oplusz.festgo.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@Slf4j
public class RoleFilter extends HttpFilter {
	
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public RoleFilter() {}

	/**
	 * @see Filter#destroy()
	 */
    @Override
	public void destroy() {
		
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    	HttpServletRequest httpReq = (HttpServletRequest)request;
    	HttpServletResponse httpResp = (HttpServletResponse)response;
    	HttpSession session = httpReq.getSession();
    	
    	String memberRole = session.getAttribute("memberRole").toString();
    	Enumeration<String> att = session.getAttributeNames();
    	while(att.hasMoreElements()) {
    		log.debug(att.nextElement());
    	}
    	
    	log.debug("권한번호 : {}", memberRole);
    	
    	if(memberRole.equals("2") || memberRole.equals("3")) {
    		log.debug("접근 가능한 권한입니다");
    		chain.doFilter(request, response);
    		return;
    	}
    	
    	log.debug("접근 불가능한 권한입니다 : 권한없을 알림 띄우기");
    	
    	httpReq.setAttribute("roleRequired", true);
    	httpReq.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}

//
