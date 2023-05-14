package top.cqnussl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.cqnussl.dao.UserDao;
import top.cqnussl.domain.User;

@SpringBootTest
class BlogSystemApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
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
