package com.footing.website.modules.luxclub.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.footing.website.common.persistence.DataEntity;

/**
 * 消息通知Entity
 * @author liuguoqing
 * @version 2016-03-15
 */
public class MessageNotify extends DataEntity<MessageNotify> {

    private static final long serialVersionUID = 1L;
    private String sendContent; // 发送内容
    private Integer messageType; // 消息类型(1：站内信，2：短信)
    private String memberPhone; // 会员电话
    private String sendResult; // 发送结果
    private String memberCardno; // 会员卡号
    private String orderCode; // 订单编号
    private Integer state; // 状态(-2:失效,-1:已删除,0:创建,1:已发送,2:已读)

    private String title; // 消息标题
    private String receiver; // 接收人(用户ID)
    private Date sendTime; // 发送时间
    private Integer messageChannel; // 消息渠道
    private String returnCode; // 返回代码
    private String errorMessage; // 错误信息
    
    private Date beginCreateDate; //创建日期   开始
    private Date endCreateDate;   //创建日期   结束
    private String receiverName;   //客户经理名称

    public MessageNotify() {
        super();
    }

    public MessageNotify(Long id) {
        super(id);
    }

    @Length(min = 0, max = 1000, message = "短信内容长度必须介于 0 和 1000 之间")
    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    @Length(min = 0, max = 64, message = "会员电话长度必须介于 0 和 64 之间")
    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    @Length(min = 0, max = 20, message = "发送结果长度必须介于 0 和 20 之间")
    public String getSendResult() {
        return sendResult;
    }

    public void setSendResult(String sendResult) {
        this.sendResult = sendResult;
    }

    @Length(min = 0, max = 64, message = "会员卡号长度必须介于 0 和 64 之间")
    public String getMemberCardno() {
        return memberCardno;
    }

    public void setMemberCardno(String memberCardno) {
        this.memberCardno = memberCardno;
    }

    @Length(min = 0, max = 64, message = "订单编号长度必须介于 0 和 64 之间")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getMessageChannel() {
        return messageChannel;
    }

    public void setMessageChannel(Integer messageChannel) {
        this.messageChannel = messageChannel;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

}