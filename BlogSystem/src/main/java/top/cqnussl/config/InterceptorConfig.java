package top.cqnussl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.cqnussl.interceptor.TokenInterceptor;

/**
 * @author shisl
 * @date 2023/04/22
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .excludePathPatterns("/users/query/**") // 排除查询账号是否可用接口
                .excludePathPatterns("/users") // 排除注册接口
                .excludePathPatterns("/users/login") // 排除登录接口
                .excludePathPatterns("/blogs/count/**") // 排除获取个人成就接口
                .addPathPatterns("/**"); // 拦截所有请求
    }
}

