/**
 *
 */
package com.noah.mapi.filter;

import com.noah.mapi.config.GlobalConfiguration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author larry
 * @email larry.lv.word@gmail.com
 */
public class FilterArgs implements Filter {

    private String allow = "*";

    protected FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        // 本过滤器默认编码是UTF-8，但也可以在web.xml配置文件里设置自己需要的编码
        if (filterConfig.getInitParameter("allow") != null) {
            allow = filterConfig.getInitParameter("allow");
        }
    }

    public void doFilter(ServletRequest srequset, ServletResponse sresponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) srequset;
        HttpServletResponse response = (HttpServletResponse) sresponse;
        // 设置编码
        String encoding = GlobalConfiguration.charset.toString();

        request.setCharacterEncoding(encoding);
        // 跨域
        response.setHeader("Access-Control-Allow-Origin", allow);

        try {
            filterChain.doFilter(srequset, sresponse);
        } catch (Exception e) {

        }
    }

    @Override
    public void destroy() {

    }

}