package top.cqnussl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author shisl
 * @date 2023/04/17
 */
@Data
public class Comment {

    /*评论唯一id*/
    @TableId(type = IdType.AUTO)
    private Long id;

    /*评论者id*/
    private Long userId;

    /*评论内容*/
    private String content;

    /*评论所属的博客id*/
    private Long blogId;

    /*评论创建时间*/
    private String createTime;

    /*父级评论id*/
    private Long parentId;

    /*回复评论id*/
    private Long replyUserId;

}
