package com.shuheng.cms.server;

import com.shuheng.cms.dao.DaoSupport;
import com.shuheng.cms.entity.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("volunteerService")
public class VolunteerService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 志愿者风采新闻列表
     * @param pd
     * @return
     */
    public List<PageData> listNews(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("VolunteerMapper.listNews",pd);
    }

    /**
     * 志愿者风采新闻数量
     * @param pd
     * @return
     */
    public Long countListNews(PageData pd) throws Exception {
        return (Long) dao.findForObject("VolunteerMapper.countListNews",pd);
    }

    /**
     * 志愿者招募活动列表
     * @param pd
     * @return
     */
    public List<PageData> listActive(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("VolunteerMapper.listActive",pd);
    }

    /**
     * 志愿者招募活动数量
     * @param pd
     * @return
     */
    public Long countListActive(PageData pd) throws Exception {
        return (Long) dao.findForObject("VolunteerMapper.countListActive",pd);
    }

    /**
     * 公益讲堂列表
     * @param pd
     * @return
     */
    public List<PageData> listClass(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("VolunteerMapper.listClass",pd);
    }

    /**
     * 公益讲堂数量
     * @param pd
     * @return
     */
    public Long countListClass(PageData pd) throws Exception {
        return (Long) dao.findForObject("VolunteerMapper.countListClass",pd);
    }

    /**
     * 组织列表
     * @param pd
     * @return
     */
    public List<PageData> listOrganization(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("VolunteerMapper.listOrganization",pd);
    }

    /**
     * 组织列表数量
     * @param pd
     * @return
     */
    public Long countListOrganization(PageData pd) throws Exception {
        return (Long) dao.findForObject("VolunteerMapper.countListOrganization",pd);
    }

    /**
     * 新闻详情
     * @param pd
     * @return
     */
    public PageData newsDetails(PageData pd) throws Exception {
        return (PageData) dao.findForObject("VolunteerMapper.newsDetails",pd);
    }

    /**
     * 公益讲堂详情
     * @param pd
     * @return
     */
    public PageData classDetails(PageData pd) throws Exception {
        return (PageData) dao.findForObject("VolunteerMapper.classDetails",pd);
    }

    /**
     * 组织详情
     * @param pd
     * @return
     */
    public PageData orgDetails(PageData pd) throws Exception {
        return (PageData) dao.findForObject("VolunteerMapper.orgDetails",pd);
    }

    /**
     * 组织的志愿服务之星
     * @param pd
     * @return
     */
    public List<PageData> listVolunteerStars(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("VolunteerMapper.listVolunteerStars",pd);
    }

    /**
     * 活动招募详情
     * @param pd
     * @return
     */
    public PageData activeDetails(PageData pd) throws Exception {
        return (PageData) dao.findForObject("VolunteerMapper.activeDetails",pd);
    }

    /**
     * 活动类型列表
     * @param pd
     * @return
     */
    public List<PageData> listActiveType(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("VolunteerMapper.listActiveType",pd);
    }

    /**
     * 志愿者全省排名
     * @return
     * @throws Exception
     */
    public List<PageData> volunteerProvinceRank(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("VolunteerMapper.volunteerProvinceRank", pd);
    }

    public Long countVolunteerRank(PageData pd) throws Exception {
        return (Long) dao.findForObject("VolunteerMapper.countVolunteerRank",pd);
    }

    /**
     * 组织全省排名
     * @param vPd
     * @return
     * @throws Exception
     */
    public List<PageData> orgProvinceRankList(PageData vPd) throws Exception {
        return (List<PageData>) dao.findForList("VolunteerMapper.orgProvinceRankList", vPd);
    }


    public Long countOrgRankList(PageData pd) throws Exception {
        return (Long) dao.findForObject("VolunteerMapper.countOrgRankList",pd);
    }
}
