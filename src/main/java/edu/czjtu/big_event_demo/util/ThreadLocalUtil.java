package edu.czjtu.big_event_demo.util;


import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocal 工具类，支持线程内共享 Map 结构的上下文数据
 */
public class ThreadLocalUtil {

    // 每个线程独立拥有一个 Map<String, Object> 存储多个值
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = ThreadLocal.withInitial(HashMap::new);

    // 设置值
    public static void set(String key, Object value) {
        THREAD_LOCAL.get().put(key, value);
    }

    // 获取值，泛型支持
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) THREAD_LOCAL.get().get(key);
    }

    public static String getUserName() {
        return (String) get("username");
    }

    public static Integer getUserId() {
        return (Integer) get("userId");
    }

    public static void setUserName(String username) {
        set("username", username);
    }

    public static void setUserId(Integer userId) {
        set("userId", userId);
    }

    // 获取整个 Map（可读但慎改）
    public static Map<String, Object> getMap() {
        return THREAD_LOCAL.get();
    }

    // 移除指定 key
    public static void remove(String key) {
        THREAD_LOCAL.get().remove(key);
    }

    // 清除整个线程上下文，防止内存泄漏（一定要记得在请求结束时调用！）
    public static void clear() {
        THREAD_LOCAL.remove();
    }
}