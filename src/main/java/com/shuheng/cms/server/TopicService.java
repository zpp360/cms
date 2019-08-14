package com.shuheng.cms.server;

import com.shuheng.cms.dao.DaoSupport;
import com.shuheng.cms.entity.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("topicService")
public class TopicService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;


    /**
     * 专题详情
     * @param pd
     * @return
     * @throws Exception
     */
    public PageData topicDetails(PageData pd) throws Exception {
        return (PageData) dao.findForObject("TopicMapper.topicDetails", pd);
    }
    /**
     * 根据
     * @param pd
     * @return
     * @throws Exception
     */
    public List<PageData> listColumnByTopic(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("TopicMapper.listColumnByTopic", pd);
    }

    /**
     * 专题列表
     * @param pd
     * @return
     * @throws Exception
     */
    public List<PageData> listTopic(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("TopicMapper.listTopic", pd);
    }
    /**
     * 专题数量，分页查询使用
     * @param pd
     * @return
     * @throws Exception
     */
    public Long countListTopic(PageData pd) throws Exception {
        return (Long) dao.findForObject("TopicMapper.countListTopic", pd);
    }
}
