package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.EnterpriseInfo;
import cc.mrbird.febs.cos.entity.PostInfo;
import cc.mrbird.febs.cos.service.IEnterpriseInfoService;
import cc.mrbird.febs.cos.service.IPostInfoService;
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
@RequestMapping("/cos/post-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostInfoController {

    private final IPostInfoService postInfoService;

    private final IEnterpriseInfoService enterpriseInfoService;

    /**
     * 分页获取岗位信息
     *
     * @param page     分页对象
     * @param postInfo 岗位信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<PostInfo> page, PostInfo postInfo) {
        if (postInfo.getEnterpriseId() != null) {
            EnterpriseInfo enterpriseInfo = enterpriseInfoService.getOne(Wrappers.<EnterpriseInfo>lambdaQuery().eq(EnterpriseInfo::getUserId, postInfo.getEnterpriseId()));
            postInfo.setEnterpriseId(enterpriseInfo.getId());
        }
        return R.ok(postInfoService.selectPostPage(page, postInfo));
    }

    /**
     * 岗位信息上下架
     *
     * @param postId 岗位ID
     * @param status 状态
     * @return 结果
     */
    @GetMapping("/audit")
    public R audit(@RequestParam("postId") Integer postId, @RequestParam("status") Integer status) {
        return R.ok(postInfoService.update(Wrappers.<PostInfo>lambdaUpdate().set(PostInfo::getDelFlag, status).eq(PostInfo::getId, postId)));
    }


    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(postInfoService.getById(id));
    }

    @GetMapping("/list")
    public R list() {
        return R.ok(postInfoService.list());
    }

    /**
     * 新增岗位信息
     *
     * @param postInfo 岗位信息
     * @return 结果
     */
    @PostMapping
    public R save(PostInfo postInfo) {
        if (postInfo.getEnterpriseId() != null) {
            EnterpriseInfo enterpriseInfo = enterpriseInfoService.getOne(Wrappers.<EnterpriseInfo>lambdaQuery().eq(EnterpriseInfo::getUserId, postInfo.getEnterpriseId()));
            if (enterpriseInfo != null) {
                postInfo.setEnterpriseId(enterpriseInfo.getId());
            }
        }
        postInfo.setDelFlag(1);
        postInfo.setCode("PT-" + System.currentTimeMillis());
        postInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(postInfoService.save(postInfo));
    }

    /**
     * 修改岗位信息
     *
     * @param postInfo 岗位信息
     * @return 结果
     */
    @PutMapping
    public R edit(PostInfo postInfo) {
        return R.ok(postInfoService.updateById(postInfo));
    }

    /**
     * 删除岗位信息
     *
     * @param ids ids
     * @return 岗位信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(postInfoService.removeByIds(ids));
    }
}
