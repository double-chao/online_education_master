package com.lcc.elasticsearch.service;

import com.lcc.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author Administrator
 * @Description 首页搜索
 * @Date 2021/7/27  19:50
 */
@Api(tags = "首页全文搜索服务类")
@RequestMapping("/index/search")
public interface IndexSearchService {

    @ApiOperation("/创建一个elastic-search的index索引")
    @PostMapping("/createIndex")
    Result createIndex();

    @ApiOperation("/")
    @PostMapping("/queryIndex")
    Result queryIndex();
}
