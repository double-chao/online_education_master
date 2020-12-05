package com.lcc.eduservice.entity.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 小节
 *
 * @author chaochao
 * @since 2020-05-27
 */
@Data
public class VideoVo {

    private Integer id;

    private String title;

    @ApiModelProperty("阿里云视频id")
    private String videoSourceId;
}
