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

        QueryWrapper<EduSubject> wrapperOne=new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSbjectsList = baseMapper.selectList(wrapperOne);


        QueryWrapper<EduSubject> wrapperTwo=new QueryWrapper<>();
        wrapperOne.ne("parent_id","0");
        List<EduSubject> twoSbjectsList = baseMapper.selectList(wrapperTwo);

        List<OneSubject> finalSubjectList=new ArrayList<>();

        for (int i = 0; i < oneSbjectsList.size(); i++) {
            EduSubject eduSubject = oneSbjectsList.get(i);

            OneSubject oneSubject=new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);

            finalSubjectList.add(oneSubject);

            List<TwoSubject> twofinalSubjectList=new ArrayList<>();
            for (int j = 0; j < twoSbjectsList.size(); j++) {
                EduSubject twoSubject = twoSbjectsList.get(j);
                if (twoSubject.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject1 = new TwoSubject();
                    BeanUtils.copyProperties(twoSubject, twoSubject1);
                    twofinalSubjectList.add(twoSubject1);
                }
            }
            oneSubject.setChildren(twofinalSubjectList);

        }

        return finalSubjectList;
    }
}
