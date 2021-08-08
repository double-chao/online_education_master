package com.lcc.online.recruitment.service;

import com.lcc.online.recruitment.entity.HistoryOperation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 环节历史记录表 服务类
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
@RestController
@RequestMapping("/online/recruitment/history/operation")
public interface HistoryOperationService extends IService<HistoryOperation> {

}
