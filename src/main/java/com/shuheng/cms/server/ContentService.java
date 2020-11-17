package com.shuheng.cms.server;

import com.shuheng.cms.dao.DaoSupport;
import com.shuheng.cms.entity.PageData;
import com.shuheng.cms.utils.Const;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("contentService")
public class ContentService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 网站内容列表
     * @param pd
     * @return
     * @throws Exception
     */
    public List<PageData> listContent(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ContentMapper.listContent", pd);
    }

    /**
     * 网站内容列表数量
     * @param pd
     * @return
     * @throws Exception
     */
    public Long countListContent(PageData pd) throws Exception {
        return (Long) dao.findForObject("ContentMapper.countListContent", pd);
    }

    /**
     * 下级推荐新闻，市直动态和区市动态
     * @param pd
     * @return
     * @throws Exception
     */
    public List<PageData> listUpContent(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ContentMapper.listUpContent", pd);
    }
    /**
     * 下级推荐新闻数量
     * @param pd
     * @return
     * @throws Exception
     */
    public Long countListUpContent(PageData pd) throws Exception {
        return (Long) dao.findForObject("ContentMapper.countListUpContent", pd);
    }


    /**
     * 网站内容详情
     * @param pd
     * @return
     * @throws Exception
     */
    public PageData newsDetails(PageData pd) throws Exception {
        return (PageData) dao.findForObject("ContentMapper.newsDetails", pd);
    }
    /**
     *  家园号列表，按新闻发布时间排序
     * @return
     * @throws Exception
     */
    public List<PageData> homeNumberList() throws Exception {
        return (List<PageData>) dao.findForList("ContentMapper.homeNumberList", null);
    }

    /**
     * 阅读数++
     * @param pd
     * @return
     */
    public PageData views(PageData pd) throws Exception {
        dao.update("ContentMapper.updateViews", pd);
        pd.put("views",(long)pd.get("views")+1);
        return pd;
    }

    public void ups(PageData pd) throws Exception {
        dao.update("ContentMapper.ups",pd);
    }
}
