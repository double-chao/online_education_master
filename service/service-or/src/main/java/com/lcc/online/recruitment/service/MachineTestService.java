package com.lcc.online.recruitment.service;

import com.lcc.online.recruitment.entity.MachineTest;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 简历的机考成绩记录表 服务类
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
@RestController
@RequestMapping("/online/recruitment/machine/test")
public interface MachineTestService extends IService<MachineTest> {

}
