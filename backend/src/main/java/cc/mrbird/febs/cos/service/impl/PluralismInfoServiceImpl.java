package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.PluralismInfo;
import cc.mrbird.febs.cos.dao.PluralismInfoMapper;
import cc.mrbird.febs.cos.service.IPluralismInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class PluralismInfoServiceImpl extends ServiceImpl<PluralismInfoMapper, PluralismInfo> implements IPluralismInfoService {

    /**
     * 分页获取兼职信息
     *
     * @param page          分页对象
     * @param pluralismInfo 兼职信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectPluralismPage(Page<PluralismInfo> page, PluralismInfo pluralismInfo) {
        return baseMapper.selectPluralismPage(page, pluralismInfo);
    }
}
