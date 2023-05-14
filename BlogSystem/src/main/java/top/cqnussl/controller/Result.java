package top.cqnussl.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author shisl
 * @date 2023/04/17
 */
@Data
@AllArgsConstructor
public class Result {

    /*返回的数据编码(是否成功)*/
    private Integer code;

    /*返回的数据*/
    private Object data;

    /*返回的数据提示消息*/
    private String msg;

    public Result(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }
}
