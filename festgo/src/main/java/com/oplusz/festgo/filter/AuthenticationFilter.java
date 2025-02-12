package com.oplusz.festgo.filter;

import java.io.IOException;
import java.net.URLEncoder;

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
public class AuthenticationFilter extends HttpFilter {
	
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public AuthenticationFilter() {}

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
    	Object signedInUser = session.getAttribute("signedInUser");
    	
    	if(signedInUser != null && !signedInUser.equals("")) {
    		log.debug("로그인 중 : {}", signedInUser);
    		
    		chain.doFilter(request, response);
    		return;
    	}
    	
    	log.debug("로그아웃 상태 : 로그인 모달 띄우기!");
    	// 로그인 필요 플래그 설정
    	String contextpath = httpReq.getContextPath();
    	String url = httpReq.getRequestURL().toString();
    	String qs = httpReq.getQueryString();
    	String redirectAfterLogin = null;
    	log.debug("{}, {}, {}", url, contextpath, qs);
    	
    	if(qs == null) redirectAfterLogin = URLEncoder.encode(url, "UTF-8");
    	else redirectAfterLogin = URLEncoder.encode(url + "?" + qs, "UTF-8");
    	
        httpReq.setAttribute("loginRequired", true);
        session.setAttribute("redirectAfterLogin", redirectAfterLogin);
        httpReq.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
        
    		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}

//
