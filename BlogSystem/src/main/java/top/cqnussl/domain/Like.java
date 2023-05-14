package top.cqnussl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author shisl
 * @date 2023/04/17
 */
@Data
public class Like {

    /*点赞唯一id*/
    @TableId(type = IdType.AUTO)
    private Long id;

    /*点赞博客的用户id*/
    private Integer blogId;

    /*被点赞的博客id*/
    private Integer userId;
}
