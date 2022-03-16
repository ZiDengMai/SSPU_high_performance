package entity;

import java.io.Serializable;

public class CommonResult <T> implements Serializable {
    private int code;
    private T data;
    public CommonResult(int code,T data){
        this.code=code;
        this.data=data;
    }
    public CommonResult(){

    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
