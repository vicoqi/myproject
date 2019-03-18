package com.vic.chain;


import java.io.Serializable;

public class CustomResult<T> implements Serializable {

    private static final long serialVersionUID = -7003210754311840835L;
    /**
     * 返回结果
     */
    private T data;
    /**
     * 错误code，code为0表示成功
     */
    private int code;
    /**
     * 错误message
     */
    private String message;

    public CustomResult() {
        this(null, 0, null);
    }

    public CustomResult(T data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public CustomResult(T resultContent) {
        this(resultContent, 0, null);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return this.code == 0;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
