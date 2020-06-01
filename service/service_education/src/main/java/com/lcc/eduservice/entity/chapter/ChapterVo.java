package com.lcc.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
/**
 * 章节
 *
 * @author chaochao
 * @since 2020-05-27
 */
@Data
public class ChapterVo {

    private String id;

    private String title;

    //表示小节
    private List<VideoVo> children = new ArrayList<>();
}
