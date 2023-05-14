package top.cqnussl.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.cqnussl.domain.Collect;
import top.cqnussl.service.CollectService;

import java.util.List;

/**
 * @author shisl
 * @date 2023/04/17
 */
@RestController
@RequestMapping("/collects")
public class CollectController {

    @Autowired
    private CollectService collectService;

    /**
     * 收藏文章
     * @param collect
     * @return 布尔值
     * */
    @PostMapping
    public Result saveCollect(@RequestBody Collect collect){
        boolean flag = collectService.save(collect);
        Integer code = flag ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag ? "收藏文章成功" : "收藏文章失败，请重试";
        return new Result(code,flag,msg);
    }

    /**
     * 根据博客id删除文章收藏信息
     * @param blogId
     * @return 布尔值
     * */
    @DeleteMapping("/{blogId}")
    public Result removeLikeByBlogId(@PathVariable Long blogId){
        // 设置查询条件
        LambdaQueryWrapper<Collect> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Collect::getBlogId,blogId);
        boolean flag = collectService.remove(lqw);
        Integer code = flag ? Code.DELETE_OK : Code.DELETE_ERR;
        String msg = flag ? "删除文章收藏信息成功" : "删除文章收藏失败，请重试";
        return new Result(code,flag,msg);
    }

    /**
     * 取消收藏
     * @param blogId
     * @param userId
     * @return 布尔值
     * */
    @DeleteMapping("/{blogId}/{userId}")
    public Result removeCollect(@PathVariable Integer blogId,@PathVariable Integer userId){
        // 设置删除条件
        LambdaQueryWrapper<Collect> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Collect::getBlogId,blogId).eq(Collect::getUserId,userId);
        boolean flag = collectService.remove(lqw);
        Integer code = flag ? Code.DELETE_OK : Code.DELETE_ERR;
        String msg = flag ? "取消收藏成功" : "取消收藏失败，请重试";
        return new Result(code,flag,msg);
    }

    /**
     * 查询文章收藏状态
     * @param blogId
     * @param userId
     * @return collect
     * */
    @GetMapping("/{blogId}/{userId}")
    public Result getState(@PathVariable Integer blogId,@PathVariable Integer userId){
        // 设置查询条件
        LambdaQueryWrapper<Collect> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Collect::getBlogId,blogId).eq(Collect::getUserId,userId);
        Collect collect = collectService.getOne(lqw);
        boolean flag = collect != null ? true : false;
        Integer code = Code.GET_OK;
        String msg = "查询收藏状态成功";
        return new Result(code,flag,msg);
    }

    /**
     * 查询指定用户id收藏的博客id列表
     * @param id
     * @return likes
     */
    @GetMapping("/list/{id}")
    public Result listUserCollectBlogId(@PathVariable Long id){
        // 设置查询条件
        LambdaQueryWrapper<Collect> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Collect::getUserId,id);
        List<Collect> collects = collectService.list(lqw);
        Integer code = collects != null ? Code.GET_OK : Code.GET_ERR;
        String msg = collects != null ? "查询博客id列表成功" : "查询博客id列表失败，请重试";
        return new Result(code,collects,msg);
    }

    /**
     * 查询指定博客id的点赞数量
     * @param blogId
     * @return 点赞数量
     * */
    @GetMapping("/count/{blogId}")
    public Result getCollectCountByBlogId(@PathVariable Long blogId){
        // 设置查询条件
        LambdaQueryWrapper<Collect> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Collect::getBlogId,blogId);
        int count = collectService.count(lqw);
        Integer code = Code.GET_OK;
        String msg = "查询博客收藏数量成功";
        return new Result(code,count,msg);
    }
}
