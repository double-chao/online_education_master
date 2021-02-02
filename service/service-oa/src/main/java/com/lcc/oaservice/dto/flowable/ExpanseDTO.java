package com.lcc.oaservice.dto.flowable;

import lombok.Data;

/**
 * @Author Administrator
 * @Description 报销单据
 * @Date 2021/1/18  16:14
 */
@Data
public class ExpanseDTO {
    private String userId;
    private Integer money;
    private String description;
}
