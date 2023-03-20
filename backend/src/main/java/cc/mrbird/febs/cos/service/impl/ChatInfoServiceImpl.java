package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ChatInfo;
import cc.mrbird.febs.cos.dao.ChatInfoMapper;
import cc.mrbird.febs.cos.service.IChatInfoService;
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
public class ChatInfoServiceImpl extends ServiceImpl<ChatInfoMapper, ChatInfo> implements IChatInfoService {

    /**
     * 分页获取技术沟通信息
     *
     * @param page     分页对象
     * @param chatInfo 技术沟通信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectChatPage(Page<ChatInfo> page, ChatInfo chatInfo) {
        return baseMapper.selectChatPage(page, chatInfo);
    }

    /**
     * 根据用户编号获取联系人
     *
     * @param userCode 用户编号
     * @param flag     1.专家 2.企业
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectContactPerson(String userCode, Integer flag) {
        return baseMapper.selectContactPerson(userCode, flag);
    }

    /**
     * 查询聊天记录
     *
     * @param expertCode     专家编号
     * @param enterpriseCode 企业编号
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectChatList(String expertCode, String enterpriseCode) {
        return baseMapper.selectChatList(expertCode, enterpriseCode);
    }
}
