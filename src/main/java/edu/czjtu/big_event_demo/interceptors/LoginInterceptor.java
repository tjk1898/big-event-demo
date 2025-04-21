package edu.czjtu.big_event_demo.interceptors;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import edu.czjtu.big_event_demo.entity.Result;
import edu.czjtu.big_event_demo.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.debug("preHandle起作用了。");
        String tokenString = request.getHeader("Authorization");
        if (tokenString == null || tokenString.isEmpty()) {
            response.sendError(401, "请先登录");
            log.debug("用户没有登录");
            return false;
        } else {
            try {
                log.debug("开始校验token");
                Map<String, Object> tokenMap = JWTUtil.parseToken(tokenString);
                return true;
            } catch (Exception e) {
                response.setContentType("application/json;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(Result.error("请先登录").toString());
                return false;
            }
        }
    }
}
