package cn.jackiegu.spring.cloud.eureka.zuul.client.provider;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 服务降级提供类
 *
 * @author JackieGu
 * @date 2021/8/27
 */
@Component
public class CommonFallbackProvider implements FallbackProvider {

    private static final String COMMON_RESPONSE_BODY = "{\"code\":500,\"msg\":\"失败，服务降级\"}";

    @Override
    public String getRoute() {
        // 表示针对所有路由的服务
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {

            @Override
            public HttpStatus getStatusCode() {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() {
                return this.getStatusCode().value();
            }

            @Override
            public String getStatusText() {
                return this.getStatusCode().getReasonPhrase();
            }

            @Override
            @SuppressWarnings("deprecation")
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }

            @Override
            public InputStream getBody() {
                return new ByteArrayInputStream(COMMON_RESPONSE_BODY.getBytes(StandardCharsets.UTF_8));
            }

            @Override
            public void close() {

            }
        };
    }
}
