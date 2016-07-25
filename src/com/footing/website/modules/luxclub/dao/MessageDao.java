package com.footing.website.modules.luxclub.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.footing.website.common.persistence.CrudDao;
import com.footing.website.common.persistence.annotation.MyBatisDao;
import com.footing.website.modules.luxclub.api.response.MessageResp;
import com.footing.website.modules.luxclub.entity.MessageNotify;

/**
 * 客户经理-消息DAO接口
 * @author liuguoqing
 *
 */
@MyBatisDao
public interface MessageDao extends CrudDao<MessageNotify>{
    
    /**
     * 根据登录名称查询用户
     * @param loginName
     * @return
     */
    public List<MessageResp> messagePageList(@Param("receiver")String receiver, @Param("startNumber") int startNumber,@Param("endNumber") int endNumber);
    
    /**
     * 批量更新消息状态
     * @param ids
     * @param status
     */
    public int batchUpdateStatus(@Param("ids")String[] ids, @Param("state")int state);
    
    
    /**
     * 获取未读消息数量
     * @param receiver 接收人
     * @return
     */
    public int getUnreadCount(@Param("receiver")String receiver);

}
