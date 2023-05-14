package top.cqnussl.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.cqnussl.domain.Type;
import top.cqnussl.service.TypeService;

import java.util.List;

/**
 * @author shisl
 * @date 2023/04/17
 */
@RestController
@RequestMapping("/types")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 新增分类
     * @param type
     * @return 布尔值
     * */
    @PostMapping
    public Result saveType(@RequestBody Type type){
        boolean flag = typeService.save(type);
        Integer code = flag ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag ? "添加分类成功" : "添加分类失败，请重试";
        return new Result(code,flag,msg);
    }

    /**
     * 删除分类
     * @param id
     * @return 布尔值
     * */
    @DeleteMapping("/{id}")
    public Result removeType(@PathVariable Integer id){
        boolean flag = typeService.removeById(id);
        Integer code = flag ? Code.DELETE_OK : Code.DELETE_ERR;
        String msg = flag ? "删除分类成功" : "删除分类失败，请重试";
        return new Result(code,flag,msg);
    }

    /**
     * 更新分类
     * @param type
     * @return 布尔值
     * */
    @PutMapping
    public Result updateType(@RequestBody Type type){
        boolean flag = typeService.updateById(type);
        Integer code = flag ? Code.UPDATE_OK : Code.UPDATE_ERR;
        String msg = flag ? "更新分类成功" : "更新分类失败，请重试";
        return new Result(code,flag,msg);
    }

    /**
     * 根据id查询分类
     * @param id
     * @return type
     * */
    @GetMapping("/{id}")
    public Result getType(@PathVariable Integer id){
        Type type = typeService.getById(id);
        Integer code = type != null ? Code.GET_OK : Code.GET_ERR;
        String msg = type != null ? "查询分类成功" : "查询分类失败，请重试";
        return new Result(code,type,msg);
    }

    /**
     * 查询所有分类
     * @return 分类列表
     * */
    @GetMapping
    public Result listTypes(){
        List<Type> types = typeService.list();
        Integer code = types != null ? Code.GET_OK : Code.GET_ERR;
        String msg = types != null ? "查询分类列表成功" : "添加分类列表失败，请重试";
        return new Result(code,types,msg);

    }

    /**
    * 根据类别查询分类
    * @param classify
    * @return 分类列表
    * */
    @GetMapping("/list/{classify}")
    public Result listTypesByClassify(@PathVariable String classify){
        // 设置查询条件
        LambdaQueryWrapper<Type> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Type::getClassify,classify);
        List<Type> types = typeService.list(lqw);
        Integer code = types != null ? Code.GET_OK : Code.GET_ERR;
        String msg = types != null ? "查询分类列表成功" : "查询分类列表失败，请重试";
        return new Result(code,types,msg);
    }
}
