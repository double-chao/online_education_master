package com.lcc.online.recruitment.service;

import com.lcc.online.recruitment.entity.ComprehensiveInterview;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
@RestController
@RequestMapping("/online/recruitment/comprehensive/interview")
public interface ComprehensiveInterviewService extends IService<ComprehensiveInterview> {

}
