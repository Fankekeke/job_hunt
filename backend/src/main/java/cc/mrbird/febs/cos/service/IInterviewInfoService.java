package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.InterviewInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IInterviewInfoService extends IService<InterviewInfo> {

    /**
     * 分页获取面试信息
     *
     * @param page          分页对象
     * @param interviewInfo 面试信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectInterViewPage(Page<InterviewInfo> page, InterviewInfo interviewInfo);
}
