package top.cqnussl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author shisl
 * @date 2023/04/17
 */
@Data
public class Collect {

    /*收藏唯一id*/
    @TableId(type = IdType.AUTO)
    private Long id;

    /*被收藏的博客id*/
    private Long blogId;

    /*收藏博客的用户id*/
    private Long UserId;
}
