package top.cqnussl.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.cqnussl.domain.Comment;
import top.cqnussl.service.CommentService;

import java.util.List;

/**
 * @author shisl
 * @date 2023/04/17
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 新增评论
     * @param comment
     * @return 布尔值
     * */
    @PostMapping
    public Result saveComment(@RequestBody Comment comment){
        boolean flag = commentService.save(comment);
        Integer code = flag ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag ? "发表评论成功" : "发表评论失败，请重试";
        return new Result(code,flag,msg);
    }

    /**
     * 删除评论
     * @param id
     * @return 布尔值
     * */
    @DeleteMapping("/{id}")
    public Result removeComment(@PathVariable Long id){
        // 设置查询条件
        LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
        // 删除掉指定评论id 和 父级评论id 的数据
        lqw.eq(Comment::getId,id).or().eq(Comment::getParentId,id);
        boolean flag = commentService.remove(lqw);
        Integer code = flag ? Code.DELETE_OK : Code.DELETE_ERR;
        String msg = flag ? "删除评论成功" : "删除评论失败，请重试";
        return new Result(code,flag,msg);
    }

    /**
     * 查询指定博客id的评论列表
     * @param blogId
     * @return 评论列表
     * */
    @GetMapping("/{blogId}")
    public Result listComment(@PathVariable Long blogId){
        // 设置查询条件
        LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Comment::getBlogId,blogId);
        List<Comment> comments = commentService.list(lqw);
        Integer code = comments != null ? Code.GET_OK : Code.GET_ERR;
        String msg = comments != null ? "查询评论列表成功" : "查询评论列表失败，请重试";
        return new Result(code,comments,msg);
    }

    /**
     * 删除指定博客id的评论列表
     * @param blogId
     * @return 布尔值
     * */
    @DeleteMapping("/clear/{blogId}")
    public Result clearCommentsByBlogId(@PathVariable Long blogId){
        // 设置查询条件
        LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Comment::getBlogId,blogId);
        boolean flag = commentService.remove(lqw);
        Integer code = flag ? Code.DELETE_OK : Code.DELETE_ERR;
        String msg = flag ? "删除评论列表成功" : "删除评论列表失败，请重试";
        return new Result(code,flag,msg);
    }

}
