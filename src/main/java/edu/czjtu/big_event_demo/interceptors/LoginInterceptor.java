package edu.czjtu.big_event_demo.interceptors;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.czjtu.big_event_demo.entity.Result;
import edu.czjtu.big_event_demo.util.JWTUtil;
import edu.czjtu.big_event_demo.util.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private static final ObjectMapper mapper = new ObjectMapper();

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
                ThreadLocalUtil.setUserId((Integer) tokenMap.get("userId"));
                ThreadLocalUtil.setUserName((String) tokenMap.get("username"));
                return true;
            } catch (Exception e) {
                response.setContentType("application/json;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(mapper.writeValueAsString(Result.error("请先登录")));
                return false;
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.clear();  // 请求完成后清理数据
    }
}
