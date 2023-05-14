package top.cqnussl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.cqnussl.dao.CommentDao;
import top.cqnussl.domain.Comment;
import top.cqnussl.service.CommentService;

/**
 * @author shisl
 * @date 2023/04/17
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

}
