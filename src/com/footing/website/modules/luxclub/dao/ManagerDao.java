package com.footing.website.modules.luxclub.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.footing.website.common.persistence.CrudDao;
import com.footing.website.common.persistence.annotation.MyBatisDao;
import com.footing.website.modules.entity.User;

/**
 * 客户经理DAO接口
 * @author liuguoqing
 *
 */
@MyBatisDao
public interface ManagerDao extends CrudDao<User>{
    
    /**
     * 根据登录名称查询用户
     * @param loginName
     * @return
     */
    public User getByLoginName(String loginName);
    
    /**
     * 更新登录信息, 如登录时间
     * @param loginDate
     * @return
     */
    public boolean updateLoginInfo(@Param("id")Long id,@Param("terminalType")int terminalType, @Param("loginDate")Date loginDate);
    
    public boolean updatePassword(@Param("id")Long id, @Param("password") String password);

}
