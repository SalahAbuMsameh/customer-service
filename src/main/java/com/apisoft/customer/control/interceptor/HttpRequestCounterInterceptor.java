package com.apisoft.customer.control.interceptor;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * class docs.
 *
 * @author Salah Abu Msameh
 * @since 2/28/2020
 */
@Component
public class HttpRequestCounterInterceptor implements HandlerInterceptor {

    private Counter successRequestCounter;
    private Counter badRequestCounter;
    private Counter failedRequestCounter;
    private Counter othersRequestCounter;

    /**
     * constructor.
     * @param registry
     */
    public HttpRequestCounterInterceptor(final MeterRegistry registry) {
        successRequestCounter = registry.counter("http_success_requests_count");
        badRequestCounter = registry.counter("http_bad_requests_count");
        failedRequestCounter = registry.counter("http_failed_requests_count");
        othersRequestCounter = registry.counter("http_other_requests_count");
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
                                final Object handler, final Exception ex) throws Exception {

        HttpStatus responseStatus = HttpStatus.valueOf(response.getStatus());

        if(responseStatus.is2xxSuccessful()) {
            successRequestCounter.increment(Math.random() * 1000);
        } else if(responseStatus.is4xxClientError()) {
            badRequestCounter.increment(Math.random() * 10);
        } else if(responseStatus.is4xxClientError()) {
            failedRequestCounter.increment();
        } else {
            othersRequestCounter.increment();
        }
    }
}
