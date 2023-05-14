package top.cqnussl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.cqnussl.domain.Type;

import java.util.List;

/**
 * @author shisl
 * @date 2023/04/17
 */
@Mapper
public interface TypeDao extends BaseMapper<Type> {

}
