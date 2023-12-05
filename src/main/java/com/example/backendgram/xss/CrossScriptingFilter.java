package com.example.backendgram.xss;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter("/*")
public class CrossScriptingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 코드
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 필터 로직
        chain.doFilter(new RequestWrapper((HttpServletRequest) request), response);
    }

    @Override
    public void destroy() {
        // 필터 종료 시 처리
    }
}