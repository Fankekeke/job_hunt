package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ChatInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface ChatInfoMapper extends BaseMapper<ChatInfo> {

    /**
     * 分页获取技术沟通信息
     *
     * @param page     分页对象
     * @param chatInfo 技术沟通信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectChatPage(Page<ChatInfo> page, @Param("chatInfo") ChatInfo chatInfo);

    /**
     * 根据用户编号获取联系人
     *
     * @param userCode 用户编号
     * @param flag     1.专家 2.企业
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectContactPerson(@Param("userCode") String userCode, @Param("flag") Integer flag);

    /**
     * 查询聊天记录
     *
     * @param expertCode     专家编号
     * @param enterpriseCode 企业编号
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectChatList(@Param("expertCode") String expertCode, @Param("enterpriseCode") String enterpriseCode);
}
