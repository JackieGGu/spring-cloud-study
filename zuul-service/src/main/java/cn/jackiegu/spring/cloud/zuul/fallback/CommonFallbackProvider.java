package cn.jackiegu.spring.cloud.zuul.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 统一降级提供类
 *
 * @author JackieGu
 * @date 2021/8/27
 */
@Slf4j
@Component
public class CommonFallbackProvider implements FallbackProvider {

    private static final String COMMON_RESPONSE_BODY = "{\"code\":500,\"message\":\"请求失败\",\"data\":null}";

    @Override
    public String getRoute() {
        // *表示所有
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        log.warn("调用{}服务失败, 进行降级处理", route);
        return new ClientHttpResponse() {

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                MediaType type = new MediaType("application", "json", StandardCharsets.UTF_8);
                headers.setContentType(type);
                return headers;
            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(COMMON_RESPONSE_BODY.getBytes(StandardCharsets.UTF_8));
            }

            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return this.getStatusCode().value();
            }

            @Override
            public String getStatusText() throws IOException {
                return this.getStatusCode().getReasonPhrase();
            }

            @Override
            public void close() {

            }
        };
    }
}
