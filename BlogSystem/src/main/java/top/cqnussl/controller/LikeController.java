package top.cqnussl.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.cqnussl.domain.Like;
import top.cqnussl.service.LikeService;

import java.util.List;

/**
 * @author shisl
 * @date 2023/04/17
 */
@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    /**
     * 新增点赞
     * @param like
     * @return 布尔值
     * */
    @PostMapping
    public Result saveLike(@RequestBody Like like){
        boolean flag = likeService.save(like);
        Integer code = flag ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag ? "点赞文章成功" : "点赞文章失败，请重试";
        return new Result(code,flag,msg);
    }

    /**
     * 根据博客id删除文章点赞信息
     * @param blogId
     * @return 布尔值
     * */
    @DeleteMapping("/{blogId}")
    public Result removeLikeByBlogId(@PathVariable Long blogId){
        // 设置查询条件
        LambdaQueryWrapper<Like> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Like::getBlogId,blogId);
        boolean flag = likeService.remove(lqw);
        Integer code = flag ? Code.DELETE_OK : Code.DELETE_ERR;
        String msg = flag ? "删除文章点赞信息成功" : "删除文章点赞失败，请重试";
        return new Result(code,flag,msg);
    }

    /**
     * 取消点赞
     * @param blogId
     * @param userId
     * @return 布尔值
     * */
    @DeleteMapping("/{blogId}/{userId}")
    public Result removeLike(@PathVariable Integer blogId,@PathVariable Integer userId){
        // 设置删除条件
        LambdaQueryWrapper<Like> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Like::getBlogId,blogId).eq(Like::getUserId,userId);
        boolean flag = likeService.remove(lqw);
        Integer code = flag ? Code.DELETE_OK : Code.DELETE_ERR;
        String msg = flag ? "取消收藏成功" : "取消收藏失败，请重试";
        return new Result(code,flag,msg);
    }


    /**
     * 查询文章点赞状态
     * @param blogId
     * @param userId
     * @return like
     * */
    @GetMapping("/{blogId}/{userId}")
    public Result getState(@PathVariable Integer blogId,@PathVariable Integer userId){
        // 设置查询条件
        LambdaQueryWrapper<Like> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Like::getBlogId,blogId).eq(Like::getUserId,userId);
        Like like = likeService.getOne(lqw);
        boolean flag = like != null ? true : false;
        Integer code = Code.GET_OK;
        String msg = "查询点赞状态成功";
        return new Result(code,flag,msg);
    }

    /**
     * 查询指定用户id点赞的博客id列表
     * @param id
     * @return likes
     */
    @GetMapping("/list/{userId}")
    public Result listUserLikeBlogId(@PathVariable Long userId){
        // 设置查询条件
        LambdaQueryWrapper<Like> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Like::getUserId,userId);
        List<Like> likes = likeService.list(lqw);
        Integer code = likes != null ? Code.GET_OK : Code.GET_ERR;
        String msg = likes != null ? "查询博客id列表成功" : "查询博客id列表失败，请重试";
        return new Result(code,likes,msg);
    }

    /**
     * 查询指定博客id的点赞数量
     * @param blogId
     * @return 点赞数量
     * */
    @GetMapping("/count/{blogId}")
    public Result getLikeCountByBlogId(@PathVariable Long blogId){
        // 设置查询条件
        LambdaQueryWrapper<Like> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Like::getBlogId,blogId);
        int count = likeService.count(lqw);
        Integer code = Code.GET_OK;
        String msg = "查询博客点赞数量成功";
        return new Result(code,count,msg);
    }

}
