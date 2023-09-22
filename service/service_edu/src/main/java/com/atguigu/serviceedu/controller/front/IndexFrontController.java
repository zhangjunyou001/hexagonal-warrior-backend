package com.atguigu.serviceedu.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.service.EduCourseService;
import com.atguigu.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(description = "首页数据")
@RestController
@RequestMapping("/eduservice/indexfront")
//@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("index")
    public R index(){

        List<EduCourse> eduCourseList=eduCourseService.getHotCourse();

        List<EduTeacher> eduTeacherList=eduTeacherService.getHotTeacher();

        return R.ok().data("courseList",eduCourseList).data("teacherList",eduTeacherList);
    }

}
