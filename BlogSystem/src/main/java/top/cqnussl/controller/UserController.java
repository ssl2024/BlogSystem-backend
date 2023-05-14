package top.cqnussl.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.web.bind.annotation.*;
import top.cqnussl.domain.User;
import top.cqnussl.service.UserService;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author shisl
 * @date 2023/04/17
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 新增用户
     * @param user
     * @return 布尔值
     * */
    @PostMapping
    public Result saveUser(@RequestBody User user){
        boolean flag = userService.save(user);
        Integer code = flag ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag ? "添加用户成功" : "添加用户失败";
        return new Result(code,flag,msg);
    }

    /**
     * 删除用户
     * @param id
     * @return 布尔值
     * */
    @DeleteMapping("/{id}")
    public Result removeUser(@PathVariable Long id){
        // boolean flag = userService.removeById(id); 暂不允许删除用户
        boolean flag = false;
        Integer code = flag ? Code.DELETE_OK : Code.DELETE_ERR;
        String msg = flag ? "删除用户成功" : "删除用户失败，请重试";
        return new Result(code,flag,msg);
    }

    /**
     * 更新用户
     * @param user
     * @return 布尔值
     * */
    @PutMapping
    public Result updateUser(@RequestBody User user){
        boolean flag = userService.updateById(user);
        Integer code = flag ? Code.UPDATE_OK : Code.UPDATE_ERR;
        String msg = flag ? "更新用户信息成功" : "删除用户信息失败，请重试";
        return new Result(code,flag,msg);
    }

    /**
     * 根据id查询用户
     * @param id
     * @return user
     * */
    @GetMapping("/{id}")
    public Result getUser(@PathVariable Long id){
        User user = userService.getById(id);
        Integer code = user != null ? Code.GET_OK : Code.GET_ERR;
        String msg = user != null ? "查询用户成功" : "查询用户失败，请重试";
        return new Result(code,user,msg);
    }

    /**
     * 判断账号是否可用
     * @param account
     * @return 布尔值
     * */
    @GetMapping("/query/{account}")
    public Result getUserByAccount(@PathVariable String account){
        // 设置查询条件
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getAccount,account);
        int count = userService.count(lqw);
        boolean flag = count == 0 ? true : false;
        Integer code = flag ? Code.ACCOUNT_OK : Code.ACCOUNT_ERR;
        String msg = flag ? "该账号可以使用" : "该账号已被占用";
        return new Result(code,flag,msg);
    }

    /**
     * 用户登录
     * @param user
     * @return 布尔值
     * */
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        // 设置查询条件
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        // 获取账号和密码
        String account = user.getAccount();
        String pwd = user.getPwd();
        lqw.eq(User::getAccount,account);
        lqw.eq(User::getPwd,pwd);
        User queryUser = userService.getOne(lqw);
        if(queryUser != null){
            HashMap<String,Object> hashMap = new HashMap<>();
            // 登录成功 生成 token 令牌
            String token = UUID.randomUUID()+"";
            hashMap.put("user",queryUser);
            hashMap.put("token",token);
            // 将 User 对象转换为 JSON 字符串进行序列化
            Jackson2JsonRedisSerializer<User> serializer = new Jackson2JsonRedisSerializer<>(User.class);
            // 存到 Redis 数据库
            redisTemplate.opsForValue().set(token,serializer.serialize(queryUser),Duration.ofHours(12));

            return new Result(Code.GET_OK,hashMap,"登录成功");
        }
        // 登录失败
        return new Result(Code.GET_ERR,null,"登录失败");
    }

    /**
     * 分页查询用户
     * @param currentPage
     * @param size
     * @param ids
     * @return 用户列表
     * */
    @PostMapping("/{currentPage}/{size}")
    public Result listUserPage(@PathVariable Integer currentPage,@PathVariable Integer size, @RequestBody HashMap<String,List<Integer>> ids){
        // 设置查询条件
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        List<Integer> id = ids.get("id");
        lqw.in(User::getId,id);
        IPage<User> userPage = new Page<>(currentPage,size);
        userService.page(userPage, lqw);
        Integer code = userPage != null ? Code.GET_OK : Code.GET_ERR;
        String msg = userPage != null ? "查询用户列表成功" : "查询用户列表失败，请重试";
        return new Result(code,userPage,msg);
    }

    /**
     * 从 Redis 中获取登录用户信息
     * @param request
     * @return user
     * */
    @GetMapping("/getUserOfLogin")
    public Result getUserOfLogin(HttpServletRequest request) throws UnsupportedEncodingException {
        // 获取 Headers 中的参数
        String token = request.getHeader("token");

        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            return new Result(Code.LOGIN_OK,user,"获取用户登录成功");
        }
        return new Result(Code.LOGIN_ERR,null,"获取用户登录失败");
    }

    /**
     * 获取所有用户信息
     * @param
     * @return 用户id列表
     * */
    @GetMapping
    public Result listUser(){
        List<User> users = userService.list();
        Integer code = Code.GET_OK;
        String msg = "查询用户id列表成功";
        return new Result(code,users,msg);
    }

    /**
     * 用户注销
     * @param token
     * @return 布尔值
     * */
    @DeleteMapping("/logout")
    public Result logout(@RequestHeader("token") String token){
        // 删除 Redis 中对应的 token
        redisTemplate.delete(token);
        Integer code = Code.DELETE_OK;
        String msg = "注销成功";
        return new Result(code,true,msg);
    }
}
