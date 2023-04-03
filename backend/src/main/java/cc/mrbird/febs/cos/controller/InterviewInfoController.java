package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.EnterpriseInfo;
import cc.mrbird.febs.cos.entity.ExpertInfo;
import cc.mrbird.febs.cos.entity.InterviewInfo;
import cc.mrbird.febs.cos.service.IEnterpriseInfoService;
import cc.mrbird.febs.cos.service.IExpertInfoService;
import cc.mrbird.febs.cos.service.IInterviewInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/interview-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InterviewInfoController {

    private final IInterviewInfoService interviewInfoService;

    private final IExpertInfoService expertInfoService;

    private final IEnterpriseInfoService enterpriseInfoService;

    /**
     * 分页获取面试信息
     *
     * @param page          分页对象
     * @param interviewInfo 面试信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<InterviewInfo> page, InterviewInfo interviewInfo) {
        if (interviewInfo.getEnterpriseId() != null) {
            EnterpriseInfo enterpriseInfo = enterpriseInfoService.getOne(Wrappers.<EnterpriseInfo>lambdaQuery().eq(EnterpriseInfo::getUserId, interviewInfo.getEnterpriseId()));
            interviewInfo.setEnterpriseId(enterpriseInfo.getId());
        }
        return R.ok(interviewInfoService.selectInterViewPage(page, interviewInfo));
    }

    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(interviewInfoService.getById(id));
    }

    @GetMapping("/list")
    public R list() {
        return R.ok(interviewInfoService.list());
    }

    /**
     * 新增面试信息
     *
     * @param interviewInfo 面试信息
     * @return 结果
     */
    @PostMapping
    public R save(InterviewInfo interviewInfo) {
        ExpertInfo expertInfo  = expertInfoService.getOne(Wrappers.<ExpertInfo> lambdaQuery().eq(ExpertInfo::getUserId, interviewInfo.getExpertId()));
        interviewInfo.setExpertId(expertInfo.getId());
        interviewInfo.setDelFlag(0);
        interviewInfo.setStatus(1);
        interviewInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(interviewInfoService.save(interviewInfo));
    }

    /**
     * 修改面试信息
     *
     * @param interviewInfo 面试信息
     * @return 结果
     */
    @PutMapping
    public R edit(InterviewInfo interviewInfo) {
        return R.ok(interviewInfoService.updateById(interviewInfo));
    }

    /**
     * 删除面试信息
     *
     * @param ids ids
     * @return 面试信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(interviewInfoService.removeByIds(ids));
    }

}
