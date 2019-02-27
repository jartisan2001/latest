package com.github.jartisan.latest.configuration;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.github.jartisan.latest.global.utils.JwtUtil;

/***
 * token 过滤器
 * @author wjl
 */
public class TokenFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);
	
	private static final PathMatcher PATH_MATCHER = new AntPathMatcher();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("TokenFilter init...");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String token = request.getHeader("token");
		try {
            if(isProtectedUrl(request)) {
            	 //check jwt令牌, 如果令牌不合法或者过期, 里面会直接抛出异常, 下面的catch部分会直接返回
            	Map<String, Object> jwt =JwtUtil.parserJavaWebToken(token, "3136339");
            	log.info("jwt: {}",jwt);
            }
        } catch (Exception e) {
        	log.warn("[USER-API TOKEN 校验失败] >>> TOKEN:{}", token,e.getMessage());
        	((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        //如果jwt令牌通过了检测, 那么就把request传递给后面的RESTful api
        filterChain.doFilter(request, servletResponse);
	}

	/***
	 * 对地址 /api开头的api检查jwt. 避免登录/login也需要jwt
	 */
    private boolean isProtectedUrl(HttpServletRequest request) {
        return PATH_MATCHER.match("/api/**", request.getServletPath());
    }
    
	@Override
	public void destroy() {
		log.info("TokenFilter destroy...");
	}
}
