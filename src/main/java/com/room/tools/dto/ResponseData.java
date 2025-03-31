package com.room.tools.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.room.SysConst;
import lombok.Data;

/**
 * 通用的返回数据结构
 *
 * @param <T>
 */
@Data
public class ResponseData<T> {


    /**
     * 消息内容
     */
    @JsonProperty("Msg")
    public String Msg;


    /**
     * 返回数据
     */
    @JsonProperty("Data")
    public T Data;

    /**
     * 是否成功
     */
    @JsonProperty("Success")
    public Boolean Success;

    /**
     * 状态码
     */
    @JsonProperty("Code")
    public String Code;


    /**
     * 得到一个响应的实例
     *
     * @return
     */
    public static ResponseData GetResponseDataInstance(Object data, String msg, Boolean success) {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(success);
        responseData.setData(data);
        responseData.setMsg(msg);
        responseData.setCode(SysConst.STATUS_200);
        return responseData;
    }

    /**
     * 响应一个没有消息体成功
     *
     * @return
     */
    public static ResponseData OfSuccess() {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);
        responseData.setMsg("成功");
        responseData.setCode(SysConst.STATUS_200);
        return responseData;
    }

}
