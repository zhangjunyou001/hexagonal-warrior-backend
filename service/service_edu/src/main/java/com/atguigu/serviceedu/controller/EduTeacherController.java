package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.servicebase.config.exception.GuliException;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.entity.vo.TeacherQuery;
import com.atguigu.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api(description="讲师管理") //swagger提示
//@CrossOrigin //跨域
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }


    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("removeById/{id}")
    public R removeById(@PathVariable String id){
        boolean flag=eduTeacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageTeacher/{page}/{limit}")
    public R TeacherPageList(@PathVariable Long page, @PathVariable Long limit){
        Page<EduTeacher> pageParam=new Page<>(page,limit);
        eduTeacherService.page(pageParam,null);
        List<EduTeacher> records = pageParam.getRecords();
        long total=pageParam.getTotal();
        return R.ok().data("total",total).data("rows",records);
    }


    @ApiOperation(value = "分页讲师列表")
    @PostMapping("PageCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();

        //多条件组合查询（动态sql组合）
        if (!StringUtils.isEmpty(teacherQuery.getName())){
            //构造条件（这里“name”是mysql中的列名，后面一样）
            wrapper.like("name",teacherQuery.getName());
        }
        if (!StringUtils.isEmpty(teacherQuery.getLevel())){
            wrapper.eq("level",teacherQuery.getLevel());
        }
        if (!StringUtils.isEmpty(teacherQuery.getBegin())){
            wrapper.ge("gmt_create",teacherQuery.getBegin());
        }
        if (!StringUtils.isEmpty(teacherQuery.getEnd())){
            wrapper.le("gmt_create", teacherQuery.getEnd());
        }
        //排序
        wrapper.orderByDesc("gmt_create");
        //调用方法实现条件查询分页
        eduTeacherService.page(pageTeacher,wrapper);
        //
        List<EduTeacher> records = pageTeacher.getRecords();
        //
        long total=pageTeacher.getTotal();

        return R.ok().data("total",total).data("rows",records);

    }

    @GetMapping("selectByName/{username}")
    public R SelectByName(@PathVariable String username){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", username);
        List<EduTeacher> eduTeachers= (List<EduTeacher>) eduTeacherService.listByMap(map);
        eduTeachers.forEach(System.out::println);
        return R.ok().data("data",eduTeachers);
    }


    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("selectById/{id}")
    public R getById(@PathVariable String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }


    @ApiOperation(value = "根据ID修改讲师")
    @PostMapping("updateTeacherById/{id}")
    public R updateById(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }


    @ApiOperation(value = "添加或修改讲师")
    @PostMapping("saveOrUpdateTeacher")
    public R saveOrUpdateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.saveOrUpdate(eduTeacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }


    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }


    @ApiOperation(value = "测试自定义异常")
    @PostMapping("testException")
    public R testException(){
        try {
            int a = 10/0;
        }catch(Exception e) {
            throw new GuliException(20001,"出现自定义异常");
        }
        return R.ok();
    }

}

