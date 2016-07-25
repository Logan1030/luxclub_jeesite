package com.footing.website.modules.luxclub.api.response;

/**
 * login response
 */
public class LoginResp {
    
    /**客户经理ID*/
    private Long userId;

    /** 令牌  */
    private String token;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TokenResp [userId=");
        builder.append(userId);
        builder.append(", token=");
        builder.append(token);
        builder.append("]");
        return builder.toString();
    }

}
