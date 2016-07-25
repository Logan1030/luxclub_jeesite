package com.footing.website.modules.luxclub.api.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 消费/充值记录接口请求对象
 * @author liuguoqing
 *
 */
public class FeeRecordRequest extends BaseRequest{
    
    private String cardno; // 会员卡号
    
    private Integer feeType; // 费用类型

    @NotBlank(message = "卡号不能为空")
    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    @NotNull(message = "类型不能为空")
    @Min(value=0)
    @Max(value=2)
    public Integer getFeeType() {
        return feeType;
    }

    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FeeRecordRequest [cardno=");
        builder.append(cardno);
        builder.append(", feeType=");
        builder.append(feeType);
        builder.append(", client=");
        builder.append(getClient());
        builder.append(", token=");
        builder.append(getToken());
        builder.append(", pageSize=");
        builder.append(getPageSize());
        builder.append(", pageNumber=");
        builder.append(getPageNumber());
        builder.append("]");
        return builder.toString();
    }

}
