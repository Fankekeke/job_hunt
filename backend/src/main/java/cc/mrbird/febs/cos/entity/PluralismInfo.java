package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 兼职管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PluralismInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 兼职标题
     */
    private String title;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 结算方式（1.日结 2.周结 3.月结 4.季结）
     */
    private Integer paymentMethod;

    /**
     * 岗位描述
     */
    private String describe;

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


}
