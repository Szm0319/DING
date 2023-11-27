package com.example.ding.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI().substring(req.getContextPath().length());//在URI中过滤上下文路径
        // 检查会话中是否有用户，且请求不是登录页面或静态资源
        if (path.startsWith("/login") ||path.startsWith("/register")||path.startsWith("/toregister")||path.endsWith("/")) {
            chain.doFilter(request,response);
        }else{
            //检查session
            HttpSession session = req.getSession(false);//这里false的作用是不新建session
            if (session == null || session.getAttribute("USER") == null) {
                String encodeMessage = URLEncoder.encode("请登录后重试","UTF-8");
                res.sendRedirect(req.getContextPath() + "/login"+"?message="+encodeMessage);

            } else {
                chain.doFilter(request, response);
            }
        }

    }
}