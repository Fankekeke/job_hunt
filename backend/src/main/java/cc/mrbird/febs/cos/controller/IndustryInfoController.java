package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.IndustryInfo;
import cc.mrbird.febs.cos.service.IIndustryInfoService;
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
@RequestMapping("/cos/industry-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IndustryInfoController {

    private final IIndustryInfoService industryInfoService;

    /**
     * 分页获取行业信息
     *
     * @param page         分页对象
     * @param industryInfo 行业信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<IndustryInfo> page, IndustryInfo industryInfo) {
        return R.ok(industryInfoService.selectIndustryPage(page, industryInfo));
    }

    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(industryInfoService.getById(id));
    }

    @GetMapping("/list")
    public R list() {
        return R.ok(industryInfoService.list(Wrappers.<IndustryInfo>lambdaQuery().eq(IndustryInfo::getDelFlag, 1)));
    }

    /**
     * 新增行业信息
     *
     * @param industryInfo 行业信息
     * @return 结果
     */
    @PostMapping
    public R save(IndustryInfo industryInfo) {
        industryInfo.setCode("IDT-" + System.currentTimeMillis());
        industryInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(industryInfoService.save(industryInfo));
    }

    /**
     * 修改行业信息
     *
     * @param industryInfo 行业信息
     * @return 结果
     */
    @PutMapping
    public R edit(IndustryInfo industryInfo) {
        return R.ok(industryInfoService.updateById(industryInfo));
    }

    /**
     * 删除行业信息
     *
     * @param ids ids
     * @return 行业信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(industryInfoService.removeByIds(ids));
    }

}
