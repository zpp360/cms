package com.shuheng.cms.server;

import com.shuheng.cms.dao.DaoSupport;
import com.shuheng.cms.entity.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("friendLinkService")
public class FriendLinkService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;


    /**
     * 根据单位id和分类id查询友情链接列表
     * @param pd
     * @return
     * @throws Exception
     */
    public List<PageData> listFriendLink(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("FriendLinkMapper.listFriendLink", pd);
    }
}
