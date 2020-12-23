package com.lcc.eduservice.controller;

import com.lcc.eduservice.service.CodeGeneratorService;
import com.lcc.result.Result;
import com.lcc.vo.CreateCodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Administrator
 * @Description 代码生成器
 * @Date 2020/12/9  17:05
 */
@Api(description = "代码生成器")
@RestController
@RequestMapping("/eduservice/code")
public class CodeGeneratorController {

    @Autowired
    private CodeGeneratorService codeGeneratorService;

    @ApiOperation("代码生成")
    @PostMapping("/createCode")
    public Result createCode(@RequestBody CreateCodeVO createCodeVO) {
        boolean flag = codeGeneratorService.createCode(createCodeVO);
        return flag ? Result.ok() : Result.fail();
    }

}
