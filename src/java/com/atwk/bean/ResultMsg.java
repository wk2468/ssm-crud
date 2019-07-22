package com.atwk.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用的返回信息的类
 */
public class ResultMsg {
    /** 状态码 */
    private String code;
    /** 提示信息 */
    private String msg;
    /** 用户要返回给浏览器的数据 */
    private Map<String,Object> extend = new HashMap<String,Object>();

    /**
     * 处理成功的方法
     * @return ResultMsg
     */
    public static ResultMsg succsee(){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setCode("100");
        resultMsg.setMsg("处理成功！");
        return resultMsg;
    }

    /**
     * 处理失败的方法
     * @return ResultMsg
     */
    public static ResultMsg fail(){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setCode("200");
        resultMsg.setMsg("处理失败！");
        return resultMsg;
    }

    /**
     * 添加返回信息的方法
     * @param key
     * @param value
     * @return ResultMsg
     */
    public ResultMsg add(String key,Object value){
        this.extend.put(key,value);
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }
}
