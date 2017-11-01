/*
 * Copyright (c) 2010-2017 EEFUNG Software Co.Ltd. All rights reserved.
 * 版权所有(c) 2010-2017 湖南蚁坊软件股份有限公司。保留所有权利。
 */

package com.antfact.datacenter.utils;

import com.antfact.datacenter.configuration.Configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>Cbase连接工具类。</p>
 * 创建日期 2017年08月07日
 *
 * @author zhouxinghua(zhouxinghua@eefung.com)
 * @since $version$
 */
@Component
public class CbaseUtil {

    private Configuration confoguration;
    private Session session;

    @Autowired
    public CbaseUtil (Configuration confoguration) {
        this.confoguration = confoguration;
        init();
    }

    private void init (){
        String[] hosts = confoguration.getHostNames().split(",");
        Cluster cluster = Cluster.builder().addContactPoints(hosts).withCredentials(confoguration.getUserName(), confoguration.getPassword())
                         .withClusterName(confoguration.getClusterName()).build();
        session = cluster.connect(confoguration.getKeyspace());
    }

    public Session getSession(){
        return session;
    }

}
