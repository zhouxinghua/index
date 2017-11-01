/*
 * Copyright (c) 2010-2017 EEFUNG Software Co.Ltd. All rights reserved.
 * 版权所有(c) 2010-2017 湖南蚁坊软件股份有限公司。保留所有权利。
 */

package com.antfact.datacenter.error;

/**
 * <p>功能描述。</p>
 * 创建日期 2017年08月07日
 *
 * @author zhouxinghua(zhouxinghua@eefung.com)
 * @since $version$
 */
public enum ErrorCode {

    /**
     * 数据库数据写入失败
     */
    DB_DATA_SAVE_FAILURE(111001, "数据库数据写入失败"),

    /**
     * 数据库查询数据出错
     */
    DB_DATA_QUERY_ERROR(111002, "数据库查询数据出错"),

    /**
     * 数据库数据更新失败
     */
    DB_DATA_UPDATE_FAILURE(111003, "数据库数据更新失败"),

    /**
     * 数据库写入数据为NULL
     */
    DB_DATA_SAVE_NULL(111004, "数据库写入数据为NULL"),

    /**
     * 数据库更新数据为NULL
     */
    DB_DATA_UPDATE_NULL(111005, "数据库更新数据为NULL"),

    /**
     * 数据库查询不到相应数据
     */
    DB_DATA_QUERY_NULL(111006, "数据库查询数据为NULL"),

    /**
     * 传入参数错误
     */
    PARAMETER_ERROR(111100, "传入参数错误"),

    /**
     * 锁超时异常
     */
    TIME_OUT_FOR_LOCK(111101, "获取分布式锁超时"),

    /**
     * API服务异常
     */
    API_SERVER_ERROR(111102, "API服务异常");

    private static final String FORMAT_STR = "errorCode:%d, message:%s";

    private int code;

    private String message;

    /**
     * @param code
     * @param message
     */

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return String.format(FORMAT_STR, code, message);
    }

}
