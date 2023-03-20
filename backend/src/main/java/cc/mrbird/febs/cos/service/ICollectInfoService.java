package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.CollectInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface ICollectInfoService extends IService<CollectInfo> {

    /**
     * 分页获取收藏信息
     *
     * @param page 分页对象
     * @param collectInfo 收藏信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectCollectPage(Page<CollectInfo> page, CollectInfo collectInfo);

    /**
     * 根据企业编号获取收藏专家信息
     *
     * @param enterpriseCode 企业编号
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectCollectByEnterprise(String enterpriseCode);
}
