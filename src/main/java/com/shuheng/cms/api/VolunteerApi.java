package com.shuheng.cms.api;

import com.shuheng.cms.base.BaseApiController;
import com.shuheng.cms.entity.ApiConstants;
import com.shuheng.cms.entity.ApiResponse;
import com.shuheng.cms.entity.PageData;
import com.shuheng.cms.server.VolunteerService;
import com.shuheng.cms.utils.Const;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 志愿者网站相关API接口
 */
@RestController
@RequestMapping(value = "volunteer")
public class VolunteerApi extends BaseApiController {

    @Resource(name = "volunteerService")
    private VolunteerService volunteerService;


    /**
     * 是否包含标题图，0所有，1有，2没有  默认为所有并且返回值不带img
     */
    private static final String HAS_IMG = "has_img";

    /**
     * 是否有摘要, 0所有，1有，2没有  默认为所有
     */
    private static final String HAS_DIGEST = "has_digest";

    /**
     * 取前几条标识，如果不为空，则不分页,最大值为30
     */
    private static final String TOP_SIZE = "top_size";

    /**
     * 分页，每页多少条数据
     */
    private static final String PAGE_SIZE = "page_size";

    /**
     * 分页，页码，第几页
     */
    private static final String PAGE_NUM = "page_num";

    /**
     * 来源限制，0不限制，1协会2组织。 默认不限制,暂定只能传1，因为组织发的新闻质量参差不齐
     */
    private static final String FROM_LIMIT = "from_limit";


    /**
     * 志愿者风采列表
     * @param apiRes  province 省 city市  根据这两个限制数据区域  exclude_ids不包含的新闻
     * @return
     */
    @RequestMapping(value = "newsList")
    public ApiResponse newsList(ApiResponse apiRes){
        PageData pd = this.getPageInfo();
        String province = pd.getString("province");
        String topSize = pd.getString("top_size");
        String excludeIds = pd.getString("exclude_ids");


        try {
            if (StringUtils.isBlank(province)) {
                apiRes.setErrorCode(ApiConstants.CODE_201);
                return apiRes;
            }
            //top_size最大值30
            if (StringUtils.isNotBlank(topSize) && Integer.parseInt(topSize) > Const.INT_THIRTY) {
                pd.put("top_size", Const.INT_THIRTY);
            }

            //不包含的news_id
            if(StringUtils.isNotBlank(excludeIds) && excludeIds.contains(Const.COMMAT)){
                String[] excludeIdsArr = StringUtils.split(excludeIds, Const.COMMAT);
                //多栏目，去掉column_option
                if(excludeIdsArr!=null && excludeIdsArr.length > Const.INT_ONE){
                    pd.put("exclude_ids_in", true);
                    pd.put("exclude_ids_arr", excludeIdsArr);
                }
            }

            List<PageData> list = volunteerService.listNews(pd);
            if(StringUtils.isBlank(topSize)){
                //无top_size应该进行分页
                Long count = volunteerService.countListNews(pd);
                apiRes.put("count", count);
            }

            apiRes.setDatas(list);
            apiRes.setErrorCode(ApiConstants.CODE_200);

        }catch (Exception e) {
            apiRes.setErrorCode(ApiConstants.CODE_202);
            e.printStackTrace();
        }
        return apiRes;

    }

    /**
     * 新闻详情
     * @param apiRes
     * @return
     */
    @RequestMapping(value = "newsDetails")
    public ApiResponse newsDetails(ApiResponse apiRes){
        PageData pd = this.getPageData();
        String id = pd.getString("news_id");
        if(StringUtils.isBlank(id)){
            apiRes.setErrorCode(ApiConstants.CODE_201);
            return apiRes;
        }
        try {
            pd = volunteerService.newsDetails(pd);
            apiRes.setData(pd);
            apiRes.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            apiRes.setErrorCode(ApiConstants.CODE_202);
            e.printStackTrace();
        }
        return apiRes;
    }


    /**
     * 志愿者招募活动api接口
     * @param apiRes
     * @return
     */
    @RequestMapping("activeList")
    public ApiResponse activeList(ApiResponse apiRes){
        PageData pd = this.getPageInfo();
        String province = pd.getString("province");
        String topSize = pd.getString("top_size");
        try {
            if (StringUtils.isBlank(province)) {
                apiRes.setErrorCode(ApiConstants.CODE_201);
                return apiRes;
            }
            //top_size最大值30
            if (StringUtils.isNotBlank(topSize) && Integer.parseInt(topSize) > Const.INT_THIRTY) {
                pd.put("top_size", Const.INT_THIRTY);
            }

            List<PageData> list = volunteerService.listActive(pd);

            if(StringUtils.isBlank(topSize)){
                //无top_size应该进行分页
                Long count = volunteerService.countListActive(pd);
                apiRes.put("count", count);
            }
            apiRes.setDatas(list);
            //类型列表
            pd.put("key", "V_ACTIVE_TYPE");
            List<PageData> typeList = volunteerService.listActiveType(pd);
            apiRes.put("active_type",typeList);
            apiRes.setErrorCode(ApiConstants.CODE_200);
        }catch (Exception e) {
            apiRes.setErrorCode(ApiConstants.CODE_202);
            e.printStackTrace();
        }
        return apiRes;
    }

    /**
     * 活动详情
     * @param apiRes
     * @return
     */
    @RequestMapping(value = "activeDetails")
    public ApiResponse activeDetails(ApiResponse apiRes){
        PageData pd = this.getPageData();
        String activeId = pd.getString("active_id");
        if(StringUtils.isBlank(activeId)){
            apiRes.setErrorCode(ApiConstants.CODE_201);
        }

        try {
            pd = volunteerService.activeDetails(pd);
        } catch (Exception e) {
            apiRes.setErrorCode(ApiConstants.CODE_202);
            e.printStackTrace();
        }
        apiRes.setData(pd);
        apiRes.setErrorCode(ApiConstants.CODE_200);
        return apiRes;
    }

    /**
     * 公益讲堂列表
     * @param apiRes
     * @return
     */
    @RequestMapping(value = "classList")
    public ApiResponse classList(ApiResponse apiRes){
        PageData pd = this.getPageInfo();
        String province = pd.getString("province");
        String topSize = pd.getString("top_size");
        try {
            if (StringUtils.isBlank(province)) {
                apiRes.setErrorCode(ApiConstants.CODE_201);
                return apiRes;
            }
            //top_size最大值30
            if (StringUtils.isNotBlank(topSize) && Integer.parseInt(topSize) > Const.INT_THIRTY) {
                pd.put("top_size", Const.INT_THIRTY);
            }

            List<PageData> list = volunteerService.listClass(pd);

            if(StringUtils.isBlank(topSize)){
                //无top_size应该进行分页
                Long count = volunteerService.countListClass(pd);
                apiRes.put("count", count);
            }
            apiRes.setDatas(list);
            apiRes.setErrorCode(ApiConstants.CODE_200);

        }catch (Exception e) {
            apiRes.setErrorCode(ApiConstants.CODE_202);
            e.printStackTrace();
        }
        return apiRes;

    }


    /**
     * 新闻详情
     * @param apiRes
     * @return
     */
    @RequestMapping(value = "classDetails")
    public ApiResponse classDetails(ApiResponse apiRes){
        PageData pd = this.getPageData();
        String id = pd.getString("news_id");
        if(StringUtils.isBlank(id)){
            apiRes.setErrorCode(ApiConstants.CODE_201);
            return apiRes;
        }
        try {
            pd = volunteerService.classDetails(pd);
            apiRes.setData(pd);
            apiRes.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            apiRes.setErrorCode(ApiConstants.CODE_202);
            e.printStackTrace();
        }
        return apiRes;
    }

    /**
     * 组织列表
     * @param apiRes
     * @return
     */
    @RequestMapping(value = "organizationList")
    public ApiResponse organizationList(ApiResponse apiRes){
        PageData pd = this.getPageInfo();
        String province = pd.getString("province");
        String topSize = pd.getString("top_size");
        try {
            if (StringUtils.isBlank(province)) {
                apiRes.setErrorCode(ApiConstants.CODE_201);
                return apiRes;
            }
            //top_size最大值30
            if (StringUtils.isNotBlank(topSize) && Integer.parseInt(topSize) > Const.INT_THIRTY) {
                pd.put("top_size", Const.INT_THIRTY);
            }

            List<PageData> list = volunteerService.listOrganization(pd);

            if(StringUtils.isBlank(topSize)){
                //无top_size应该进行分页
                Long count = volunteerService.countListOrganization(pd);
                apiRes.put("count", count);
            }
            apiRes.setDatas(list);
            apiRes.setErrorCode(ApiConstants.CODE_200);

        }catch (Exception e) {
            apiRes.setErrorCode(ApiConstants.CODE_202);
            e.printStackTrace();
        }
        return apiRes;
    }

    /**
     * 组织详情
     * @param apiRes
     * @return
     */
    @RequestMapping(value = "orgDetails")
    public ApiResponse orgDetails(ApiResponse apiRes){
        PageData pd = this.getPageData();
        String id = pd.getString("organization_id");
        if(StringUtils.isBlank(id)){
            apiRes.setErrorCode(ApiConstants.CODE_201);
            return apiRes;
        }
        try {
            pd = volunteerService.orgDetails(pd);
            apiRes.setData(pd);

            //查询志愿服务之星
            List<PageData> list = volunteerService.listVolunteerStars(pd);
            apiRes.setDatas(list);
            apiRes.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            apiRes.setErrorCode(ApiConstants.CODE_202);
            e.printStackTrace();
        }
        return apiRes;
    }


    /**
     * 志愿者爱心榜
     * @param apiRes
     * @return
     */
    @RequestMapping("loveHourRank")
    public ApiResponse volunteerRank(ApiResponse apiRes){
        PageData pd = this.getPageInfo();
        String topSize = pd.getString("top_size");

        //top_size最大值30
        if (StringUtils.isNotBlank(topSize) && Integer.parseInt(topSize) > Const.INT_THIRTY) {
            pd.put("top_size", Const.INT_THIRTY);
        }
        try {
            List<PageData> list = volunteerService.volunteerProvinceRank(pd);
            apiRes.setDatas(list);

            Long count = volunteerService.countVolunteerRank(pd);
            apiRes.put("count",count);
            apiRes.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            apiRes.setErrorCode(ApiConstants.CODE_202);
            e.printStackTrace();
        }
        return apiRes;
    }

    /**
     * 组织爱心榜
     * @param apiRes
     * @return
     */
    @RequestMapping("orgRank")
    public ApiResponse orgRank(ApiResponse apiRes){
        PageData pd = this.getPageInfo();
        String topSize = pd.getString("top_size");

        //top_size最大值30
        if (StringUtils.isNotBlank(topSize) && Integer.parseInt(topSize) > Const.INT_THIRTY) {
            pd.put("top_size", Const.INT_THIRTY);
        }
        try {
            List<PageData> list = volunteerService.orgProvinceRankList(pd);
            apiRes.setDatas(list);

            Long count = volunteerService.countOrgRankList(pd);
            apiRes.put("count",count);
            apiRes.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            apiRes.setErrorCode(ApiConstants.CODE_202);
            e.printStackTrace();
        }
        return apiRes;
    }

}
