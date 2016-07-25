package com.footing.website.modules.luxclub.api.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 客户经理API request
 * @author liuguoqing
 *
 */
public class ManagerRequest extends BaseRequest {
    
    /** 登录名 */
    private String loginName;

    /** 密码 */
    private String password;

    @NotBlank(message = "登录名不能为空")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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
        builder.append("Manager [loginName=");
        builder.append(loginName);
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
