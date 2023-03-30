package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.InterviewInfo;
import cc.mrbird.febs.cos.dao.InterviewInfoMapper;
import cc.mrbird.febs.cos.service.IInterviewInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class InterviewInfoServiceImpl extends ServiceImpl<InterviewInfoMapper, InterviewInfo> implements IInterviewInfoService {

    /**
     * 分页获取面试信息
     *
     * @param page          分页对象
     * @param interviewInfo 面试信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectInterViewPage(Page<InterviewInfo> page, InterviewInfo interviewInfo) {
        if (interviewInfo.getType() == 1) {
            return baseMapper.selectInterViewPagePluralism(page, interviewInfo);
        } else {
            return baseMapper.selectInterViewPagePost(page, interviewInfo);
        }
    }
}
