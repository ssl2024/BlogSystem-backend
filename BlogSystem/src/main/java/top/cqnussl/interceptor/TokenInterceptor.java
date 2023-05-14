package top.cqnussl.interceptor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.cqnussl.domain.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shisl
 * @date 2023/04/22
 */
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取 token
        String token = request.getHeader("token");
        // 从 Redis 中获取 JSON 字符串并反序列化为 User 对象
        Jackson2JsonRedisSerializer<User> serializer = new Jackson2JsonRedisSerializer<>(User.class);
        byte[] bytes = (byte[]) redisTemplate.opsForValue().get(token);
        User user = serializer.deserialize(bytes);
        if(user != null){
            // 放行
            // 将 user_id 存储在 request 中，方便后续的业务方法使用
            request.setAttribute("user_id", user.getId());
            return true;
        }
        // 拦截
        return false;
    }
}
