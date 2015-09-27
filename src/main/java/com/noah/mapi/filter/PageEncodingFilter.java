package com.noah.mapi.filter;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by noahli on 2015/2/10.
 */
public class PageEncodingFilter implements Filter {

    private String encoding = "UTF-8";
    protected FilterConfig filterConfig;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        // 本过滤器默认编码是UTF-8，但也可以在web.xml配置文件里设置自己需要的编码
        if (filterConfig.getInitParameter("encoding") != null) {
            encoding = filterConfig.getInitParameter("encoding");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        // 必须否则前台会收到乱码
        response.setContentType("text/html;charset=".concat(encoding));
        //
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        this.encoding = null;
    }
}
