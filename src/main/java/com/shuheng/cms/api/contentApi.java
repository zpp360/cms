package com.shuheng.cms.api;

import com.shuheng.cms.base.BaseApiController;
import com.shuheng.cms.entity.ApiConstants;
import com.shuheng.cms.entity.ApiResponse;
import com.shuheng.cms.entity.PageData;
import com.shuheng.cms.server.ContentService;
import com.shuheng.cms.server.FriendLinkService;
import com.shuheng.cms.server.TopicService;
import com.shuheng.cms.utils.Const;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 内容api
 */
@RestController
public class contentApi extends BaseApiController{
    @Resource(name = "contentService")
    private ContentService contentService;

    @Resource(name = "friendLinkService")
    private FriendLinkService friendLinkService;

    @Resource(name = "topicService")
    private TopicService topicService;


    /**
     * unit_id单位id，允许多个单位id，用","分割
     */
    private static final String UNIT_ID = "unit_id";

    /**
     * column_id栏目id，允许多个栏目id，用","分割，如果是多栏目则不包含下级栏目
     */
    private static final String COLUMN_ID = "column_id";

    /**
     * 栏目项，是否包含下级栏目 0不包含  1包含  默认不包含
     */
    private static final String COLUMN_OPTION = "column_option";

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
     * 排序，
     * 0发布时间降序
     * 1发布时间升序
     *
     * 2添加时间降序
     * 3添加时间升序
     *
     * 4更新时间降序
     * 5更新时间升序
     *
     * 6置顶级别降序,然后按照发布时间降序
     * 7置顶级别升序,然后按照发布时间降序
     *
     * 默认为发布时间降序
     */
    private static final String ORDER_BY = "order_by";

    /**
     * 如果一个上级单位，例如卫健委想建网站，卫健委下的医院把新闻推荐到上级后，卫健委接收才能在网站显示
     * 内容列表
     * @param apiRes   unit_id单位id column_id栏目id  has_img是否有标题图  has_digest是否有摘要
     * top_size取前几条，有此参数不分页     page_size每页数量， page_number页码
     * group_by不为空，根据名称去重，适用于轮播图
     * keyword模糊查询标题关键字
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/contentList")
    public ApiResponse contentList(ApiResponse apiRes){
        PageData pd = this.getPageData();
        pd = this.getPageInfo(pd);
        String unitId = pd.getString("unit_id");
        String columnId = pd.getString("column_id");
        String topSize = pd.getString("top_size");
        try {
            if(StringUtils.isBlank(unitId)){
                apiRes.setErrorCode(ApiConstants.CODE_201);
                return apiRes;
            }
            //top_size最大值30
            if(StringUtils.isNotBlank(topSize) && Integer.parseInt(topSize) > Const.INT_THIRTY){
                pd.put("top_size", Const.INT_THIRTY);
            }

            if(unitId.contains(Const.COMMAT)){
                pd.put("unit_ids", unitId.split(Const.COMMAT));
                pd.put("unit_in", true);
            }

            if(StringUtils.isNotBlank(columnId) && columnId.contains(Const.COMMAT)){
                String[] colunmIds = StringUtils.split(columnId, Const.COMMAT);
                //多栏目，去掉column_option
                if(colunmIds!=null && colunmIds.length > Const.INT_ONE){
                    pd.remove(COLUMN_OPTION);
                    pd.put("column_in", true);
                    pd.put("column_ids", colunmIds);
                }
            }

            List<PageData> list = contentService.listContent(pd);
            if(StringUtils.isBlank(topSize)){
                //无top_size应该进行分页
                Long count = contentService.countListContent(pd);
                apiRes.put("count", count);
            }
            apiRes.setDatas(list);
            apiRes.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            apiRes.setErrorCode(ApiConstants.CODE_202);
            e.printStackTrace();
        }
        return apiRes;
    }

    /**
     * 下级推荐新闻
     * 市直动态 param:3 和区市动态 param:4,5,6
     * param unit_id 省委老干部局单位id
     * param unit_type 下级单位类型 不传显示全部
     * param exclude_unit 排除的单位
     * @return
     */
    @RequestMapping(value = "/listUpContent")
    @ResponseBody
    public ApiResponse listUpContent(ApiResponse apiRes){
        PageData pd = this.getPageData();
        pd = this.getPageInfo(pd);
        String unitId = pd.getString("unit_id");
        String topSize = pd.getString("top_size");
        String unitType = pd.getString("unit_type");
        String excludeUnit = pd.getString("exclude_unit");
        try {
            if(StringUtils.isBlank(unitId)){
                apiRes.setErrorCode(ApiConstants.CODE_201);
                return apiRes;
            }
            //top_size最大值30
            if(StringUtils.isNotBlank(topSize) && Integer.parseInt(topSize) > Const.INT_THIRTY){
                pd.put("top_size", Const.INT_THIRTY);
            }

            if(unitId.contains(Const.COMMAT)){
                pd.put("unit_ids", unitId.split(Const.COMMAT));
                pd.put("unit_in", true);
            }
            //单位类型不为空
            if(StringUtils.isNotBlank(unitType) && unitType.contains(Const.COMMAT)){
                String[] unitTypes = StringUtils.split(unitType, Const.COMMAT);
                //多栏目，去掉column_option
                if(unitTypes!=null && unitTypes.length > Const.INT_ONE){
                    pd.put("unit_type_in", true);
                    pd.put("unit_type_ids", unitTypes);
                }
            }
            //不包含的单位
            if(StringUtils.isNotBlank(excludeUnit) && excludeUnit.contains(Const.COMMAT)){
                String[] excludeUnits = StringUtils.split(excludeUnit, Const.COMMAT);
                //多栏目，去掉column_option
                if(excludeUnits!=null && excludeUnits.length > Const.INT_ONE){
                    pd.put("exclude_unit_in", true);
                    pd.put("exclude_unit_ids", excludeUnits);
                }
            }

            List<PageData> list = contentService.listUpContent(pd);
            if(StringUtils.isBlank(topSize)){
                //无top_size应该进行分页
                Long count = contentService.countListUpContent(pd);
                apiRes.put("count", count);
            }
            apiRes.setDatas(list);
            apiRes.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            apiRes.setErrorCode(ApiConstants.CODE_202);
            e.printStackTrace();
        }
        return apiRes;
    }

    /**
     * 内容详情
     * news模型的内容，video模型的内容和download模型的内容
     * @param apiRes
     * @return
     */
    @RequestMapping(value = "/newsDetails")
    @ResponseBody
    public ApiResponse newsDetails(ApiResponse apiRes){
        PageData  pd = this.getPageData();
        String id = pd.getString("news_id");
        String model = pd.getString("news_model");
        if(StringUtils.isBlank(id) || StringUtils.isBlank(model)){
            apiRes.setErrorCode(ApiConstants.CODE_201);
            return apiRes;
        }
        try {
            pd = contentService.newsDetails(pd);
            apiRes.setData(pd);
            apiRes.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            apiRes.setErrorCode(ApiConstants.CODE_202);
            e.printStackTrace();
        }
        return apiRes;
    }

    /**
     * 友情链接列表
     * has_img是否有图片  0所有，1有，2没有。默认为所有
     * top_size取前多少条
     * 按照排序号码降序排序
     * @param apiRes
     * @return
     */
    @RequestMapping(value = "/friendlinkList")
    @ResponseBody
    public ApiResponse friendlinkList(ApiResponse apiRes){
        PageData  pd = this.getPageData();
        String unitId = pd.getString("unit_id");
        String category = pd.getString("link_category");
        String topSize = pd.getString("top_size");
        if(StringUtils.isBlank(unitId) || StringUtils.isBlank(category)){
            apiRes.setErrorCode(ApiConstants.CODE_201);
            return apiRes;
        }

        //top_size最大值30
        if(StringUtils.isNotBlank(topSize) && Integer.parseInt(topSize) > Const.INT_THIRTY){
            pd.put("top_size", Const.INT_THIRTY);
        }

        try {
            List<PageData> list = friendLinkService.listFriendLink(pd);
            apiRes.setDatas(list);
            apiRes.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            apiRes.setErrorCode(ApiConstants.CODE_202);
            e.printStackTrace();
        }
        return apiRes;
    }

    /**
     * 专题列表
     * unit_id单位id，多个单位id用“,”分割
     * order_by
     * 2添加时间降序
     * 3添加时间升序
     *
     * 4更新时间降序
     * 5更新时间升序
     * top_size
     * @param apiRes
     * @return
     */
    @RequestMapping(value = "topicList")
    @ResponseBody
    public ApiResponse topicList(ApiResponse apiRes){
        PageData pd = this.getPageData();
        pd = this.getPageInfo(pd);
        String unitId = pd.getString("unit_id");
        String topSize = pd.getString("top_size");
        if(StringUtils.isBlank(unitId)){
            apiRes.setErrorCode(ApiConstants.CODE_201);
            return apiRes;
        }
        try {
            if(unitId.contains(Const.COMMAT)){
                pd.put("unit_ids", unitId.split(Const.COMMAT));
                pd.put("unit_in", true);
            }
            List<PageData> list = topicService.listTopic(pd);
            if(StringUtils.isBlank(topSize)){
                //无top_size应该进行分页
                Long count = topicService.countListTopic(pd);
                apiRes.put("count", count);
            }
            apiRes.setDatas(list);
            apiRes.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            apiRes.setErrorCode(ApiConstants.CODE_202);
            e.printStackTrace();
        }
        return apiRes;
    }

    /**
     * 专题
     * @param apiRes
     * @return
     */
    @RequestMapping(value = "/topicDetails")
    @ResponseBody
    public ApiResponse topic(ApiResponse apiRes){
        PageData  pd = this.getPageData();
        String topicId = pd.getString("topic_id");
        if(StringUtils.isBlank(topicId)){
            apiRes.setErrorCode(ApiConstants.CODE_201);
            return apiRes;
        }
        try {
            pd = topicService.topicDetails(pd);
            if(pd!=null){
                List<PageData> list = topicService.listColumnByTopic(pd);
                pd.put("column_list", list);
            }
            apiRes.setData(pd);
            apiRes.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            apiRes.setErrorCode(ApiConstants.CODE_202);
            e.printStackTrace();
        }
        return apiRes;
    }

}
