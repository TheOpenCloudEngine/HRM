package org.opencloudengine.garuda.web.security;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by uengine on 2016. 4. 22..
 */
public class CorsFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "DELETE, HEAD, GET, OPTIONS, POST, PUT");
        response.setHeader("Access-Control-Max-Age", "1728000");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, accept, " +
//                "management-key, management-secret, client-key, client-secret, Content-Type, Content-Range, Content-Disposition, Content-Description");
        response.setHeader("Access-Control-Allow-Headers", "*");
        chain.doFilter(req, res);
    }
    @Override
    public void destroy() {

    }
}
