package top.cqnussl.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.cqnussl.domain.User;

@SpringBootTest
public class DaoTests {

    @Autowired
    private UserDao userDao;

    @Test
    void test(){
        User user = userDao.selectById(1);
        System.out.println(user);
    }

    @Test
    void testSave(){
        User user = new User();
        user.setAccount("123456789");
        user.setPwd("123456789");
        user.setEmail("1234@163.com");
        user.setTel("12345678901");
        user.setNickname("这是一个昵称");
        user.setType("1");
        user.setAvatar("这其实是图片在数据库中的地址");
        int insert = userDao.insert(user);
        if (insert > 0){
            System.out.println("数据插入成功");
        }
    }
}
