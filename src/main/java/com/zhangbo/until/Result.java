package com.zhangbo.until;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Result implements Serializable {
    @ApiModelProperty(value = "状态码")
    private int code;
    @ApiModelProperty(value = "提示消息")
    private String msg;
    @ApiModelProperty(value = "返回数据")
    private Object data;
    /**.
     * TODO 消息提示
     * @param status***
     * @return com.api.pojo.Result
     */
    public static Result resultFactory(Status status){
        return new Result()
                .setCode(status.statusCode)
                .setMsg(status.statusMsg);
    }
    /**.
     * TODO 数据消息提示
     * @param status***
     * @param data***
     * @return com.api.pojo.Result
     */
    public static Result resultFactory(Status status, Object data){
        return new Result()
                .setCode(status.statusCode)
                .setMsg(status.statusMsg)
                .setData(data);
    }
    /**.
     * TODO 服务器错误
     * @param ***
     * @author: ShenWen
     */
    public static Result errorFactory(){
        return new Result()
                .setCode(Status.SERVER_FAIL.statusCode)
                .setMsg(Status.SERVER_FAIL.statusMsg);
    }
    /**
     * author: admin
     * methodName: lowAuthority
     * description: TODO lowAuthority方法 权限不够
     * createTime: 2022/2/18 11:05
     * @param
     * @return com.cqz.campus_lost_and_found.pojo.Result
     */
    public static Result lowAuthority(){
        return new Result()
                .setCode(Status.LOW_AUTHORITY.statusCode)
                .setMsg(Status.LOW_AUTHORITY.statusMsg);
    }
    /**
     * author: admin
     * methodName: parseJson
     * description: TODO parseJson方法 手动转json
     * createTime: 2022/2/18 11:50
     * @param
     * @return java.lang.String
     */
    @SneakyThrows
    public String parseJson(){
        return new ObjectMapper().writeValueAsString(this);
    }
    /**
     * author: admin
     * methodName: lowAuthority
     * description: TODO lowAuthority方法 自定返回结果
     * createTime: 2022/2/18 11:05
     * @param
     * @return com.cqz.campus_lost_and_found.pojo.Result
     */
    public static Result Auto(int code, String msg , Object data){
        return new Result()
                .setCode(code)
                .setMsg(msg)
                .setData(data);
    }
}

