package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.NotifyInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface INotifyInfoService extends IService<NotifyInfo> {

    /**
     * 分页获取消息通知信息
     *
     * @param page       分页对象
     * @param notifyInfo 消息通知信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectNotifyPage(Page<NotifyInfo> page, NotifyInfo notifyInfo);

    /**
     * 根据用户ID获取通知消息
     *
     * @param userCode 用户ID
     * @return 结果
     */
    List<NotifyInfo> selectNotifyByUserId(String userCode);
}
