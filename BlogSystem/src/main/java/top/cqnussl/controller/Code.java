package top.cqnussl.controller;

/**
 * @author shisl
 * @date 2023/04/17
 */
public class Code {

    /**
     * 响应编码
     *
     * 10011-用户已登录
     * 10010-用户未登录
     * 10021-账号可用
     * 10020-账号不可用
     *
     * 20011-添加成功
     * 20021-删除成功
     * 20031-修改成功
     * 20041-查询失败
     *
     * 20010-添加失败
     * 20020-删除失败
     * 20030-修改失败
     * 20040-查询失败
     *
     * */
    public static final Integer LOGIN_OK = 10011;
    public static final Integer LOGIN_ERR = 10010;
    public static final Integer ACCOUNT_OK = 10021;
    public static final Integer ACCOUNT_ERR = 10020;

    public static final Integer SAVE_OK = 20011;
    public static final Integer DELETE_OK = 20021;
    public static final Integer UPDATE_OK = 20031;
    public static final Integer GET_OK = 20041;


    public static final Integer SAVE_ERR = 20010;
    public static final Integer DELETE_ERR = 20020;
    public static final Integer UPDATE_ERR = 20030;
    public static final Integer GET_ERR = 20040;
}
