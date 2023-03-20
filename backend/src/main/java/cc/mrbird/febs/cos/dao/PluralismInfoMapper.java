package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.PluralismInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface PluralismInfoMapper extends BaseMapper<PluralismInfo> {

    /**
     * 分页获取兼职信息
     *
     * @param page          分页对象
     * @param pluralismInfo 兼职信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectPluralismPage(Page<PluralismInfo> page, @Param("pluralismInfo") PluralismInfo pluralismInfo);
}
