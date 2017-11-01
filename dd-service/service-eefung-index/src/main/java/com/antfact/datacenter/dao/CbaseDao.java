/*
 * Copyright (c) 2010-2017 EEFUNG Software Co.Ltd. All rights reserved.
 * 版权所有(c) 2010-2017 湖南蚁坊软件股份有限公司。保留所有权利。
 */

package com.antfact.datacenter.dao;

import com.antfact.datacenter.constant.Constants;
import com.antfact.datacenter.model.Indexer;
import com.antfact.datacenter.utils.CbaseUtil;

import com.datastax.driver.core.RegularStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * <p>Cbase数据查询类。</p>
 * 创建日期 2017年08月07日
 *
 * @author zhouxinghua(zhouxinghua@eefung.com)
 * @since $version$
 */
@Component
public class CbaseDao {

    private CbaseUtil cbaseUtil;

    @Autowired
    public CbaseDao (CbaseUtil cbaseUtil) {
        this.cbaseUtil = cbaseUtil;
    }

    public List<Indexer> getTopNIndex(long time, int type, int topN) {

        List<Indexer> list = new ArrayList<Indexer>();

        RegularStatement select = QueryBuilder.select().all().from(Constants.DB_REPORT, Constants.TB_EEFUNG_INDEX)
                                              .where(QueryBuilder.eq(Constants.FIELD_TIME, time)).and(QueryBuilder.eq(Constants.FIELD_TYPE, type));

        ResultSet rs = cbaseUtil.getSession().execute(select);

        Iterator<Row> iterator = rs.iterator();

        while (iterator.hasNext()) {
            list.add(getIndexBean(iterator.next()));
        }

        // 按照指数排序
        Collections.sort(list, new Comparator<Indexer>() {
            @Override
            public int compare(Indexer o1, Indexer o2) {

                if (o1.getExp().longValue() > o2.getExp().longValue()) {
                    return -1;
                }
                if (o1.getExp().longValue() == o2.getExp().longValue()) {
                    return 0;
                }
                return 1;
            }
        });

        // 返回前topN条记录
        if(list.size() >= topN){
            return list.subList(0, topN);
        }else {
            return list;
        }
    }

    public List<Indexer> getSpecifiedIndex(long startTime, long endTime, int type, String subject) {

        List<Indexer> list = new ArrayList<Indexer>();

        RegularStatement select = QueryBuilder.select().from(Constants.DB_REPORT, Constants.TB_EEFUNG_INDEX)
                                              .where(QueryBuilder.eq(Constants.FIELD_TYPE, type)).and(QueryBuilder.gte(Constants.FIELD_TIME, startTime))
                                              .and(QueryBuilder.lte(Constants.FIELD_TIME, endTime));

        ResultSet rs = cbaseUtil.getSession().execute(select);

        Iterator<Row> iterator = rs.iterator();

        while (iterator.hasNext()) {
            Indexer indexer = getIndexBean(iterator.next());
            if (indexer.getSubject().equals(subject)) {
                list.add(indexer);
            }
        }

        return list;
    }

    private Indexer getIndexBean(Row row) {

        Indexer Indexer = new Indexer();
        Indexer.setTime(row.getLong(Constants.FIELD_TIME));
        Indexer.setType(row.getLong(Constants.FIELD_TYPE));
        Indexer.setSubject(row.getString(Constants.FIELD_SUBJECT));
        Indexer.setCount(row.getLong(Constants.FIELD_COUNT));
        Indexer.setExp(Math.round(Double.parseDouble(row.getString(Constants.FIELD_EXP))));
        return Indexer;
    }

}
