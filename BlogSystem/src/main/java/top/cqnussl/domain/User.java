package top.cqnussl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author shisl
 * @date 2023/04/17
 */
@Data
public class User {

    /*用户唯一id*/
    @TableId(type = IdType.AUTO)
    private Long id;

    /*用户账号*/
    private String account;

    /*用户昵称*/
    private String nickname;

    /*用户密码*/
//    @TableField(select = false)
    private String pwd;

    /*用户手机号*/
    private String tel;

    /*用户邮箱*/
    private String email;

    /*用户类型(访客/普通用户/管理员)*/
    private String type;

    /*用户头像*/
    private String avatar;
}
