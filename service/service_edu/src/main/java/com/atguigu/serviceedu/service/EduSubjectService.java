package com.atguigu.serviceedu.service;

import com.atguigu.serviceedu.entity.EduSubject;
import com.atguigu.serviceedu.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface EduSubjectService extends IService<EduSubject> {

    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    public List<OneSubject> getAllOneTwoSubject();
}
