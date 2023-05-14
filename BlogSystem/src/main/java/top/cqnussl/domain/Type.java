package top.cqnussl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author shisl
 * @date 2023/04/17
 */
@Data
public class Type {

    /*类型唯一id*/
    @TableId(type = IdType.AUTO)
    private Integer id;

    /*类型*/
    private String type;

    /*分类*/
    private String classify;
}
