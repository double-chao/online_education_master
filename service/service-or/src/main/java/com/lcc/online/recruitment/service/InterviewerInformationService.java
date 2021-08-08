package com.lcc.online.recruitment.service;

import com.lcc.online.recruitment.entity.InterviewerInformation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 面试官信息表 服务类
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
@RestController
@RequestMapping("/online/recruitment/interviewer/information")
public interface InterviewerInformationService extends IService<InterviewerInformation> {

}
