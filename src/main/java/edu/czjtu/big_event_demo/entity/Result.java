package edu.czjtu.big_event_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用响应结果封装类，用于统一接口返回结构。
 *
 * @param <T> 响应数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /** 成功状态码 */
    public static final int SUCCESS_CODE = 0;

    /** 错误状态码 */
    public static final int ERROR_CODE = 1;

    /** 响应状态码：0 - 成功，1 - 失败 */
    private Integer code;

    /** 提示信息 */
    private String message;

    /** 响应数据 */
    private T data;

    /**
     * 返回成功结果（带数据）
     *
     * @param <E>  数据类型
     * @param data 响应数据
     * @return 成功的 Result 对象
     */
    public static <E> Result<E> success(E data) {
        return new Result<>(SUCCESS_CODE, "操作成功", data);
    }

    /**
     * 返回成功结果（无数据）
     *
     * @param <E> 泛型类型
     * @return 成功的 Result 对象
     */
    public static <E> Result<E> success() {
        return new Result<>(SUCCESS_CODE, "操作成功", null);
    }

    /**
     * 返回带自定义消息的成功结果
     *
     * @param <E>     数据类型
     * @param message 自定义成功提示信息
     * @param data    响应数据
     * @return 成功的 Result 对象
     */
    public static <E> Result<E> success(String message, E data) {
        return new Result<>(SUCCESS_CODE, message, data);
    }

    /**
     * 返回失败结果（仅错误消息）
     *
     * @param <E>     泛型类型
     * @param message 错误提示信息
     * @return 失败的 Result 对象
     */
    public static <E> Result<E> error(String message) {
        return new Result<>(ERROR_CODE, message, null);
    }

    /**
     * 返回失败结果（带数据）
     *
     * @param <E>     数据类型
     * @param message 错误提示信息
     * @param data    响应数据
     * @return 失败的 Result 对象
     */
    public static <E> Result<E> error(String message, E data) {
        return new Result<>(ERROR_CODE, message, data);
    }
}
