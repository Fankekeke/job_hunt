package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.CollectInfo;
import cc.mrbird.febs.cos.service.ICollectInfoService;
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
@RequestMapping("/cos/collect-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CollectInfoController {

    private final ICollectInfoService collectInfoService;

    /**
     * 分页获取收藏信息
     *
     * @param page        分页对象
     * @param collectInfo 收藏信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<CollectInfo> page, CollectInfo collectInfo) {
        return R.ok(collectInfoService.selectCollectPage(page, collectInfo));
    }

    /**
     * 分页获取收藏信息
     *
     * @param page        分页对象
     * @param collectInfo 收藏信息
     * @return 结果
     */
    @GetMapping("/pageByCode")
    public R pageByCode(Page<CollectInfo> page, CollectInfo collectInfo) {
        return R.ok(collectInfoService.selectCollectPage(page, collectInfo));
    }

    @GetMapping("/collectExpert/check")
    public R collectExpert(@RequestParam("enterpriseCode") String enterpriseCode, @RequestParam("expertCode") String expertCode) {
        Integer count = collectInfoService.count(Wrappers.<CollectInfo>lambdaQuery().eq(CollectInfo::getEnterpriseCode, enterpriseCode).eq(CollectInfo::getExpertCode, expertCode));
        if (count >= 1) {
            return R.ok(false);
        }
        CollectInfo collectInfo = new CollectInfo();
        collectInfo.setEnterpriseCode(enterpriseCode);
        collectInfo.setExpertCode(expertCode);
        collectInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(collectInfoService.save(collectInfo));
    }

    /**
     * 根据企业编号获取收藏专家信息
     *
     * @param enterpriseCode 企业编号
     * @return 结果
     */
    @GetMapping("/list/{enterpriseCode}")
    public R selectCollectByEnterprise(@PathVariable("enterpriseCode") String enterpriseCode) {
        return R.ok(collectInfoService.selectCollectByEnterprise(enterpriseCode));
    }

    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(collectInfoService.getById(id));
    }

    @GetMapping("/list")
    public R list() {
        return R.ok(collectInfoService.list());
    }

    /**
     * 新增收藏信息
     *
     * @param collectInfo 收藏信息
     * @return 结果
     */
    @PostMapping
    public R save(CollectInfo collectInfo) {
        collectInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(collectInfoService.save(collectInfo));
    }

    /**
     * 修改收藏信息
     *
     * @param collectInfo 收藏信息
     * @return 结果
     */
    @PutMapping
    public R edit(CollectInfo collectInfo) {
        return R.ok(collectInfoService.updateById(collectInfo));
    }

    /**
     * 删除收藏信息
     *
     * @param ids ids
     * @return 收藏信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(collectInfoService.removeByIds(ids));
    }

}
