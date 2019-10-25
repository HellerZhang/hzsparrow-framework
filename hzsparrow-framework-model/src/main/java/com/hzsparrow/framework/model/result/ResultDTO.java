package com.hzsparrow.framework.model.result;

import com.hzsparrow.framework.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.xml.transform.Result;

@Getter
@Setter
public class ResultDTO<T> extends BaseModel {

    /**
     * 请求成功标志
     */
    private boolean success = true;

    /**
     * 请求返回码
     */
    private Integer code = 200;

    /**
     * 请求返回消息
     */
    private String msg;

    /**
     * 请求返回数据
     */
    private T data;

    /**
     * 获取操作成功返回数据
     *
     * @param <RT> 返回数据类型
     * @return
     */
    public static <RT> ResultDTO<RT> getOptionSuccess(){
        return new ResultDTO<RT>();
    }

    /**
     * 获取查询数据成功返回数据
     *
     * @param data 返回数据
     * @param <RT> 返回数据类型
     * @return
     */
    public static <RT> ResultDTO<RT> getDataSuccess(RT data){
        ResultDTO<RT> rt = new ResultDTO<RT>();
        rt.setData(data);
        return rt;
    }

    /**
     * 获取操作失败返回数据
     *
     * @param code 失败编号
     * @param msg  失败信息
     * @param <RT>  返回数据类型
     * @return
     */
    public static <RT> ResultDTO<RT> getOptionFaild(Integer code,String msg){
        ResultDTO<RT> rt = new ResultDTO<RT>();
        rt.setCode(code);
        rt.setMsg(msg);
        rt.setSuccess(false);
        return rt;
    }

    /**
     * 获取查询数据失败返回数据
     *
     * @param code 失败编号
     * @param msg 失败信息
     * @param data 返回数据
     * @param <RT> 返回数据类型
     * @return
     */
    public static <RT> ResultDTO<RT> getDataFaild(Integer code,String msg,RT data){
        ResultDTO<RT> rt = new ResultDTO<RT>();
        rt.setCode(code);
        rt.setMsg(msg);
        rt.setSuccess(false);
        rt.setData(data);
        return rt;
    }
}
