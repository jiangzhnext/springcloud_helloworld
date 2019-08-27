package com.next.springcloud.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyPreFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    // 业务逻辑实现
    @Override
    public Object run() throws ZuulException {
        // 从RequestContext中可以获取独立的数据- 》 theadlocal
        // 处理哪些业务逻辑
        RequestContext requestContext = RequestContext.getCurrentContext();

        HttpServletRequest request = requestContext.getRequest();
        String tokenStr = request.getHeader("token");
        if(tokenStr==null || tokenStr.trim().length()==0){
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(500);
            try {
                PrintWriter out = requestContext.getResponse().getWriter();
                out.write("JWT must be required!");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
