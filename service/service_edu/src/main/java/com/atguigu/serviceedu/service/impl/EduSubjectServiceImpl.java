package com.atguigu.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.serviceedu.entity.EduSubject;
import com.atguigu.serviceedu.entity.excel.SubjectData;
import com.atguigu.serviceedu.entity.subject.OneSubject;
import com.atguigu.serviceedu.entity.subject.TwoSubject;
import com.atguigu.serviceedu.listener.SubjectExcelListener;
import com.atguigu.serviceedu.mapper.EduSubjectMapper;
import com.atguigu.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            InputStream inputStream=file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        //1.查询所有的一级分类 parent_id=0
        QueryWrapper<EduSubject> wrapperOne=new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSbjectsList = baseMapper.selectList(wrapperOne);
        // 继承了，ServiceImpl，其中注入了 baseMapper
        // 或使用 this.list(wrapperOne)


        //2.查询所有的二级分类 parent_id!=0
        QueryWrapper<EduSubject> wrapperTwo=new QueryWrapper<>();
        wrapperOne.ne("parent_id","0");
        List<EduSubject> twoSbjectsList = baseMapper.selectList(wrapperTwo);

        //最后的封装集合
        List<OneSubject> finalSubjectList=new ArrayList<>();

        //3.封装一级分类
        for (int i = 0; i < oneSbjectsList.size(); i++) {
            EduSubject eduSubject = oneSbjectsList.get(i);

            OneSubject oneSubject=new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);
            //Springboot下的工具类，把 eduSubject 中的数据 copy到 oneSubject
            //等价于：oneSubject.setId(oneSubject.getId());
            //等价于：oneSubject.setTitle(oneSubject.getTitle());

            //把一级分类放入最终结果
            finalSubjectList.add(oneSubject);

            //4.分装二级分类
            List<TwoSubject> twofinalSubjectList=new ArrayList<>();
            for (int j = 0; j < twoSbjectsList.size(); j++) {
                EduSubject twoSubject = twoSbjectsList.get(j);
                //判断二级分类的id是否和一级分类的id一样
                if (twoSubject.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject1 = new TwoSubject();
                    BeanUtils.copyProperties(twoSubject, twoSubject1);
                    twofinalSubjectList.add(twoSubject1);
                }
            }
            //把所有 二级分类 放入 一级分类
            oneSubject.setChildren(twofinalSubjectList);

        }

        return finalSubjectList;
    }
}
