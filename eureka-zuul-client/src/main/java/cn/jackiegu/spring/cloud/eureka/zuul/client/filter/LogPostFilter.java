package cn.jackiegu.spring.cloud.eureka.zuul.client.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 后置日志Filter
 *
 * @author JackieGu
 * @date 2021/8/27
 */
@Slf4j
@Component
public class LogPostFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        try {
            RequestContext requestContext = RequestContext.getCurrentContext();
            InputStream stream = requestContext.getResponseDataStream();
            byte[] bytes = StreamUtils.copyToByteArray(stream);
            ByteArrayInputStream logInputStream = new ByteArrayInputStream(bytes);
            log.info("responseBody: " + StreamUtils.copyToString(logInputStream, StandardCharsets.UTF_8));
            ByteArrayInputStream responseInputStream = new ByteArrayInputStream(bytes);
            requestContext.setResponseDataStream(responseInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
