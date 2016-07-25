package com.footing.website.modules.luxclub.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.footing.website.common.persistence.CrudDao;
import com.footing.website.common.persistence.annotation.MyBatisDao;
import com.footing.website.modules.luxclub.entity.MemberInfo;

/**
 * 会员信息DAO接口
 * @author liuguoqing
 * @version 2016-03-16
 */
@MyBatisDao
@Component
public interface MemberInfoDao extends CrudDao<MemberInfo> {
    
    /**
     * 根据ID获取会员信息(记录锁)
     * @param id
     * @return
     */
    public MemberInfo getById(Long id);
    
    /**
     * 批量添加会员信息
     * @param memberInfos
     * @return
     */
    public int batchInsert(List<MemberInfo> memberInfos);

    /**
     * 会员卡充值/扣费
     * @param memberInfos
     * @return
     */
    public int changeBalance(MemberInfo memberInfo);
    
    /**
     * 零钱包充值/扣费
     * @param memberInfos
     * @return
     */
    public int changeWallet(MemberInfo memberInfo);
    /**
     * 
     * <p>
     * Description:锁行<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年3月17日
     * @param id
     * @return
     * MemberInfo
     */
    public MemberInfo getMemberInfo(long id);
    
    /**
     * 修改卡状态
     * @param memberInfo
     * @return
     */
    public boolean updateMemberState(MemberInfo memberInfo);
    
    /**
     * 根据卡号、状态查询会员卡信息
     * @param memberCardno
     * @param state
     * @return
     */
    public MemberInfo queryMemberInfoByCardno(@Param("memberCardno")String memberCardno,@Param("state") Integer state);
    
    /**
     * 修改密码
     * @param paramsMap
     * @return
     */
    public boolean updatePassword(Map<String, Object> paramsMap);
    
    /**
     * 根据批次ID获取会员卡信息列表
     * @param batchId
     * @return
     */
    public List<MemberInfo> findListByBatchId(Long batchId);
    
    /**
     * 获取会员卡总数量
     * @return
     */
    public int countAllMemberCardno();
    
    /**
     * 分页获取会员卡号
     * @param startNumber
     * @param pageSize
     * @return
     */
    public List<String> findCardnoListByPage(@Param("startNumber")Integer startNumber, @Param("endNumber")Integer pageSize);
    
    public List<MemberInfo> findListbyInterestAccrual();
	
}