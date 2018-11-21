package com.hw.springbootrestful.web.filter;

import com.hw.springbootrestful.utils.RedisCacheUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/21 12:10
 * @Version 1.0
 */
@WebFilter(filterName = "xssFilter", urlPatterns = "/*", asyncSupported = true)
public class XssFilter implements Filter {
    private Logger logger = LogManager.getLogger(XssFilter.class);


    private RedisCacheUtil redisCacheUtil;


    public XssFilter(RedisCacheUtil redisCacheUtil) {
        this.redisCacheUtil = redisCacheUtil;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        XssHttpServletRequestWrapper xssRequest =
                new XssHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        // TODO: 2018/7/12 0012 根据IP限制请求
//        String redisKey = "XssFilterRequestLimit\t" + IPUtil.getRealIP(xssRequest) + "\t" + xssRequest.getRequestURI();
//        long count = redisCacheUtil.getIncrement(redisKey);
//        if (count > 10) {
//            logger.error("【请求次数超过限制】" + redisKey);
//            throw new ServletException("Flowing Limit.");
//        }
//        redisCacheUtil.expire(redisKey, 60, TimeUnit.SECONDS);
        filterChain.doFilter(xssRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
