package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.NotifyInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface NotifyInfoMapper extends BaseMapper<NotifyInfo> {

    /**
     * 分页获取消息通知信息
     *
     * @param page 分页对象
     * @param notifyInfo 消息通知信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectNotifyPage(Page<NotifyInfo> page, @Param("notifyInfo") NotifyInfo notifyInfo);
}
