package com.footing.website.modules.luxclub.api.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class CardPasswordRequest extends BaseRequest {

    private String cardno; // 卡号
    private String oldPassword; // 旧密码
    private String newPassword; // 新密码

    @NotBlank(message = "卡号不能为空")
    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    @NotBlank(message = "新密码不能为空")
    @Length(min = 0, max = 100, message = "新密码长度必须介于 0 和 100之间")
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @NotBlank(message = "旧密码不能为空")
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CardPasswordRequest [cardno=");
        builder.append(cardno);
        builder.append(", oldPassword=");
        builder.append(oldPassword);
        builder.append(", newPassword=");
        builder.append(newPassword);
        builder.append(", client=");
        builder.append(getClient());
        builder.append(", token=");
        builder.append(getToken());
        builder.append("]");
        return builder.toString();
    }

    

}
