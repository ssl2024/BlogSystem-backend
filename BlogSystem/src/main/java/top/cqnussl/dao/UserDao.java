package top.cqnussl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.cqnussl.domain.User;

/**
 * @author shisl
 * @date 2023/04/17
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

}
