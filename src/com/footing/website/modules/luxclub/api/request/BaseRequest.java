package com.footing.website.modules.luxclub.api.request;

public class BaseRequest {

    public String client;

    public String token;

    public Integer pageSize = 10;

    public Integer pageNumber = 1;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BaseRequest [client=");
        builder.append(client);
        builder.append(", token=");
        builder.append(token);
        builder.append(", pageSize=");
        builder.append(pageSize);
        builder.append(", pageNumber=");
        builder.append(pageNumber);
        builder.append("]");
        return builder.toString();
    }

}
