package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.CollectInfo;
import cc.mrbird.febs.cos.dao.CollectInfoMapper;
import cc.mrbird.febs.cos.service.ICollectInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
public class CollectInfoServiceImpl extends ServiceImpl<CollectInfoMapper, CollectInfo> implements ICollectInfoService {

    /**
     * 分页获取收藏信息
     *
     * @param page 分页对象
     * @param collectInfo 收藏信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectCollectPage(Page<CollectInfo> page, CollectInfo collectInfo) {
        return baseMapper.selectCollectPage(page, collectInfo);
    }

    /**
     * 根据企业编号获取收藏专家信息
     *
     * @param enterpriseCode 企业编号
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectCollectByEnterprise(String enterpriseCode) {
        return baseMapper.selectCollectByEnterprise(enterpriseCode);
    }
}
