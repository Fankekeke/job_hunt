package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.IndustryInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IIndustryInfoService extends IService<IndustryInfo> {

    /**
     * 分页获取行业信息
     *
     * @param page         分页对象
     * @param industryInfo 行业信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectIndustryPage(Page<IndustryInfo> page, IndustryInfo industryInfo);
}
