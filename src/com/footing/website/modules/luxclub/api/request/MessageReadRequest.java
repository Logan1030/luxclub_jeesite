package com.footing.website.modules.luxclub.api.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 客户经理-消息标记已读, 输入参数
 * @author liuguoqing
 *
 */
public class MessageReadRequest extends BaseRequest {
    
    /** 消息ids */
    private String messageIds;

    @NotBlank(message = "ids不能为空")
    public String getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(String messageIds) {
        this.messageIds = messageIds;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageReadRequest [messageIds=");
        builder.append(messageIds);
        builder.append(", client=");
        builder.append(getClient());
        builder.append(", token=");
        builder.append(getToken());
        builder.append("]");
        return builder.toString();
    }
    
}
