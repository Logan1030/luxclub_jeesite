package com.footing.website.modules.luxclub.service;
 
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footing.website.common.persistence.Page;
import com.footing.website.common.service.CrudService;
import com.footing.website.common.utils.DateUtils;
import com.footing.website.common.utils.MakeOrderNum;
import com.footing.website.common.utils.StringUtils;
import com.footing.website.modules.entity.User;
import com.footing.website.modules.luxclub.api.client.ClientProperty;
import com.footing.website.modules.luxclub.api.request.OrderRequest;
import com.footing.website.modules.luxclub.api.response.OrderManagerVo;
import com.footing.website.modules.luxclub.api.response.OrderVo;
import com.footing.website.modules.luxclub.common.BusinessConstants;
import com.footing.website.modules.luxclub.common.MemberHisOpreationType;
import com.footing.website.modules.luxclub.common.MemberState;
import com.footing.website.modules.luxclub.dao.MemberInfoDao;
import com.footing.website.modules.luxclub.dao.OrderDao;
import com.footing.website.modules.luxclub.entity.CustomerClientToken;
import com.footing.website.modules.luxclub.entity.FeeRecord;
import com.footing.website.modules.luxclub.entity.MemberInfo;
import com.footing.website.modules.luxclub.entity.MemberInfoHis;
import com.footing.website.modules.luxclub.entity.MessageNotify;
import com.footing.website.modules.luxclub.entity.Order;
import com.footing.website.modules.luxclub.entity.OrderCnd;
import com.footing.website.modules.luxclub.entity.SiteInfo;
import com.footing.website.modules.luxclub.entity.WalletFeeRecord;
import com.footing.website.modules.luxclub.message.MessageConstant;
import com.footing.website.modules.luxclub.utils.ApiUtil;
import com.footing.website.modules.sys.dao.UserDao;
import com.footing.website.modules.sys.service.SerialNumberService;
import com.footing.website.modules.sys.utils.DictUtils;
import com.footing.website.modules.sys.utils.UserUtils;
 
/**
 * 订单表Service
 * @author yubin
 * @version 2016-03-16
 */
@Service
@Transactional(readOnly = true)
public class OrderService extends CrudService<OrderDao, Order> {
	
	@Autowired
	private MemberInfoDao memberInfoDao;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private MemberInfoHisService memberInfoHisService;
	@Autowired
	private FeeRecordService feeRecordService;
	@Autowired
	private MessageNotifyService messageNotifyService;
    @Autowired
    private SiteInfoService siteInfoService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private WalletFeeRecordService walletFeeRecordService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private SerialNumberService serialNumberService;
	 /** 
     * 锁对象，可以为任意对象 
     */  
    private static Object lockObj = "lockerOrder";  
    public Order get(Long id) {
		return super.get(id);
	}
	
	public Integer countOrder(){
		return dao.countOrder(); 
	}
	
	public List<Order> findList(Order order) {
		return super.findList(order);
	}
	
	public Page<Order> findPage(Page<Order> page, Order order) {
		return super.findPage(page, order);
	} 
	@Transactional(readOnly = false)
	public void save(Order order){
		super.save(order);
	}
	
	@Transactional(readOnly = false)
	public void delete(Order order) {
		super.delete(order);
	}
	/**
	 * 
	 * <p>
	 * Description:1 客户端提交订单<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月22日
	 * @param orderRequest
	 * @param clint
	 * @return
	 * String
	 */
	@Transactional(readOnly = false)
	public String commitOrderByApp(OrderRequest orderRequest,ClientProperty clint,HashMap<String, Object> map)throws Exception{
		 
		String result=BusinessConstants.SUCCESS;
		/*//验证数据
		String validateDateResult=validateData(orderRequest);
		if(!result.equals(validateDateResult)){
			return validateData(orderRequest);
		}*/
		Order order=new Order();
		order.setContactMobile(orderRequest.getContactMobile());
		order.setDeviceId(ApiUtil.getDeviceId(clint));
		order.setReserveDate(DateUtils.parseDate(orderRequest.getReserveDate()));
		order.setReserveCost("1");
		order.setReserveNumber(orderRequest.getReserveNumber());
		order.setReserveRequire(orderRequest.getReserveRequire());
		order.setReserveSiteId(orderRequest.getSiteId());
		order.setMemberCardno(orderRequest.getCardno());
		order.setOrderCode(genOrderCode(order));
		order.setState(BusinessConstants.ORDER_STATE_COMMIT);
		order.setRemarks("客户已提交订单，请后台工作人员完成（派单）");
		this.save(order);
		 
		Map<String,String> mapData = new HashMap<String,String>();
        mapData.put("orderCode", order.getOrderCode());
        mapData.put("createDt", DateUtils.formatDate(order.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
        ApiUtil.mapRespData(map, mapData, "提交订单成功", true);
		return result;
	}
	/**
     * 
     * <p>
     * Description:订单编号生成 区域代码(4位)+日期(8位)+流水号(4位)<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年3月16日
     * @param order
     * @return
     * String
     */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRES_NEW)
	private String genOrderCode(Order order)throws Exception{
		 
		SiteInfo siteInfo=siteInfoService.get(order.getReserveSiteId());//日期(8位)
		String code="";//区域代码
		if(siteInfo!=null){
			code=siteInfo.getCode();//区域代码
		}else{
			code="0000";//没有场所默认为0000区域
		}
		logger.info("come"+Thread.currentThread().getName());
	    synchronized (lockObj) {
	    	logger.info("come in lockobj "+Thread.currentThread().getName());
	    	return serialNumberService.getSerialNumberByDate(code, "orderCode");
		} 
	}
	/**
	 * 
	 * <p>
	 * Description:数据验证<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年4月1日
	 * @param orderRequest
	 * @return
	 * String
	 */
	private String validateData(OrderRequest orderRequest) {
		List<String> messages  = ApiUtil.validateBean(orderRequest);
		if(messages != null){
			return messages.get(0);
		}
		if(!StringUtils.validatePhone(orderRequest.getContactMobile())){
			return "请正确填写您的联系号码";
		}
	 
		MemberInfo memberInfo=memberInfoDao.queryMemberInfoByCardno(orderRequest.getCardno(),MemberState.NORMAL);
		if(memberInfo==null){
			return BusinessConstants.ILLEGAL_OPT;
		}
		if(StringUtils.isEmpty(DictUtils.getDictLabel(orderRequest.getReserveNumber(), "reserve_number",null))){
		    return BusinessConstants.ILLEGAL_OPT;
		}
		return BusinessConstants.SUCCESS;
	}

	/**
	 * 
	 * <p>
	 * Description:保存订单和订单历史信息<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月16日
	 * @param order
	 * void
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
	public String saveOrderAndOrderDetail(Order order)throws Exception{
		if(BusinessConstants.ORDER_STATE_COMMIT.equals(order.getState())){
		  order.setOrderCode(genOrderCode(order));
		}
		if(BusinessConstants.ORDER_STATE_UNPAY.equals(order.getState())){
		  if(order.getConsumerMoney()==null){
			  return "请您输入消费金额";
		  }
		  businessConfrimOrder(order);
		  order.setRemarks("用户消费后，由业务员填写消费金额和消费凭证");
		}
		this.save(order);
		return BusinessConstants.SUCCESS;
	}
    
	/**
	 * 
	 * <p>
	 * Description:20分钟后订单状态重新置为预定状态<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月17日
	 * void
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
	public void sendOrderQuartz(){
		//查询
		List<Order>orders=dao.findBysendOrderQuertz();
		if(orders!=null&&orders.size()>0){
			//更新数据
			dao.sendOrderQuartz();
			//产生消息通知信息
			for(Order order:orders){
				User user=userDao.get(order.getBusinessId());
				MessageNotify messageNotify=new MessageNotify();
				messageNotify.setOrderCode(order.getOrderCode());
				messageNotify.setMemberCardno(order.getMemberCardno());
				messageNotify.setSendContent("20分钟未处理此订单,重新分配");
				messageNotify.setRemarks("取消订单成功");
				messageNotify.setTitle("取消订单");
				messageNotify.setReceiver(String.valueOf(order.getBusinessId()));  
				messageNotify.setState(MessageConstant.MESSAGE_STATUS_CREATE);
				messageNotify.setMessageType(MessageConstant.MESSAGE_TYPE_APP);
				messageNotify.setMemberPhone(order.getContactMobile());
				messageNotify.setMessageChannel(user.getTerminalType());
				messageNotifyService.save(messageNotify);
			}		
		}
	}
	/**
	 * 
	 * <p>
	 * Description:调度员完成派单<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月17日
	 * @param order
	 * void
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
	public String sendOrder(Order order){
		Order valideOrder=dao.getOrderBystatus(order.getId(),BusinessConstants.ORDER_STATE_COMMIT);
		if(valideOrder==null){
			return "此订单已经被其他调度员派单，请重新选择订单！";
		}
		order.setRemarks(UserUtils.getUser().getLoginName()+"完成派单"+"给"+UserUtils.get(order.getBusinessId()).getName()+"服务经理接单");
		order.setState(BusinessConstants.ORDER_STATE_SEND);
		order.setSendDate(new Date());
		super.save(order);
		User user=userDao.get(order.getBusinessId());
		MessageNotify messageNotify=new MessageNotify();
		messageNotify.setOrderCode(order.getOrderCode());
		messageNotify.setMemberCardno(order.getMemberCardno());
		messageNotify.setSendContent(BusinessConstants.ORDER_DES_SEND.replace("编号", "编号"+order.getOrderCode()));
		messageNotify.setRemarks("派单成功");
		messageNotify.setTitle("派单");
		messageNotify.setReceiver(String.valueOf(order.getBusinessId()));  
		messageNotify.setState(MessageConstant.MESSAGE_STATUS_CREATE);
		messageNotify.setMessageType(MessageConstant.MESSAGE_TYPE_APP);
		messageNotify.setMemberPhone(order.getContactMobile());
		messageNotify.setMessageChannel(user.getTerminalType());
		messageNotifyService.save(messageNotify);
		return BusinessConstants.SUCCESS;
	}
	/**
	 * 
	 * <p>
	 * Description:后台完成结账<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月17日
	 * @param order
	 * void
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
	public String accountOrder(Order order)throws Exception{
		//1查询会员卡余额 锁行
		MemberInfo memberInfo=memberInfoDao.queryMemberInfoByCardno(order.getMemberCardno().trim(),MemberState.NORMAL);
		//锁行 明确主键ID，有此记录，锁此行，待事务提交才会释放锁
		memberInfo=memberInfoDao.getMemberInfo(memberInfo.getId());
		BigDecimal memberBalance=memberInfo.getMemberBalance();//卡内余额
		if(memberInfo!=null && !memberInfo.getMemberCardno().equals(order.getMemberCardno())){
			return  "订单中会员卡号不一致！";
		}
		//确认的消费金额
		BigDecimal realMoney=order.getConsumerMoney();
		order=this.get(order.getId());//查询订单
		if(StringUtils.validateMoney(realMoney.toString())&&realMoney.compareTo(new BigDecimal(0))<=0){
			return "请输入正确的消费金额";
		}
		//1从会员卡中扣除相应金额
		memberInfo.setMemberBalance(memberInfo.getMemberBalance().subtract(order.getConsumerMoney()));
		memberInfo.setWalletBalance(memberInfo.getWalletBalance().subtract(order.getWalletAmount()));
		//减少相应的待结项
		memberInfo.setObligation(memberInfo.getObligation().subtract(order.getConsumerMoney()));
		memberInfo.setWalletPrepay(memberInfo.getWalletPrepay().subtract(order.getWalletAmount()));
		//5更新订单状态
		if(realMoney.compareTo(order.getConsumerMoney())==0){
		   order.setRemarks("此订单已结账");
		}else{
		   order.setRemarks("此订单已结账 财务人员更正消费金额，原消费金额是"+order.getConsumerMoney());
		}
		order.setState(BusinessConstants.ORDER_STATE_PAY);
		order.setConsumerMoney(realMoney);
		super.save(order);
		this.updateMemberinfo(memberInfo,MemberHisOpreationType.BALANCE_WALLET_CONSUME,order);
		//2比较会员卡金额与消费金额,产生消息通知记录
		this.saveMessage(memberInfo, order,order.getConsumerMoney().compareTo(memberBalance)<=0);	
		//3产生消费记录
		this.saveFeeRecord(memberInfo, order);
		//4产生零钱包消费记录
		if(order.getWalletAmount().compareTo(new BigDecimal(0))>0){
		  this.saveWalletFeeRecord(memberInfo, order);
		}
		return "此订单结账成功";
	}
	/**
	 * 
	 * <p>
	 * Description:结账产生的消费记录<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月17日
	 * @param memberInfo
	 * @param order
	 * void
	 */
	@Transactional(readOnly = false)
	public void saveFeeRecord(MemberInfo memberInfo,Order order){
		FeeRecord feeRecord=new FeeRecord();
		feeRecord.setMemberCardno(memberInfo.getMemberCardno());
		feeRecord.setFeeMoney(order.getConsumerMoney().multiply(new BigDecimal(-1)));
		feeRecord.setFeeType(BusinessConstants.FEE_TYPE_CONSUME);
		feeRecord.setBalance(memberInfo.getMemberBalance());
		feeRecord.setSiteId(order.getReserveSiteId());
		feeRecord.setOrderCode(order.getOrderCode());
		feeRecord.setVoucher(order.getConsumerVouchers());
		feeRecord.setRemarks("结账成功");
		feeRecordService.save(feeRecord);
	}
	/**
	 * 
	 * <p>
	 * Description:结账产生的消费记录 零钱包<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年4月7日
	 * @param memberInfo
	 * @param order
	 * void
	 */
	@Transactional(readOnly = false)
	public void saveWalletFeeRecord(MemberInfo memberInfo,Order order){
		WalletFeeRecord wallet=new WalletFeeRecord();
		wallet.setMemberCardno(memberInfo.getMemberCardno());
		wallet.setAmount(order.getWalletAmount().multiply(new BigDecimal(-1)));
		wallet.setFeeType(BusinessConstants.FEE_TYPE_CONSUME);
		wallet.setBalance(memberInfo.getWalletBalance());
		wallet.setSiteId(order.getReserveSiteId());
		wallet.setOrderCode(order.getOrderCode());
		wallet.setVoucher(order.getWalletVoucher());
		wallet.setRemarks("结账成功");
		walletFeeRecordService.save(wallet);
	}
	/**
	 * 
	 * <p>
	 * Description:结账通知<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月17日
	 * @param memberInfo
	 * @param order
	 * void
	 */
	@Transactional(readOnly = false)
	public void saveMessage(MemberInfo memberInfo,Order order,boolean result){
		MessageNotify messageNotify=new MessageNotify();
		messageNotify.setOrderCode(order.getOrderCode());
		messageNotify.setMemberCardno(order.getMemberCardno());
		messageNotify.setSendContent(result?" 结账完成,"+order.getMemberCardno()+ "卡内剩余"+memberInfo.getMemberBalance()+"元"
				                           :" 结账完成,"+order.getMemberCardno()+ "卡内欠费"+memberInfo.getObligation()+"元");
		messageNotify.setRemarks("结账完成");
		messageNotify.setTitle(BusinessConstants.ORDER_DES_CONFIRM.replace("编号", "编号"+order.getOrderCode()));
		messageNotify.setReceiver(String.valueOf(order.getBusinessId()));  
		messageNotify.setState(MessageConstant.MESSAGE_STATUS_CREATE);
		messageNotify.setMessageType(MessageConstant.MESSAGE_TYPE_SMS);
		messageNotify.setMemberPhone(order.getContactMobile());
		messageNotify.setMessageChannel(Integer.valueOf(memberInfo.getTermType()));
		messageNotifyService.save(messageNotify);
	}
	/**
	 * 
	 * <p>
	 * Description:更新会员状态和增加会员历史记录<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月23日
	 * @param memberInfo
	 * @param opt
	 * void
	 */
	public void updateMemberinfo(MemberInfo memberInfo,int opt,Order order)throws Exception{
		memberInfoService.save(memberInfo);
		MemberInfoHis memberInfoHis=new MemberInfoHis();
		memberInfo.setId(null);
		memberInfoHis.setOperationType(opt);
		BeanUtils.copyProperties(memberInfoHis, memberInfo);
		memberInfoHis.setOperationDate(new Date());
		memberInfoHis.setMemberAmount(order.getConsumerMoney());
		memberInfoHis.setWalletAmount(order.getWalletAmount());
		memberInfoHisService.save(memberInfoHis);
	}
	/**
	 *  
	 * <p>
	 * Description:客户经理确认订单<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月18日
	 * @param order
	 * void
	 */
	@Transactional(readOnly = false)
	public void businessConfrimOrder(Order order)throws Exception{
		//1查询会员卡余额 
	    MemberInfo memberInfo=memberInfoDao.queryMemberInfoByCardno(order.getMemberCardno().trim(),MemberState.NORMAL);
	    memberInfo=memberInfoDao.getMemberInfo(memberInfo.getId());
	    memberInfo.setObligation(order.getConsumerMoney().add(memberInfo.getObligation()));
	    updateMemberinfo(memberInfo,9,order);
	}
	/**
	 * 
	 * <p>
	 * Description:查询已结账金额<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月21日
	 * @param orderCnd
	 * @return
	 * BigDecimal
	 */
	public BigDecimal queryPayAmount(OrderCnd orderCnd){
		return dao.queryPayAmount(orderCnd);
	}
	/**
	 * 
	 * <p>
	 * Description:查询未结账金额<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月21日
	 * @param orderCnd
	 * @return
	 * BigDecimal
	 */
	public BigDecimal queryUnpayAmount(OrderCnd orderCnd){
		return dao.queryUnpayAmount(orderCnd);
	}
	/**
	 * 
	 * <p>
	 * Description:查询订单详细<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月22日
	 * @param id
	 * @return
	 * OrderDetail
	 */
	public OrderVo queryOrderDetailById(long id){
		return dao.queryOrderDetailById(id);
	}

	/**
	 *
 	 * @param orderRequest
	 * @return
     */
	public List<OrderVo> queryOrderPageList(OrderRequest orderRequest){
		return dao.queryOrderPageList(orderRequest.getCardno(),
				(orderRequest.getPageNumber()-1)*orderRequest.getPageSize(),orderRequest.getPageSize(),orderRequest.getState());
	}
	/**
	 * 
	 * <p>
	 * Description:查询订单详细--客户经理<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月22日
	 * @param id
	 * @return
	 * OrderDetail
	 */
	public OrderManagerVo searchOrderDetailById(long id){
		return dao.searchOrderDetailById(id);
	}
	/**
	 * 
	 * <p>
	 * Description:查询订单分页列表-客户经理端<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月23日
	 * @param cardno
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 * List<OrderDetail>
	 */
	public List<OrderVo> searchOrderPageList(OrderRequest req){
		return dao.searchOrderPageList(req.getStatus(),req.getCardno(),
				(req.getPageNumber()-1)*req.getPageSize(),req.getPageSize());
	}
	/**
	 * 
	 * <p>
	 * Description:确认订单<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年4月1日
	 * @param orderRequest
	 * @param clint
	 * @param map
	 * @return
	 * String
	 */
	@Transactional(readOnly = false)
	public String confirmOrderByApp(OrderRequest orderRequest,CustomerClientToken clientToken,HashMap<String, Object> map){
		String result=BusinessConstants.SUCCESS;
		//1 验证数据
		User user=userDao.get(Long.valueOf(clientToken.getAccount()));
		if(user==null){
			return BusinessConstants.ILLEGAL_OPT;
		}
		SiteInfo siteInfo=siteInfoService.get(orderRequest.getSiteId());
	    if(siteInfo==null){
	    	return "请您选择店家";
	    }
		Order order=dao.getOrderBystatus(orderRequest.getOrderId(),BusinessConstants.ORDER_STATE_SEND);
		if(order==null){
			return BusinessConstants.ILLEGAL_OPT;
		}
		if(!String.valueOf(order.getBusinessId()).equals(clientToken.getAccount())){
			return BusinessConstants.ILLEGAL_OPT;
		}
		order.setRealNumber(Integer.valueOf(orderRequest.getReserveNumber()));
		order.setRealDate(DateUtils.parseDate(orderRequest.getRealDate()));
		order.setReserveDate(DateUtils.parseDate(orderRequest.getRealDate()));
		order.setReserveNumber(orderRequest.getReserveNumber());
		order.setRealRequire(orderRequest.getOtherRequire());
		order.setReserveSiteId(orderRequest.getSiteId());
		order.setState(BusinessConstants.ORDER_STATE_CONFIRM);
		order.setRemarks(UserUtils.get(order.getBusinessId()).getName()+" 服务经理已确认订单");
		this.save(order);
		//2 开始 产生通知信息
		MessageNotify messageNotify=new MessageNotify();
		MemberInfo memberInfo=memberInfoDao.queryMemberInfoByCardno(order.getMemberCardno(),MemberState.NORMAL);
		messageNotify.setOrderCode(order.getOrderCode());
		messageNotify.setMemberCardno(order.getMemberCardno());
		messageNotify.setSendContent(BusinessConstants.ORDER_DES_CONFIRM.replace("编号", "编号"+order.getOrderCode()));
		messageNotify.setRemarks("确认订单成功");
		messageNotify.setTitle("确认订单");
		messageNotify.setReceiver(order.getMemberCardno());  
		messageNotify.setState(MessageConstant.MESSAGE_STATUS_CREATE);
		messageNotify.setMessageType(MessageConstant.MESSAGE_TYPE_APP);
		messageNotify.setMemberPhone(order.getContactMobile());
		messageNotify.setMessageChannel(Integer.valueOf(memberInfo.getTermType()));
		messageNotifyService.save(messageNotify);
		
	    Map<String,String> mapData = new HashMap<String,String>();
        mapData.put("orderCode", order.getOrderCode());
        mapData.put("createDt", DateUtils.formatDate(order.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
        ApiUtil.mapRespData(map, mapData, "确认订单成功", true);
		return result;
	}
	/**
	 * 
	 * <p>
	 * Description:业务员取消订单<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月24日
	 * @param orderRequest
	 * @param clint
	 * @param map
	 * @return
	 * String
	 */
	@Transactional(readOnly = false)
	public String cancleOrderByApp(OrderRequest orderRequest,CustomerClientToken clientToken,HashMap<String, Object> map){
		String result=BusinessConstants.SUCCESS;
		Order order=null;
		if(clientToken.getAccount().length()==8){
			//用户端取消订单
			MemberInfo memberInfo=memberInfoDao.queryMemberInfoByCardno(clientToken.getAccount(), MemberState.NORMAL);
			if(memberInfo==null){
				return BusinessConstants.ILLEGAL_OPT;
			}
			order=dao.getOrderBystatus(orderRequest.getOrderId(),BusinessConstants.ORDER_STATE_COMMIT);
			if(order==null){
				return "此订单不能取消了，稍后会有人员与你电话联系！";
			}
			order.setRemarks("用户端取消订单");
		}else{
		    //经理端取消订单 验证数据
			User user=userDao.get(Long.valueOf(clientToken.getAccount()));
			if(user==null){
				return BusinessConstants.ILLEGAL_OPT;
			}
			order=dao.getOrderBystatus(orderRequest.getOrderId(),BusinessConstants.ORDER_STATE_CANCLE);
			if(order==null){
				return BusinessConstants.ILLEGAL_OPT;
			}
			if(!String.valueOf(order.getBusinessId()).equals(clientToken.getAccount())){
				return BusinessConstants.ILLEGAL_OPT;
			}
			order.setReserveRequire(orderRequest.getCancelReason());
			order.setRemarks("服务经理取消订单");
		}
		order.setState(BusinessConstants.ORDER_STATE_CANCLE);
		this.save(order);
		Map<String,String> mapData = new HashMap<String,String>();
        mapData.put("orderCode", order.getOrderCode());
        mapData.put("createDt", DateUtils.formatDate(order.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
        ApiUtil.mapRespData(map, mapData, "取消订单成功", true);
		return result;
	}
	/**
	 * 
	 * <p>
	 * Description:待结账订单<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年4月1日
	 * @param orderRequest
	 * @param clint
	 * @param map
	 * @return
	 * String
	 */
	@Transactional(readOnly = false)
	public String unpayOrderByApp(OrderRequest orderRequest,HttpServletRequest request,
			CustomerClientToken clientToken,HashMap<String, Object> map)throws Exception{
		String result=BusinessConstants.SUCCESS;
		//验证数据
		User user=userDao.get(Long.valueOf(clientToken.getAccount()));
		if(user==null){
			return BusinessConstants.ILLEGAL_OPT;
		}
		Order order=dao.getOrderBystatus(orderRequest.getOrderId(),BusinessConstants.ORDER_STATE_CONFIRM);
		if(order==null){
			return BusinessConstants.ILLEGAL_OPT;
		}
		MemberInfo memberInfo=memberInfoDao.queryMemberInfoByCardno(order.getMemberCardno().trim(),MemberState.NORMAL);
		if(memberInfo==null){
			return BusinessConstants.ILLEGAL_OPT;
		}
		if(!String.valueOf(order.getBusinessId()).equals(clientToken.getAccount())){
			return BusinessConstants.ILLEGAL_OPT;
		}
		if(StringUtils.validateMoney(orderRequest.getAmount())&&new BigDecimal(orderRequest.getAmount()).compareTo(new BigDecimal(0))<=0){
			return "请输入消费额度！";
		}
		if(StringUtils.isEmpty(orderRequest.getVoucherNames())){
			return "请输入消费凭证！";
		}
		if (StringUtils.isNotBlank(orderRequest.getWalletAmount())) {
			if(!StringUtils.validateMoney(orderRequest.getWalletAmount())){
				return "您的输入有误！";
			}
			if(new BigDecimal(orderRequest.getWalletAmount()).compareTo(memberInfo.getWalletBalance())>0){
				return "你的零钱包余额不足！";
			}
			if(new BigDecimal(orderRequest.getWalletAmount()).compareTo(new BigDecimal(0))<=0){
				return "请输入零钱包金额！";
			}
			
			if(StringUtils.isEmpty(orderRequest.getWalletRemarks())){
				return "请输入零钱包消费备注！";
			}
			order.setWalletAmount(new BigDecimal(orderRequest.getWalletAmount()));
			order.setWalletRemarks(orderRequest.getWalletRemarks());
		}
		if(StringUtils.isNotEmpty(orderRequest.getWalletVoucher())){
			String walletVouchers=uploadService.getVoucherUrl(orderRequest.getWalletVoucher(), request);
			order.setWalletVoucher(walletVouchers);
		}
		//上传消费凭证和零钱包凭证
		String consumerVouchers=uploadService.getVoucherUrl(orderRequest.getVoucherNames(), request);
		order.setConsumerMoney(new BigDecimal(orderRequest.getAmount()));
		order.setConsumerVouchers(consumerVouchers);
		
		order.setRemarks("服务经理已上传凭证，等待审核");
		order.setState(BusinessConstants.ORDER_STATE_UNPAY);
		this.save(order);
		
		//3更新会员信息表 
	    memberInfo=memberInfoDao.getMemberInfo(memberInfo.getId());
	    memberInfo.setObligation(memberInfo.getObligation().add(order.getConsumerMoney()));
	    memberInfo.setWalletPrepay(memberInfo.getWalletPrepay().add(order.getWalletAmount()));
	    memberInfoService.save(memberInfo);
	    
		Map<String,String> mapData = new HashMap<String,String>();
        mapData.put("orderCode", order.getOrderCode());
        mapData.put("createDt", DateUtils.formatDate(order.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
        ApiUtil.mapRespData(map, mapData, "待结账订单成功", true);
		return result;
	}
	/**
	 * 
	 * <p>
	 * Description:财务统计<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月18日
	 * @param page
	 * @param order
	 * @return
	 * Page<Order>
	 */
	public Page<Order> findPagebyStatistics(Page<Order> page, Order order) {
		order.setPage(page);
		page.setList(dao.findPagebyStatistics(order));
		return page;
	}
}