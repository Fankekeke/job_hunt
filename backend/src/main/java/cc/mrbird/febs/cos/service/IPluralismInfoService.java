package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.PluralismInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IPluralismInfoService extends IService<PluralismInfo> {

    /**
     * 分页获取兼职信息
     *
     * @param page          分页对象
     * @param pluralismInfo 兼职信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectPluralismPage(Page<PluralismInfo> page, PluralismInfo pluralismInfo);
}
