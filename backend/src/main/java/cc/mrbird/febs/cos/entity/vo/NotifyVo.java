package cc.mrbird.febs.cos.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 消息信息
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NotifyVo {

    private String expertCode;

    private String enterpriseCode;

    private String content;

    private Integer flag;

    private String expertName;

    private String enterpriseName;
}
