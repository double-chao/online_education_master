package com.lcc.eduservice.service;

import com.lcc.vo.CreateCodeVO;

/**
 * @Author Administrator
 * @Description 代码生成器
 * @Date 2020/12/9  17:57
 */
public interface CodeGeneratorService {

    /**
     * @param createCodeVO 参数实体
     * @return 返回值
     */
    boolean createCode(CreateCodeVO createCodeVO);
}
