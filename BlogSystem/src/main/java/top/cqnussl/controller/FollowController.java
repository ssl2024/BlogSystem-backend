package top.cqnussl.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.cqnussl.domain.Follow;
import top.cqnussl.service.FollowService;

import java.util.List;

/**
 * @author shisl
 * @date 2023/04/17
 */
@RestController
@RequestMapping("/follows")
public class FollowController {

    @Autowired
    private FollowService followService;

    /**
     * 关注用户
     * @param follow
     * @return 布尔值
     * */
    @PostMapping
    public Result saveFollow(@RequestBody Follow follow){
        boolean flag = followService.save(follow);
        Integer code = flag ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag ? "关注成功" : "关注失败，请重试";
        return new Result(code,flag,msg);
    }

    /**
     * 取消关注
     * @param followedUserId
     * @param followUserId
     * @return 布尔值
     * */
    @DeleteMapping("/{followedUserId}/{followUserId}")
    public Result removeFollow(@PathVariable Long followedUserId,@PathVariable Long followUserId){
        // 设置删除条件
        LambdaQueryWrapper<Follow> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Follow::getFollowedUserId,followedUserId).eq(Follow::getFollowUserId,followUserId);
        boolean flag = followService.remove(lqw);
        Integer code = flag ? Code.DELETE_OK : Code.DELETE_ERR;
        String msg = flag ? "取消关注用户成功" : "取消关注用户失败，请重试";
        return new Result(code,flag,msg);
    }

    /**
     * 查询指定用户id的粉丝
     * @param id
     * @return 粉丝的用户id列表
     * */
    @GetMapping("/fans/{id}")
    public Result listFans(@PathVariable Long id){
        // 设置查询条件
        LambdaQueryWrapper<Follow> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Follow::getFollowedUserId,id);
        List<Follow> fans = followService.list(lqw);
        Integer code = fans != null ? Code.GET_OK : Code.GET_ERR;
        String msg = fans != null ? "查询粉丝列表成功" : "查询粉丝列表失败";
        return new Result(code,fans,msg);
    }

    /**
     * 查询指定用户id的关注
     * @param id
     * @return 关注的用户id列表
     * */
    @GetMapping("/follow/{id}")
    public Result listFollows(@PathVariable Long id){
        // 设置查询条件
        LambdaQueryWrapper<Follow> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Follow::getFollowUserId,id);
        List<Follow> follows = followService.list(lqw);
        Integer code = follows != null ? Code.GET_OK : Code.GET_ERR;
        String msg = follows != null ? "查询关注列表成功" : "查询关注列表失败";
        return new Result(code,follows,msg);
    }

    /**
     * 查询关注状态
     * @param followedUserId
     * @param followUserId
     * @return 布尔值
     * */
    @GetMapping("/state/{followedUserId}/{followUserId}")
    public Result getFollowState(@PathVariable Long followedUserId,@PathVariable Long followUserId){
        // 设置查询条件
        LambdaQueryWrapper<Follow> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Follow::getFollowedUserId,followedUserId).eq(Follow::getFollowUserId,followUserId).last("limit 1");;
        Follow follow = followService.getOne(lqw);
        boolean flag = follow != null ? true : false;
        Integer code = Code.GET_OK;
        String msg = "查询关注状态成功";
        return new Result(code,flag,msg);
    }
}
