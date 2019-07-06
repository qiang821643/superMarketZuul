package com.zuul.model;

import lombok.Data;

/**
 * @author: create QiangShW
 * @version: v1.0
 * @description: com.zuul.model
 * @date:2019/7/6
 **/
@Data
public class BuildResult<T> {

    public static <T>Result<T> build(Integer code,T t){
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMsg(code==200?"success":"error");
        result.setDate(t);
        return result;
    }

    public static <T>Result<T> build(Integer code,String msg,T t){
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMsg(msg);
        result.setDate(t);
        return result;
    }
}

@Data
class Result<T>{
    private Integer code;

    private String msg;

    private T date;
}