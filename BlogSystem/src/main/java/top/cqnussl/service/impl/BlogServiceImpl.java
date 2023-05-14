package top.cqnussl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.cqnussl.dao.BlogDao;
import top.cqnussl.domain.Blog;
import top.cqnussl.service.BlogService;


/**
 * @author shisl
 * @date 2023/04/17
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogDao, Blog> implements BlogService {

}
