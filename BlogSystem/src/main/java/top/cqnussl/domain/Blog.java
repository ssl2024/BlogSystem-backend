package top.cqnussl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author shisl
 * @date 2023/04/17
 */
@Data
public class Blog {

    /*博客唯一id*/
    @TableId(type = IdType.AUTO)
    private Long id;

    /*博客标题*/
    private String title;

    /*博客内容*/
    private String content;

    /*博客摘要*/
    private String blogAbstract;

    /*博客浏览次数*/
    private Integer browseCount;

    /*博客点赞次数*/
    private Integer likeCount;

    /*博客收藏次数*/
    private Integer collectCount;

    /*博客评论次数*/
    private Integer commentCount;

    /*博客创建时间*/
    private String createTime;

    /*博客更新时间*/
    private String updateTime;

    /*博客作者id*/
    private Long authorId;

    /*博客首图*/
    private String picture;

    /*博客类型*/
    private String type;
}
