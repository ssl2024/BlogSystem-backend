package top.cqnussl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author shisl
 * @date 2023/04/17
 */
@Data
public class Follow {

    /*关注唯一id*/
    @TableId(type = IdType.AUTO)
    private Long id;

    /*被关注的用户id*/
    private Long followedUserId;

    /*关注别人的用户*/
    private Long followUserId;
}
