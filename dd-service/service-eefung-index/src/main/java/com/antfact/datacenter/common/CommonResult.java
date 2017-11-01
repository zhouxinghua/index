/*
 * Copyright (c) 2010-2017 EEFUNG Software Co.Ltd. All rights reserved.
 * 版权所有(c) 2010-2017 湖南蚁坊软件股份有限公司。保留所有权利。
 */

package com.antfact.datacenter.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>返回结果封装。</p>
 * 创建日期 2017年08月13日
 *
 * @author zhouxinghua(zhouxinghua@eefung.com)
 * @since $version$
 */
public class CommonResult<T> {
    private static final int STATUS_OK = 200;
    private int statusCode;
    private String errorCode;
    private String errorMsg;
    private T info;

    @JsonCreator
    public CommonResult(@JsonProperty("info") T info) {
        this.statusCode = STATUS_OK;
        this.info = info;
    }

    public boolean isSuccess() {
        return statusCode == 200;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(@JsonProperty("info") T info) {
        this.info = info;
    }
}
