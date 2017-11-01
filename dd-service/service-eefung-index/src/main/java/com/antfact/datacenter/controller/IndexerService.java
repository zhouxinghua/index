/*
 * Copyright (c) 2010-2017 EEFUNG Software Co.Ltd. All rights reserved.
 * 版权所有(c) 2010-2017 湖南蚁坊软件股份有限公司。保留所有权利。
 */

package com.antfact.datacenter.controller;

import com.antfact.datacenter.dao.CbaseDao;
import com.antfact.datacenter.error.ErrorCode;
import com.antfact.datacenter.common.CommonResult;
import com.antfact.datacenter.model.Indexer;
import com.google.common.base.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.ws.rs.core.Response;


/**
 * <p>蚁坊指数数据服务。</p>
 * 创建日期 2017年08月07日
 *
 * @author zhouxinghua(zhouxinghua@eefung.com)
 * @since $version$
 */
@RestController
@RequestMapping("antIndex")
public class IndexerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexerService.class);

    @Autowired
    private CbaseDao cbaseDao;

    @RequestMapping(value = "topN", method= RequestMethod.GET)
    public CommonResult getTopN(@RequestParam(value = "time") long time, @RequestParam(value = "type") int type,
                                @RequestParam(value = "topN") int topN) {

        LOGGER.info("查询蚁坊指数 topN 入参time：{}，type：{}，topN：{}", new Object[] { time, type, topN });

        if (topN < 0 || type > 10 || type <= 0 || time <= 0) {
            LOGGER.error("查询蚁坊指数 topN 入参错误");
            //return new ResponseHelper<Indexer>().ng(new HStoreServiceException(ErrorCode.PARAMETER_ERROR));
            CommonResult commonResult = new CommonResult(null);
            commonResult.setErrorMsg(ErrorCode.PARAMETER_ERROR.getMessage());
            commonResult.setErrorCode(String.valueOf(ErrorCode.PARAMETER_ERROR.getCode()));
            commonResult.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

            return commonResult;

        }
        // topN 排名前N位，不传topN，默认为前20位
        int defaultTopN = 20;
        if (topN == 0) {
            topN = defaultTopN;
        }

        List<Indexer> list = cbaseDao.getTopNIndex(time, type, topN);
        if (list.isEmpty()) {
            LOGGER.info("数据库中没有查询到相关的数据");
            CommonResult commonResult = new CommonResult(null);
            commonResult.setErrorMsg(ErrorCode.DB_DATA_QUERY_NULL.getMessage());
            commonResult.setErrorCode(String.valueOf(ErrorCode.DB_DATA_QUERY_NULL.getCode()));
            commonResult.setStatusCode(Response.Status.NOT_FOUND.getStatusCode());

            return commonResult;
        }

        return new CommonResult<List<Indexer>>(list);
    }

    @RequestMapping(value = "times", method = RequestMethod.GET)
    public CommonResult getIndex(@RequestParam(value = "startTime") long startTime,
                             @RequestParam(value = "endTime") long endTime, @RequestParam(value = "type") int type,
                             @RequestParam(value = "subject") String subject) {

        LOGGER.info("查询某时间段内指定对象蚁坊指数入参startTime：{}，endTime：{}，type：{}，subject：{}",
                 new Object[] { startTime, endTime, type, subject });

        if (startTime > endTime || type < 0 || type > 10 || Strings.isNullOrEmpty(subject)) {
            LOGGER.error("查询某时间段内指定对象蚁坊指数入参错误");
            //return new ResponseHelper<Indexer>().ng(new HStoreServiceException(ErrorCode.PARAMETER_ERROR));
            CommonResult commonResult = new CommonResult(null);
            commonResult.setErrorMsg(ErrorCode.PARAMETER_ERROR.getMessage());
            commonResult.setErrorCode(String.valueOf(ErrorCode.PARAMETER_ERROR.getCode()));
            commonResult.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());

            return commonResult;
        }

        List<Indexer> list = cbaseDao.getSpecifiedIndex(startTime, endTime, type, subject);
        if (list.isEmpty()) {
            LOGGER.info("数据库中没有查询到相关的数据");
            //return new ResponseHelper<Indexer>().ng(new HStoreServiceException(ErrorCode.DB_DATA_QUERY_NULL));

            CommonResult commonResult = new CommonResult(null);
            commonResult.setErrorMsg(ErrorCode.DB_DATA_QUERY_NULL.getMessage());
            commonResult.setErrorCode(String.valueOf(ErrorCode.DB_DATA_QUERY_NULL.getCode()));
            commonResult.setStatusCode(Response.Status.NOT_FOUND.getStatusCode());

            return commonResult;
        }

        return new CommonResult<List<Indexer>>(list);
    }
}
