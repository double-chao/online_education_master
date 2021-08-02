package com.lcc.elasticsearch.service.impl;

import com.alibaba.fastjson.JSON;
import com.lcc.elasticsearch.client.TeacherClient;
import com.lcc.elasticsearch.config.ElasticSearchConfig;
import com.lcc.elasticsearch.service.IndexSearchService;
import com.lcc.result.Result;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.servicebase.exceptionhandler.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @Author Administrator
 * @Description
 * @Date 2021/7/27  19:51
 */
@Slf4j
@RestController
public class IndexSearchServiceImpl implements IndexSearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private TeacherClient teacherClient;

    @Override
    public Result createIndex() {
        IndexRequest indexRequest = new IndexRequest("teacher");
        indexRequest.id("1"); // 数据的id
        Result teacher = teacherClient.getTeacher(1);
        String string = JSON.toJSONString(teacher);
        indexRequest.source(string, XContentType.JSON);
        try {
            restHighLevelClient.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
        } catch (IOException e) {
            log.info("创建全文搜索引擎index失败，抛出的异常信息为：", e);
            throw new BadException(CodeEnum.CREATE_ELASTIC_SEARCH_INDEX_EXCEPTION);
        }
        return Result.ok();
    }

    @Override
    public Result queryIndex() {
        SearchRequest searchRequest = new SearchRequest();
        // 指定索引
        searchRequest.indices("teacher");
        // 指定DSL，搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 构造检索条件

        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
            SearchHits hits = response.getHits();
            SearchHit[] hitsHits = hits.getHits();
            for (SearchHit hitsHit : hitsHits) {
                Map<String, Object> sourceAsMap = hitsHit.getSourceAsMap();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 分析结果
        return null;
    }
}
