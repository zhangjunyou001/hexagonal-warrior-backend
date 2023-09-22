package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.chapter.ChapterVo;
import com.atguigu.serviceedu.service.EduChapterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description="章节管理")
@RestController
@RequestMapping("/eduService/chapter")
//@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list=eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }


    @GetMapping("getChapterById/{chapterid}")
    public R getChapterById(@PathVariable String chapterid){
        System.out.println("getChapterById");
        EduChapter eduChapter = eduChapterService.getById(chapterid);
        return R.ok().data("chapter",eduChapter);
    }

    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        System.out.println(eduChapter);
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    @DeleteMapping("deleteChapterById/{chapterid}")
    public R deleteChapterById(@PathVariable String chapterid){
        int ans=eduChapterService.deleteChapter(chapterid);
        return R.ok();
    }
}

