package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 岗位管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PostInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 岗位编号
     */
    private String code;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 工作地点
     */
    private String address;

    /**
     * 所需经验
     */
    private String experience;

    /**
     * 岗位职责
     */
    private String responsibility;

    /**
     * 学历要求（1.小学 2.初中 3.高中 4.大专 5.本科 6.研究生）
     */
    private Integer academic;

    /**
     * 工作时间
     */
    private String workTime;

    /**
     * 工作时段
     */
    private String workHour;

    /**
     * 工作时段
     */
    private String workAddress;

    /**
     * 工作要求
     */
    private String workRequire;

    /**
     * 所属行业
     */
    private Integer industryId;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 上下架（0.下架 1.上架）
     */
    private Integer delFlag;

    /**
     * 所属企业
     */
    private Integer enterpriseId;

    /**
     * 薪资
     */
    private String salary;

    /**
     * 福利
     */
    private String welfare;


}
