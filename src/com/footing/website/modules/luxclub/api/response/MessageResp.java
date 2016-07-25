package com.footing.website.modules.luxclub.api.response;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 客户经理-个人信息返回参数
 * @author liuguoqing
 *
 */
public class MessageResp implements Serializable {

    private static final long serialVersionUID = -346080022558700766L;

    /** 消息ID */
    private Long messageId;
    /** 标题 */
    private String title;
    /** 内容 */
    private String content;
    /** 状态 */
    private int status;
    /** 创建时间 */
    private Date createDate;
    
    /** 状态名称 */
    private String statusName;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ManagerMessageResp [messageId=");
        builder.append(messageId);
        builder.append(", title=");
        builder.append(title);
        builder.append(", content=");
        builder.append(content);
        builder.append(", status=");
        builder.append(status);
        builder.append(", createDate=");
        builder.append(createDate);
        builder.append(", statusName=");
        builder.append(statusName);
        builder.append("]");
        return builder.toString();
    }

}
