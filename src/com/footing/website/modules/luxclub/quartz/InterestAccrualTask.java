package com.footing.website.modules.luxclub.quartz;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footing.website.modules.luxclub.common.BusinessConstants;
import com.footing.website.modules.luxclub.common.MemberHisOpreationType;
import com.footing.website.modules.luxclub.common.WalletFeeType;
import com.footing.website.modules.luxclub.entity.MemberInfo;
import com.footing.website.modules.luxclub.entity.MemberInfoHis;
import com.footing.website.modules.luxclub.entity.WalletFeeRecord;
import com.footing.website.modules.luxclub.service.MemberInfoHisService;
import com.footing.website.modules.luxclub.service.MemberInfoService;
import com.footing.website.modules.luxclub.service.WalletFeeRecordService;

/**
 * 
 * <p>
 * Description:会员的零钱按照年化3.68%进行计息<br />
 * </p>
 * @title InterestAccrualQuartz.java
 * @package com.footing.website.quartz 
 * @author yubin
 * @version 0.1 2016年4月8日
 */
@Transactional(readOnly = true)
@Service 
@Lazy(false)
public class InterestAccrualTask {
	
	private  final Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private MemberInfoHisService memberInfoHisService;
	@Autowired
	private WalletFeeRecordService walletFeeRecordService;
	
	@Scheduled(cron = "0 0 0 * * ?")
	@Transactional(readOnly = false)
	public void doInterestAccrual(){
		   logger.info("会员的零钱按照年化进行计息任务开始");
		   Date startTime = new Date();
	       List<MemberInfo>memberInfos=memberInfoService.findListbyInterestAccrual();
	       try {
		       for(MemberInfo memberInfo:memberInfos){
		    	   BigDecimal interest=memberInfo.getWalletBalance()
		    			                         .multiply(BusinessConstants.YEAR_INTEREST)
		    			                         .divide(new BigDecimal(100*365),2,5);
		    	   memberInfo.setWalletBalance(memberInfo.getWalletBalance().add(interest));
		    	   memberInfo.setWalletProfit(memberInfo.getWalletProfit().add(interest));
		    	   memberInfo.setWalletLastProfit(interest);
				   updateMemberinfo(memberInfo,interest);
				   saveWalletFeeRecord(memberInfo, interest);
		       }
	       } catch (Exception e) {
	    	   logger.error("", e);
	       }
	       Date endTime = new Date();
	       logger.info("总共花费时间：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
	}
	@Transactional(readOnly = false)
	public void updateMemberinfo(MemberInfo memberInfo,BigDecimal interest)throws Exception{
		memberInfoService.save(memberInfo);
		MemberInfoHis memberInfoHis=new MemberInfoHis();
		memberInfo.setId(null);
		memberInfoHis.setOperationType(MemberHisOpreationType.WALLET_INTREEST_ACCURAL);
		BeanUtils.copyProperties(memberInfoHis, memberInfo);
		memberInfoHis.setOperationDate(new Date());
		memberInfoHis.setRemarks("利息结算完成后自动加入用户的零钱包");
		memberInfoHis.setWalletAmount(interest);
		memberInfoHisService.save(memberInfoHis);
	} 
	@Transactional(readOnly = false)
	public void saveWalletFeeRecord(MemberInfo memberInfo,BigDecimal interest){
		WalletFeeRecord wallet=new WalletFeeRecord();
		wallet.setMemberCardno(memberInfo.getMemberCardno());
		wallet.setAmount(interest);
		wallet.setFeeType(WalletFeeType.INTREEST_ACCURAL);
		wallet.setBalance(memberInfo.getWalletBalance());
		wallet.setRemarks("利息结算完成后自动加入用户的零钱包");
		walletFeeRecordService.save(wallet);
	}
}
