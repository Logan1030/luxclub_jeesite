package com.footing.website.modules.luxclub.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footing.website.common.persistence.Page;
import com.footing.website.common.service.CrudService;
import com.footing.website.modules.luxclub.dao.CustomerClientTokenDao;
import com.footing.website.modules.luxclub.entity.CustomerClientToken;
import com.footing.website.modules.luxclub.utils.CustomerClientUtils;

/**
 * 客户端缓存信息Service
 * @author lzb
 * @version 2015-10-14
 */
@Service
@Transactional(readOnly = true)
public class CustomerClientTokenService extends CrudService<CustomerClientTokenDao, CustomerClientToken> {
	@Autowired
	private CustomerClientTokenDao customerClientTokenDao;
//	@Autowired
//	private CustomerPushSwitchDao customerPushSwitchDao;

	public CustomerClientToken get(Long id) {
		return super.get(id);
	}
	
	public List<CustomerClientToken> findList(CustomerClientToken customerClientToken) {
		return super.findList(customerClientToken);
	}
	
	public Page<CustomerClientToken> findPage(Page<CustomerClientToken> page, CustomerClientToken customerClientToken) {
		return super.findPage(page, customerClientToken);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerClientToken customerClientToken) {
		super.save(customerClientToken);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerClientToken customerClientToken) {
		super.delete(customerClientToken);
	}
	
	/**
	 * 更新
	 * @param customerClientToken
	 */
	@Transactional(readOnly = false)
	public int update(CustomerClientToken customerClientToken) {
		return customerClientTokenDao.update(customerClientToken);
	}
	/**
	 * 根据token查询
	 * @param token
	 * @return
	 */
	public CustomerClientToken getByToken(String token) {
		return customerClientTokenDao.getByToken(token);
	}
	
	/**
	 * 绑卡操作缓存信息
	 * @param customerClientToken
	 */
	@Transactional(readOnly = false)
	public String operaCustomerClientToken(CustomerClientToken customerClientToken) {
		CustomerClientToken customerClient = CustomerClientUtils.getByAccount(customerClientToken.getAccount());
		if(customerClient == null ){
			customerClient = customerClientTokenDao.getClientTokenByAccountAndTermType(customerClientToken.getAccount(),customerClientToken.getTermType());
		}
		if(customerClient == null ){
			customerClientTokenDao.insert(customerClientToken);
			CustomerClientUtils.addCache(customerClientToken);
		}else{
			customerClient.setLastDate(new Date());
			customerClientTokenDao.update(customerClient);
			CustomerClientUtils.addCache(customerClient);
			customerClientToken.setToken(customerClient.getToken());
		}
		//初始化push消息开关
		//initPushSwitch(customerClientToken.getCustomerId(), opTerm);
		String token = customerClientToken.getToken();
		return token;
	}
	
	public List<CustomerClientToken> getCustomerClientTokenList(String cardno){
	    return customerClientTokenDao.getCustomerClientTokenList(cardno);
	}
	
//	/**
//	 * 初始化push消息开关
//	 * @param accountId
//	 * @param pushChannel
//	 */
//	public void initPushSwitch(long accountId, String pushChannel) {
//		if (ProjectConstant.OP_TERM_DICT_ANDROID.equals(pushChannel)
//				|| ProjectConstant.OP_TERM_DICT_IOS.equals(pushChannel)) {
//			CustomerPushSwitch pushSwitch = customerPushSwitchDao
//					.getCustomerPushSwitch(accountId, pushChannel);
//			if (pushSwitch == null) {
//				logger.info("the accountId:"+accountId+" initPushSwitch...");
//				CustomerPushSwitch customerPushSwitch = new CustomerPushSwitch();
//				customerPushSwitch.setAccountId(accountId);
//				customerPushSwitch.setPushChannel(pushChannel);
//				customerPushSwitch.setIsReceive(ApiConstant.PUSH_SWITCH_YES);
//				customerPushSwitchDao.insert(customerPushSwitch);
//			}
//		}
//	}
	
}