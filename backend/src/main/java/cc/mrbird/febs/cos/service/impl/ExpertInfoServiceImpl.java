package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.EnterpriseInfoMapper;
import cc.mrbird.febs.cos.dao.InterviewInfoMapper;
import cc.mrbird.febs.cos.entity.EnterpriseInfo;
import cc.mrbird.febs.cos.entity.ExpertInfo;
import cc.mrbird.febs.cos.dao.ExpertInfoMapper;
import cc.mrbird.febs.cos.entity.InterviewInfo;
import cc.mrbird.febs.cos.service.IExpertInfoService;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExpertInfoServiceImpl extends ServiceImpl<ExpertInfoMapper, ExpertInfo> implements IExpertInfoService {

    private final EnterpriseInfoMapper enterpriseInfoMapper;

    private final InterviewInfoMapper interviewInfoMapper;

    /**
     * 分页获取专家信息
     *
     * @param page       分页对象
     * @param expertInfo 专家信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectExpertPage(Page<ExpertInfo> page, ExpertInfo expertInfo) {
        return baseMapper.selectExpertPage(page, expertInfo);
    }

    /**
     * 分页获取游客信息
     *
     * @param page     分页对象
     * @param user 对象
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectUserPage(Page<User> page, User user) {
        return baseMapper.selectUserPage(page, user);
    }

    /**
     * 根据求职者获取面试信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectInterByExpert(Integer userId) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("post", Collections.emptyList());
                put("pluralism", Collections.emptyList());
            }
        };
        if (userId == null) {
            return result;
        }
        // 获取求职者信息
        ExpertInfo expertInfo = this.getOne(Wrappers.<ExpertInfo>lambdaQuery().eq(ExpertInfo::getUserId, userId));
        if (expertInfo == null) {
            return result;
        }

        // 获取面试信息
        List<InterviewInfo> interviewList = interviewInfoMapper.selectList(Wrappers.<InterviewInfo>lambdaQuery().eq(InterviewInfo::getExpertId, expertInfo.getId()));
        if (CollectionUtil.isEmpty(interviewList)) {
            return result;
        }
        // 根据类型转MAP
        Map<Integer, List<InterviewInfo>> integerMap = interviewList.stream().collect(Collectors.groupingBy(InterviewInfo::getType));
        for (Integer interType : integerMap.keySet()) {
            switch (interType) {
                case 1:
                    List<Integer> interPluralismIds = integerMap.get(interType).stream().map(InterviewInfo::getId).collect(Collectors.toList());
                    result.put("pluralism", interviewInfoMapper.selectInterViewPluralismByIds(interPluralismIds));
                    break;
                case 2:
                    List<Integer> interPostIds = integerMap.get(interType).stream().map(InterviewInfo::getId).collect(Collectors.toList());
                    result.put("post", interviewInfoMapper.selectInterViewPostByIds(interPostIds));
                    break;
                default:
            }
        }

        return result;
    }

    /**
     * 根据企业编号获取推荐专家
     *
     * @param enterpriseCode 企业编号
     * @return 结果
     */
    @Override
    public List<ExpertInfo> selectExpertRecommend(String enterpriseCode) {
        // 获取企业信息
        EnterpriseInfo enterpriseInfo = enterpriseInfoMapper.selectOne(Wrappers.<EnterpriseInfo>lambdaQuery().eq(EnterpriseInfo::getCode, enterpriseCode));
        if (enterpriseInfo == null) {
            return Collections.emptyList();
        }
        // 查找推荐专家
        return this.list(Wrappers.<ExpertInfo>lambdaQuery()
                .like(StrUtil.isNotEmpty(enterpriseInfo.getIndustry()), ExpertInfo::getLevelOne, enterpriseInfo.getIndustry())
                .or()
                .like(StrUtil.isNotEmpty(enterpriseInfo.getIndustry()), ExpertInfo::getLevelTwo, enterpriseInfo.getIndustry())
                .eq(ExpertInfo::getOpenFlag, 1));
    }

    /**
     * 导入专家信息列表
     *
     * @param file 文件
     * @return 结果
     */
    @Override
    public String importExcel(MultipartFile file) throws Exception {
        ExcelReader excelReader = ExcelUtil.getReader(file.getInputStream(), 0);
        setExcelHeaderAlias(excelReader);
        List<ExpertInfo> reports = excelReader.read(1, 2, Integer.MAX_VALUE, ExpertInfo.class);
        StringBuilder error = new StringBuilder("");
        if (CollectionUtil.isEmpty(reports)) {
            error.append("导入数据不得为空。");
            return error.toString();
        }
        for (ExpertInfo expert : reports) {
            if (StrUtil.isEmpty(expert.getName())) {
                error.append("\n名称不能为空");
                return error.toString();
            }
            expert.setCode("EX-" + System.currentTimeMillis());
            expert.setOpenFlag(1);
            expert.setHasExist(0);
            expert.setCreateDate(DateUtil.formatDate(new Date()));
        }
        if (StrUtil.isEmpty(error.toString())) {
            this.saveBatch(reports);
            return null;
        }
        return error.toString();
    }

    /**
     * 校验专家编号
     *
     * @param expertCode 专家编号
     * @return 结果
     */
    @Override
    public boolean checkExpert(String expertCode) {
        return this.getOne(Wrappers.<ExpertInfo>lambdaQuery().eq(ExpertInfo::getCode, expertCode)) != null;
    }

    /**
     * 专家注册
     *
     * @param expertCode 专家编号
     * @return 结果
     */
    @Override
    public String expertRegister(String expertCode) {
        if (StrUtil.isEmpty(expertCode)) {
            ExpertInfo expertInfo = new ExpertInfo();
            expertInfo.setHasExist(1);
            expertInfo.setOpenFlag(1);
            expertInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            expertInfo.setCode("EX-" + System.currentTimeMillis());
            this.save(expertInfo);
            return expertInfo.getCode();
        }
        this.update(Wrappers.<ExpertInfo>lambdaUpdate().set(ExpertInfo::getHasExist, 1).eq(ExpertInfo::getCode, expertCode));
        return expertCode;
    }

    /**
     * 设置HeaderAlias
     *
     * @param excelReader HeaderAlias
     */
    public void setExcelHeaderAlias(ExcelReader excelReader) {
        excelReader.addHeaderAlias("姓名", "name");
        excelReader.addHeaderAlias("民族", "nationality");
        excelReader.addHeaderAlias("性别", "sex");
        excelReader.addHeaderAlias("政治面貌", "politicalStatus");
        excelReader.addHeaderAlias("籍贯", "nativePlace");
        excelReader.addHeaderAlias("工作单位", "employer");
        excelReader.addHeaderAlias("职务", "position");
        excelReader.addHeaderAlias("通讯地址", "address");
        excelReader.addHeaderAlias("手机号", "phone");
        excelReader.addHeaderAlias("专业方向一级", "levelOne");
        excelReader.addHeaderAlias("专业方向二级", "levelTwo");
        excelReader.addHeaderAlias("出生日期", "birthDate");
        excelReader.addHeaderAlias("职称", "jobTitle");
        excelReader.addHeaderAlias("特殊称谓", "specialAppellation");
        excelReader.addHeaderAlias("邮箱", "mail");
        excelReader.addHeaderAlias("固定电话", "fixedTelephone");
        excelReader.addHeaderAlias("个人简介", "profile");
    }
}
