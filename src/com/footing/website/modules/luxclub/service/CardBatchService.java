package com.footing.website.modules.luxclub.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footing.website.common.persistence.Page;
import com.footing.website.common.service.CrudService;
import com.footing.website.modules.entity.User;
import com.footing.website.modules.luxclub.common.MemberState;
import com.footing.website.modules.luxclub.dao.CardBatchDao;
import com.footing.website.modules.luxclub.dao.MemberInfoDao;
import com.footing.website.modules.luxclub.entity.CardBatch;
import com.footing.website.modules.luxclub.entity.MemberInfo;
import com.footing.website.modules.luxclub.utils.CardNoUtil;
import com.footing.website.modules.sys.utils.UserUtils;

/**
 * 会员卡批次生成Service
 * @author liuguoqing
 * @version 2016-03-15
 */
@Service
@Transactional(readOnly = true)
public class CardBatchService extends CrudService<CardBatchDao, CardBatch> {
    
    @Autowired
    private MemberInfoDao memberInfoDao;
    
    @Autowired
    private CardNoUtil cardNoUtil;
    
	public CardBatch get(Long id) {
		return super.get(id);
	}
	
	public List<CardBatch> findList(CardBatch cardBatch) {
		return super.findList(cardBatch);
	}
	
	public Page<CardBatch> findPage(Page<CardBatch> page, CardBatch cardBatch) {
		return super.findPage(page, cardBatch);
	}
	
	@Transactional(readOnly = false)
    public void save(CardBatch cardBatch) {
        super.save(cardBatch);
    }
    
    @Transactional(readOnly = false)
    public void delete(CardBatch cardBatch) {
        super.delete(cardBatch);
    }
	
    @Transactional(readOnly = false)
	public void batchGenerate(CardBatch cardBatch) {
	    //添加批次表
	    cardBatch.preInsert();
		super.save(cardBatch);
		long begin = System.currentTimeMillis();
		//生成优惠券编号
		ConcurrentHashMap<String, String> cardNoMap = cardNoUtil.generateCardNo(cardBatch.getCreateCount());
		logger.info("=== 会员卡和密码Map生成,批次ID:{}, 数量：{}, cost: {} ms", cardBatch.getId(), cardNoMap.size(), (System.currentTimeMillis()-begin));
        // 生成一批优惠券
        List<MemberInfo> memberInfos = new ArrayList<MemberInfo>();
        List<String> cardnoList = new ArrayList<String>();
        User user = UserUtils.getUser();
        Date date = new Date();
        BigDecimal balance = new BigDecimal("0");
        try {
            begin = System.currentTimeMillis();
            for(Entry<String, String> entry : cardNoMap.entrySet()){
                MemberInfo memberInfo = new MemberInfo();
                memberInfo.setBatchId(cardBatch.getId());
                memberInfo.setMemberCardno(entry.getKey());
                memberInfo.setMemberPwd(entry.getValue());
                memberInfo.setMemberLevel(cardBatch.getCardType());
                memberInfo.setMemberBalance(balance);
                memberInfo.setObligation(balance);
                memberInfo.setWalletBalance(balance);
                memberInfo.setWalletPrepay(balance);
                memberInfo.setWalletProfit(balance);
                memberInfo.setWalletLastProfit(balance);
                memberInfo.setIssuingDate(new Date());
                memberInfo.setState(MemberState.INACTIVE);
                if (null != user.getId()){
                    memberInfo.setUpdateBy(user);
                    memberInfo.setCreateBy(user);
                }
                memberInfo.setCreateDate(date);
                memberInfo.setUpdateDate(date);
                memberInfos.add(memberInfo);
                cardnoList.add(entry.getKey());//会员卡号
            }
            logger.info("=== 卡号添加到list,数量: {}, cost: {}ms ", cardnoList.size(), (System.currentTimeMillis()-begin));
            boolean flag = this.batchSaveMemberInfo(cardBatch.getId(), memberInfos);
            if(flag){
                begin = System.currentTimeMillis();
                //保存数据库成功后，添加到内存中
                int count = 0;
                if(cardnoList!=null && cardnoList.size() >0){
                    cardNoUtil.addCacheMap(cardnoList);
                    count = cardnoList.size();
                }
                logger.info("=== 卡号添加到缓存,数量: {}, cost: {}ms ", count,(System.currentTimeMillis()-begin));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }finally{
            cardNoMap = null;
            memberInfos = null;
            cardnoList = null;
            balance = null;
        }
	}
	
	@Transactional(readOnly = false)
	public boolean batchSaveMemberInfo(Long batchId, List<MemberInfo> memberInfos){
	    long begin = System.currentTimeMillis();
	    int updateMaxSize = 500;
        int result = 0;
        if (null != memberInfos && memberInfos.size() > 0) {
            int size = memberInfos.size();
            for (int i = 0; i <= size / updateMaxSize; i++) {
                List<MemberInfo> tmpList = memberInfos.subList(i * updateMaxSize, (i + 1) * updateMaxSize > size ? size : (i + 1) * updateMaxSize);
                if (tmpList.size() > 0) {
                    result += memberInfoDao.batchInsert(tmpList);
                }
            }
        }
        logger.info("=== 批次生成会员卡保存完成, 批次编号：{}, 数量：{}, cost: {} ms", batchId, result, (System.currentTimeMillis()-begin));
        return result > 0 ? true : false;
	}
	
}