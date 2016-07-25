package com.footing.website.modules.luxclub.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.footing.website.common.persistence.CrudDao;
import com.footing.website.common.persistence.annotation.MyBatisDao;
import com.footing.website.modules.luxclub.entity.MessageNotify;

/**
 * 消息通知DAO接口
 * @author liuguoqing
 * @version 2016-03-15
 */
@MyBatisDao
public interface MessageNotifyDao extends CrudDao<MessageNotify> {
    
    
    List<MessageNotify> findAppListByStatus(@Param("state") int state, @Param("type")int type);

    /**
     * 更新消息数据
     * @param paramsMap
     */
    void updateMessage(Map<String, Object> paramsMap)throws Exception;

}