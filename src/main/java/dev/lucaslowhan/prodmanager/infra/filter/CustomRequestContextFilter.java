package dev.lucaslowhan.prodmanager.infra.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomRequestContextFilter implements Filter {

    public static final String MDC_REQUEST_ID = "requestId";
    public static final String MDC_USER = "user";
    public static final String MDC_IP = "ip";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestId = Optional.ofNullable(request.getHeader("X-Request-Id"))
                .filter(h -> !h.isBlank())
                .orElse(UUID.randomUUID().toString());

        try{
            MDC.put(MDC_REQUEST_ID, requestId);
            MDC.put(MDC_IP, request.getRemoteAddr());

            try{
                var auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
                MDC.put(MDC_USER, auth != null && auth.isAuthenticated()? auth.getName() : "anonymous");
            }catch (Exception e) {
                MDC.put(MDC_USER, "anonymous");
            }
            filterChain.doFilter(servletRequest,servletResponse);
        }finally {
            MDC.clear();
        }
    }
}
