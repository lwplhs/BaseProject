package com.lwp.website.entity.Bo;


import com.alibaba.fastjson.JSONObject;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2019/12/22/15:18
 * @Description: rest返回对象
 */
public class RestResponseBo<T> {
    /**
     * 服务器响应数据
     */
    private T payload;

    /**
     * 请求是否成功
     */
    private boolean success;

    /**
     * 状态信息
     */
    private String msg;

    /**
     * 状态码 -1 错误
     * 1 成功
     */
    private int code = -1;

    /**
     * 服务器响应时间
     */
    private long timestamp;

    public RestResponseBo(){
        this.timestamp = System.currentTimeMillis()/1000;
    }

    public RestResponseBo(boolean success){
        this.timestamp = System.currentTimeMillis()/1000;
        this.success = success;
    }

    public RestResponseBo(boolean success, T payload){
        this.timestamp = System.currentTimeMillis();
        this.success = success;
        this.payload = payload;
    }

    public RestResponseBo(boolean success, T payload, int code){
        this.timestamp = System.currentTimeMillis()/1000;
        this.success = success;
        this.payload = payload;
        this.code = code;
    }
    public RestResponseBo(boolean success, T payload, String msg, int code){
        this.timestamp = System.currentTimeMillis()/1000;
        this.success = success;
        this.payload = payload;
        this.msg = msg;
        this.code = code;
    }

    public RestResponseBo(boolean success, String msg){
        this.timestamp = System.currentTimeMillis();
        this.success = success;
        this.msg = msg;
    }

    public RestResponseBo(boolean success, String msg, int code){
        this.timestamp = System.currentTimeMillis();
        this.success = success;
        this.msg = msg;
        this.code = code;
    }

    public T getPayload(){
        return payload;
    }

    public void setPayload(T payload){
        this.payload = payload;
    }

    public boolean isSuccess(){
        return success;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public int getCode(){
        return code;
    }

    public void setCode(int code){
        this.code = code;
    }

    public long getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }

    public static RestResponseBo ok(){
        return new RestResponseBo(true);
    }
    public static RestResponseBo ok(String result){
        int c = -1;
        String m = "请求失败";
        try{
            JSONObject jsonObject = JSONObject.parseObject(result);
            c = jsonObject.getInteger("code");
            m = jsonObject.getString("msg");
            return new RestResponseBo(true,m,c);
        }catch (Exception e){
            e.printStackTrace();
            return new RestResponseBo(false,m,c);
        }
    }

    public static <T> RestResponseBo ok(T payload){
        return new RestResponseBo(true,payload);
    }

    public static <T> RestResponseBo ok(int code){
        return new RestResponseBo(true,null,code);
    }

    public static <T> RestResponseBo ok(int code,String msg){
        return new RestResponseBo(true,msg,code);
    }

    public static <T> RestResponseBo ok(Object code,Object msg){
        String m = "请求失败";
        int c = -1;
        try {
            m = msg.toString();
            c = Integer.parseInt(code.toString());
            return new RestResponseBo(true,m,c);
        }catch (Exception e){
            e.printStackTrace();
            return new RestResponseBo(false,m,c);
        }
    }

    public static <T> RestResponseBo ok(T payload,int code){
        return new RestResponseBo(true,payload,code);
    }

    public static <T> RestResponseBo ok(T payload,int code,String msg){
        return new RestResponseBo(true,payload,msg,code);
    }

    public static RestResponseBo fail(){
        return new RestResponseBo(false);
    }

    public static RestResponseBo fail(String msg){
        return new RestResponseBo(false,msg);
    }

    public static RestResponseBo fail(int code){
        return new RestResponseBo(false,null,code);
    }

    public static RestResponseBo fail(int code,String msg){
        return new RestResponseBo(false,msg,code);
    }
}
