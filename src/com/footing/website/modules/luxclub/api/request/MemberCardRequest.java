package com.footing.website.modules.luxclub.api.request;

import org.hibernate.validator.constraints.NotBlank;

public class MemberCardRequest extends BaseRequest {

    private String cardno; // 卡号
    private String password; // 密码

    @NotBlank(message = "卡号不能为空")
    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    @NotBlank(message = "密码不能为空")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MemberCardRequest [cardno=");
        builder.append(cardno);
        builder.append(", password=");
        builder.append(password);
        builder.append(", client=");
        builder.append(getClient());
        builder.append(", token=");
        builder.append(getToken());
        builder.append("]");
        return builder.toString();
    }

}
