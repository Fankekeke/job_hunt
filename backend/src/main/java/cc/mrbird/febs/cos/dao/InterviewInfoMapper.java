package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.InterviewInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface InterviewInfoMapper extends BaseMapper<InterviewInfo> {

    /**
     * 分页获取面试信息
     *
     * @param page          分页对象
     * @param interviewInfo 面试信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectInterViewPage(Page<InterviewInfo> page, @Param("interviewInfo") InterviewInfo interviewInfo);
}
