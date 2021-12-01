package com.zara.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : ResultDTO
 * @description : [固定返回格式]
 * @createTime : [2021/11/30 22:34]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/11/30 22:34]
 * @updateRemark : [描述说明本次修改内容]
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO<T> implements Serializable {
    //状态码
    private Integer code;

    //提示信息
    private String message;

    //具体的内容
    private T data;

}
