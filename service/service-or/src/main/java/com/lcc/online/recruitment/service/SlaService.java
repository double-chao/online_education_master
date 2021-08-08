package com.lcc.online.recruitment.service;

import com.lcc.online.recruitment.entity.Sla;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 当前简历所处的环节SLA表 服务类
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
@RestController
@RequestMapping("/online/recruitment/sla")
public interface SlaService extends IService<Sla> {

}
