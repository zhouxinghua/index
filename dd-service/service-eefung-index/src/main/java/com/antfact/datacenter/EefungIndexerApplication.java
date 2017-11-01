/*
 * Copyright (c) 2010-2017 EEFUNG Software Co.Ltd. All rights reserved.
 * 版权所有(c) 2010-2017 湖南蚁坊软件股份有限公司。保留所有权利。
 */

package com.antfact.datacenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * <p>蚁坊指数应用服务入口。</p>
 * 创建日期 2017年08月07日
 *
 * @author zhouxinghua(zhouxinghua@eefung.com)
 * @since $version$
 */
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class EefungIndexerApplication  {
    public static void main (String[] args) {
        SpringApplication.run(EefungIndexerApplication.class, args);
    }
}
