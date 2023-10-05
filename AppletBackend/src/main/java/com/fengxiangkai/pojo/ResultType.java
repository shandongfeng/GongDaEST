package com.fengxiangkai.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultType<T> {
    private int statusCode;
    private String errMsg;
    private T data;

    public static <T> ResultType<T> success(T data) {
        return new ResultType<>(200, "成功", data);
    }

    public static <T> ResultType<T> error(T data) {
        return new ResultType<>(404, "失败", data);
    }
}
