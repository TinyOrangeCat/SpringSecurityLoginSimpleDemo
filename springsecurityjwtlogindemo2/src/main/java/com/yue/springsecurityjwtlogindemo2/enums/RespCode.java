package com.yue.springsecurityjwtlogindemo2.enums;

public enum RespCode {
    SUCCESS(200,"处理成功"),
    NOT_FOUND(404,"获取不到资源"),
    UNAUTHORIZED(401,"缺失认证"),
    AUTHORITY_REFUSED(403,"权限不足"),
    ERROR(500,"请求失败");

    private Integer responseCode;
    private String responseInfo;

    public Integer getResponseCode() {
        return responseCode;
    }


    public String getResponseInfo() {
        return responseInfo;
    }

    RespCode(Integer responseCode, String responseInfo) {
        this.responseCode = responseCode;
        this.responseInfo = responseInfo;
    }

}
