package com.footing.website.modules.luxclub.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.footing.website.common.persistence.CrudDao;
import com.footing.website.common.persistence.annotation.MyBatisDao;
import com.footing.website.modules.luxclub.entity.CustomerClientToken;

/**
 * 客户端缓存信息DAO接口
 * @author lzb
 * @version 2015-10-14
 */
@MyBatisDao
public interface CustomerClientTokenDao extends CrudDao<CustomerClientToken> {
	/**
	 * 根据token获取有效的缓存信息
	 * @param token
	 * @param date
	 * @return
	 */
	CustomerClientToken getByTokenAndTheDate(@Param("token")String token, @Param("theDate")Date date);
	
	/**
	 * 根据账号及终端获取缓存信息
	 * @param account
	 * @param termType
	 * @return
	 */
	CustomerClientToken getClientTokenByAccountAndTermType(@Param("account")String account, @Param("termType")String termType);
	
	/**
	 * 根据token查询
	 * @param token
	 * @return
	 */
	CustomerClientToken getByToken(@Param("token")String token);
	
	/**
	 * 根据会员卡号获取缓存信息
	 * @param account
	 * @return
	 */
	List<CustomerClientToken> getCustomerClientTokenList(String account);
}