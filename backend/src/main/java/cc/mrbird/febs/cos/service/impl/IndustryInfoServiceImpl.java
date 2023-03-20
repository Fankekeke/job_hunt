package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.IndustryInfo;
import cc.mrbird.febs.cos.dao.IndustryInfoMapper;
import cc.mrbird.febs.cos.service.IIndustryInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class IndustryInfoServiceImpl extends ServiceImpl<IndustryInfoMapper, IndustryInfo> implements IIndustryInfoService {

    /**
     * 分页获取行业信息
     *
     * @param page         分页对象
     * @param industryInfo 行业信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectIndustryPage(Page<IndustryInfo> page, IndustryInfo industryInfo) {
        return baseMapper.selectIndustryPage(page, industryInfo);
    }
}
