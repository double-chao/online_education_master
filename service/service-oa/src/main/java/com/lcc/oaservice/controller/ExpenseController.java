package com.lcc.oaservice.controller;

import com.lcc.oaservice.dto.flowable.ExpanseDTO;
import com.lcc.oaservice.vo.flowable.ExpanseVO;
import com.lcc.result.Result;
import com.lcc.servicebase.exceptionhandler.BadException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author Administrator
 * @Description 报销
 * @Date 2021/1/18  15:11
 */
@Api(tags = "报销")
@RestController
@RequestMapping("/oaservice/expense")
public class ExpenseController {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;

    @ApiOperation("生成报销-启动流程")
    @PostMapping(value = "/addExpense")
    public Result addExpense1(@RequestBody ExpanseDTO expanseDTO) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("taskUser", expanseDTO.getUserId());
        map.put("money", expanseDTO.getMoney());
        map.put("description", expanseDTO.getDescription());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Expense", map);
        return Result.ok().data("processId", processInstance.getId());
    }

    @ApiOperation("查询待办列表")
    @GetMapping(value = "/listExpense/{userId}")
    public Result list(@ApiParam(name = "userId", value = "用户id", required = true) @PathVariable String userId) {
        List<Task> list = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        List<ExpanseVO> expanseVOList = new ArrayList<>();
        list.forEach((task) -> {
            ExpanseVO expanseVO = new ExpanseVO();
            expanseVO.setId(task.getId());
            expanseVO.setName(task.getName());
            expanseVOList.add(expanseVO);
        });
        return Result.ok().data("expanseVOList", expanseVOList);
    }

    @ApiOperation("批准/统一")
    @GetMapping(value = "/approval/{taskId}")
    public Result approval(@ApiParam(name = "taskId", value = "任务ID", required = true) @PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new BadException(HttpStatus.NOT_FOUND, "流程不存在");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
        taskService.complete(taskId, map);
        return Result.ok();
    }

    @ApiOperation("拒绝/退回")
    @GetMapping(value = "/reject/{taskId}")
    public Result reject(@ApiParam(name = "taskId", value = "任务ID", required = true) @PathVariable String taskId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "驳回");
        taskService.complete(taskId, map);
        return Result.ok().message("拒绝");
    }

    @ApiOperation("生成流程图")
    @GetMapping(value = "/processDiagram/{processId}")
    public Result genProcessDiagram(HttpServletResponse httpServletResponse,
                                    @ApiParam(name = "processId", value = "流程ID", required = true) @PathVariable String processId) throws Exception {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        //流程走完的不显示图
        if (pi == null) {
            return Result.ok();
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(InstanceId).list();
        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engConf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engConf.getProcessDiagramGenerator();
        byte[] buf = new byte[1024];
        int length;
        try (InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, engConf.getActivityFontName(), engConf.getLabelFontName(), engConf.getAnnotationFontName(), engConf.getClassLoader(), 1.0, true); OutputStream out = httpServletResponse.getOutputStream()) {
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
        }
        return Result.ok();
    }

}