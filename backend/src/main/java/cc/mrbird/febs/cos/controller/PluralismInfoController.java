package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.PluralismInfo;
import cc.mrbird.febs.cos.service.IPluralismInfoService;
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
@RequestMapping("/cos/pluralism-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PluralismInfoController {

    private final IPluralismInfoService pluralismInfoService;

    /**
     * 分页获取兼职信息
     *
     * @param page          分页对象
     * @param pluralismInfo 兼职信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<PluralismInfo> page, PluralismInfo pluralismInfo) {
        return R.ok(pluralismInfoService.selectPluralismPage(page, pluralismInfo));
    }

    /**
     * 兼职信息上下架
     *
     * @param pluralismId 兼职ID
     * @param status      状态
     * @return 结果
     */
    @GetMapping("/audit")
    public R audit(@RequestParam("pluralismId") Integer pluralismId, @RequestParam("status") Integer status) {
        return R.ok(pluralismInfoService.update(Wrappers.<PluralismInfo>lambdaUpdate().set(PluralismInfo::getDelFlag, status).eq(PluralismInfo::getId, pluralismId)));
    }



    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(pluralismInfoService.getById(id));
    }

    @GetMapping("/list")
    public R list() {
        return R.ok(pluralismInfoService.list());
    }

    /**
     * 新增兼职信息
     *
     * @param pluralismInfo 兼职信息
     * @return 结果
     */
    @PostMapping
    public R save(PluralismInfo pluralismInfo) {
        pluralismInfo.setDelFlag(1);
        pluralismInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(pluralismInfoService.save(pluralismInfo));
    }

    /**
     * 修改兼职信息
     *
     * @param pluralismInfo 兼职信息
     * @return 结果
     */
    @PutMapping
    public R edit(PluralismInfo pluralismInfo) {
        return R.ok(pluralismInfoService.updateById(pluralismInfo));
    }

    /**
     * 删除兼职信息
     *
     * @param ids ids
     * @return 兼职信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(pluralismInfoService.removeByIds(ids));
    }

}
