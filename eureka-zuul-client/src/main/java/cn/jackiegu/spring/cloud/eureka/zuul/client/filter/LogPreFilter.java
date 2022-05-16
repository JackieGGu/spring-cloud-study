package cn.jackiegu.spring.cloud.eureka.zuul.client.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * 前置日志Filter
 *
 * @author JackieGu
 * @date 2021/8/27
 */
@Slf4j
@Component
public class LogPreFilter extends ZuulFilter {

    /**
     * 过滤器类型
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 优先级
     * 越小表示优先级越高
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 是否执行过滤器
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器执行逻辑
     */
    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        String servletPath = context.getRequest().getServletPath();
        log.info("servletPath: " + servletPath);
        return null;
    }
}
