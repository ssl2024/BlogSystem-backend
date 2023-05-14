package top.cqnussl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.cqnussl.dao.TypeDao;
import top.cqnussl.domain.Type;
import top.cqnussl.service.TypeService;

/**
 * @author shisl
 * @date 2023/04/17
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeDao, Type> implements TypeService {

}
