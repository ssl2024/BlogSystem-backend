package top.cqnussl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.cqnussl.dao.UserDao;
import top.cqnussl.domain.User;
import top.cqnussl.service.UserService;


/**
 * @author shisl
 * @date 2023/04/17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
