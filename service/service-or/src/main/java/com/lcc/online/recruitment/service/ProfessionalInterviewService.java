package com.lcc.online.recruitment.service;

import com.lcc.online.recruitment.entity.ProfessionalInterview;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 专业面试记录表 服务类
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
@RestController
@RequestMapping("/online/recruitment/professional/interview")
public interface ProfessionalInterviewService extends IService<ProfessionalInterview> {

}
