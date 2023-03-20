package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ExpertInfo;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface ExpertInfoMapper extends BaseMapper<ExpertInfo> {

    /**
     * 分页获取专家信息
     *
     * @param page 分页对象
     * @param expertInfo 专家信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectExpertPage(Page<ExpertInfo> page, @Param("expertInfo") ExpertInfo expertInfo);

    /**
     * 分页获取游客信息
     *
     * @param page     分页对象
     * @param user 对象
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectUserPage(Page<User> page, @Param("user")User user);
}
