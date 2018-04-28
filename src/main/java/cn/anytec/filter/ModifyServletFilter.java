package cn.anytec.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注意在程序主函数类添加@ServletComponentScan启动类扫描servlet组件，否则是不生效
 */

@WebFilter(urlPatterns = "/*")
public class ModifyServletFilter implements Filter{

    private static final Logger logger = LoggerFactory.getLogger(ModifyServletFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("====== 过滤器初始化 ======");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
