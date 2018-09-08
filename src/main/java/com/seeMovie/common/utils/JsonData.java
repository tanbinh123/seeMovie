package com.seeMovie.common.utils;

import java.io.Serializable;

/**
 * @author      mym
 * @version     V1.0
 * @project     SeeMovie
 * @date        2018-08-28 20:35
 * @describe    封装返回信息的实体类
 */
public class JsonData implements Serializable {
    /**
     * 返回的信息
    */
    private String msg;
    /**
     * 返回的状态码
     */
    private String returnCode;
    /**
     * 返回Object
     */
    private Object object;
    /**
     * 执行状态
    */
    private boolean status = false;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}