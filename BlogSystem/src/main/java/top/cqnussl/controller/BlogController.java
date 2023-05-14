package top.cqnussl.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.cqnussl.domain.Blog;
import top.cqnussl.service.BlogService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


/**
 * @author shisl
 * @date 2023/04/17
 */
@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    BlogService blogService;

    /**
     * 新增博客
     * @param blog
     * @return 布尔值
     * */
    @PostMapping
    public Result saveBlog(@RequestBody Blog blog,HttpServletRequest request){
        // 获取用户id
        Long id = (Long) request.getAttribute("user_id");
        blog.setAuthorId(id);
        boolean flag = blogService.save(blog);
        Integer code = flag ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag ? "发布成功" : "发布失败，请重试";
        return new Result(code,flag,msg);
    }

    /**
     * 删除博客
     * @param id
     * @return 布尔值
     */
    @DeleteMapping("/{id}")
    public Result removeBlog(@PathVariable Long id){
        boolean flag = blogService.removeById(id);
        Integer code = flag ? Code.DELETE_OK : Code.DELETE_ERR;
        String msg = flag ? "删除成功" : "删除失败，请重试";
        return new Result(code,flag,msg);
    }

    /**
     * 更新博客
     * @param blog
     * @return 布尔值
     * */
    @PutMapping
    public Result updateBlog(@RequestBody Blog blog){
        boolean flag = blogService.updateById(blog);
        Integer code = flag ? Code.UPDATE_OK : Code.UPDATE_ERR;
        String msg = flag ? "更新成功" : "更新失败，请重试";
        return new Result(code,flag,msg);
    }

    /**
     * 根据id查询博客
     * @param id
     * @return blog
     */
    @GetMapping("/{id}")
    public Result getBlog(@PathVariable Long id){
        Blog blog = blogService.getById(id);
        Integer code = blog != null ? Code.GET_OK : Code.GET_ERR;
        String msg = blog != null ? "查询成功" : "查询失败，请重试";
        return new Result(code,blog,msg);
    }

    /**
     * 分页查询博客
     * @param currentPage
     * @param size
     * @param query
     * @return 博客列表
     * */
    @PostMapping("/{currentPage}/{size}")
    public Result listBlogPage(@PathVariable Integer currentPage, @PathVariable Integer size,@RequestBody HashMap<String,List<String>> query){
        // 设置查询条件
        LambdaQueryWrapper<Blog> lqw = new LambdaQueryWrapper<>();
        // 获取查询条件
        // 博客 id
        List<String> id = query.get("id");
        // 博客作者 id
        List<String> authorId = query.get("authorId");
        // 博客类型
        List<String> type = query.get("type");
        // 博客标题
        List<String> title = query.get("title");
        // 如果条件不为 null 则 添加
        if(id != null){
            lqw.in(Blog::getId,id);
        }
        if(authorId != null){
            lqw.in(Blog::getAuthorId,authorId);
        }
        if(type != null){
            lqw.in(Blog::getType,type);
        }
        if(title != null){
            lqw.like(Blog::getTitle,title.get(0));
        }
        IPage<Blog> blogPage= new Page(currentPage,size);
        blogService.page(blogPage,lqw);
        Integer code = blogPage != null ? Code.GET_OK : Code.GET_ERR;
        String msg = blogPage != null ? "查询博客列表成功" : "查询博客列表失败，请重试";
        return new Result(code,blogPage,msg);
    }

    /**
     * 查询指定用户id的相关信息
     * 1 - 发表的博客数量
     * 2 - 博客被阅读的次数
     * 3 - 博客被点赞的次数
     * 4 - 博客被收藏的次数
     * @param id
     * @return 相关数量
     */
    @GetMapping("/count/{id}")
    public Result getBlogCount(@PathVariable Long id){
        // 设置查询条件
        LambdaQueryWrapper<Blog> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Blog::getAuthorId,id);
        List<Blog> blogs = blogService.list(lqw);
        Integer code = blogs != null ? Code.GET_OK : Code.GET_ERR;
        String msg = blogs != null ? "查询博客列表成功" : "查询博客列表失败，请重试";
        // 博客数量
        Integer blogCount = blogs.size();
        // 博客被阅读次数
        Integer browsedCount = 0;
        // 博客被点赞次数
        Integer likedCount = 0;
        // 博客被收藏次数
        Integer collectedCount = 0;
        // 遍历博客列表
        for (Blog item : blogs) {
            browsedCount += item.getBrowseCount();
            likedCount += item.getLikeCount();
            collectedCount += item.getCollectCount();
        }
        // 定义 hashMap 存储数据
        HashMap<String,Integer> count = new HashMap<>();
        count.put("entryCount",blogCount);
        count.put("browsedCount",browsedCount);
        count.put("likedCount",likedCount);
        count.put("collectedCount",collectedCount);
        return new Result(code,count,msg);
    }
}
