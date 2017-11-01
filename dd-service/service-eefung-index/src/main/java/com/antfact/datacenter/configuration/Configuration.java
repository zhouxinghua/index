/*
 * Copyright (c) 2010-2017 EEFUNG Software Co.Ltd. All rights reserved.
 * 版权所有(c) 2010-2017 湖南蚁坊软件股份有限公司。保留所有权利。
 */

package com.antfact.datacenter.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>配置型入口。</p>
 * 创建日期 2017年08月07日
 *
 * @author zhouxinghua(zhouxinghua@eefung.com)
 * @since $version$
 */
@Component
public class Configuration {

    @Value("${cbase.host.name}")
    private String hostNames;

    @Value("${cbase.user.name}")
    private String userName;

    @Value("${cbase.password}")
    private String password;

    @Value("${cbase.cluster.name}")
    private String clusterName;

    @Value("${cbase.keyspace}")
    private String keyspace;


    public String getHostNames() {
        return hostNames;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getClusterName() {
        return clusterName;
    }

    public String getKeyspace() {
        return keyspace;
    }
}
