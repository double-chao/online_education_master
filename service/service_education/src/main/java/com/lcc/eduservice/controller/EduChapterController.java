package com.lcc.eduservice.controller;

import com.lcc.eduservice.entity.EduChapter;
import com.lcc.eduservice.entity.chapter.ChapterVo;
import com.lcc.eduservice.service.EduChapterService;
import com.lcc.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程章节 前端控制器
 * </p>
 *
 * @author chaochao
 * @since 2020-05-28
 */
@Api(description = "课程章节")
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @ApiOperation("根据课程id获取章节列表")
    @GetMapping("/getChapterVideo/{courseId}") // 因为只有课程有了后才有课程的章节
    public Result getChapterVideo(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId) {
        List<ChapterVo> chapterVoList = chapterService.getChapterAndVideoById(courseId);
        return Result.ok().data("allChapterVideo", chapterVoList);
    }

    @ApiOperation("添加章节")
    @PostMapping("/addChapter")
    public Result addChapter(@RequestBody EduChapter eduChapter) {
        chapterService.save(eduChapter);
        return Result.ok();
    }

    @ApiOperation("根据id查询章节")
    @GetMapping("/getChapterInfo/{chapterId}")
    public Result getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        return Result.ok().data("chapter",eduChapter);
    }

    @ApiOperation("更新章节")
    @PostMapping("/updateChapter")
    public Result updateChapter(@RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return Result.ok();
    }

    @ApiOperation("根据章节id删除章节")
    @DeleteMapping("{chapterId}")
    public Result deleteChapter(@PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        return flag ? Result.ok() : Result.fail();
    }

}

