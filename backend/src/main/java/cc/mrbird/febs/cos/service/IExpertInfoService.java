package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.ExpertInfo;
import cc.mrbird.febs.cos.entity.vo.ExpertRecommendVo;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IExpertInfoService extends IService<ExpertInfo> {

    /**
     * 分页获取专家信息
     *
     * @param page       分页对象
     * @param expertInfo 专家信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectExpertPage(Page<ExpertInfo> page, ExpertInfo expertInfo);

    /**
     * 分页获取游客信息
     *
     * @param page     分页对象
     * @param user 对象
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectUserPage(Page<User> page, User user);

    /**
     * 根据企业编号获取推荐专家
     *
     * @param enterpriseCode 企业编号
     * @return 结果
     */
    List<ExpertInfo> selectExpertRecommend(String enterpriseCode);

    /**
     * 导入专家信息列表
     *
     * @param file 文件
     * @return 结果
     */
    String importExcel(MultipartFile file) throws Exception;

    /**
     * 校验专家编号
     *
     * @param expertCode 专家编号
     * @return 结果
     */
    boolean checkExpert(String expertCode);

    /**
     * 专家注册
     *
     * @param expertCode 专家编号
     * @return 结果
     */
    String expertRegister(String expertCode);
}
