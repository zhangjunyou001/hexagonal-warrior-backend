package com.atguigu.serviceedu.service.impl;

import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.mapper.EduTeacherMapper;
import com.atguigu.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Cacheable(value = "eduTeacherList",key = "'eduTeacherList'")
    @Override
    public List<EduTeacher> getHotTeacher() {
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper=new QueryWrapper<>();
        eduTeacherQueryWrapper.orderByDesc("id");
        eduTeacherQueryWrapper.last("limit 4");
        List<EduTeacher> eduTeacherList = baseMapper.selectList(eduTeacherQueryWrapper);
        return eduTeacherList;
    }

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(pageTeacher,wrapper);

        List<EduTeacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();
        boolean hasNext = pageTeacher.hasNext();
        boolean hasPrevious = pageTeacher.hasPrevious();

        Map<String,Object> map=new HashMap<>();
        map.put("items",records);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        return map;
    }
}
