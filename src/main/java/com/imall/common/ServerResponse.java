package com.imall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 *
 * author:  ywy
 * date:  2018-07-24
 * desc:
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
// 保證序列化json如果是null对象key也会消失
public class ServerResponse<T> implements Serializable {

    private int status;
    
    private String msg;

    private T data;

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    // 使之不出现在 json序列化中
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccessMessage(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg, T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String msg){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),msg);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode ,String errorMsg){
        return new ServerResponse<T>(errorCode,errorMsg);
    }

    public static void main(String[] args) {
        ServerResponse sr1 = new ServerResponse(1,new Object());
        ServerResponse sr2 = new ServerResponse(1,"abc");

        System.out.println("console");
    }
}