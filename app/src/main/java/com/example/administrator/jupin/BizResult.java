package com.example.administrator.jupin;


import java.util.List;

/**
 * <p>返回代码封装</p>
 *
 * @author chenzhaoju
 */
public class BizResult<T> {

    private String retCode; //返回的操作码。可以不用管
    private String msg; //返回消息提示
    private boolean succ; //是否成功操作

    /*
     //{"status" : "300", "message" : "用户未登录或超时退出，请重新登陆！"}
     */
    private String status; //状态码。
    private String message;  //status=300时，有值

    private T item; //具体业务的单个对象
    private List<T> items; //具体业务的对象集合


    /**
     * 获取服务器返回的操作提示
     * @return
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 是否为成功的结果
     * @return
     */
    public boolean isSucc(){
        return succ;
    }

    /**
     * 是否为错误的结果
     * @return
     */
    public boolean isFail(){
        return !succ;
    }


    public String getRetCode() {
        return retCode;
    }

    /**
     * 返回{@link #getStatus()}=300时的消息
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * 状态码
     * @return
     */
    public String getStatus() {
        return status;
    }



    public void setMessage(String message) {
        this.message = message;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public void setSucc(boolean succ) {
        this.succ = succ;
    }




}