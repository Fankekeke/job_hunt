package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 企业收藏
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CollectInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 专家编号
     */
    private String expertCode;


    private String baseId;

    /**
     * 收藏类型（1.企业收藏 2.兼职收藏 3.岗位收藏）
     */
    private Integer type;

    /**
     * 企业编号
     */
    @TableField(exist = false)
    private String enterpriseCode;

    /**
     * 创建时间
     */
    private String createDate;

    @TableField(exist = false)
    private String expertName;

    @TableField(exist = false)
    private String enterpriseName;

    @TableField(exist = false)
    private String postName;

}
