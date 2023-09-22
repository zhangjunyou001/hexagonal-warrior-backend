package com.atguigu.serviceedu.service;

import com.atguigu.serviceedu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface EduVideoService extends IService<EduVideo> {

    void removeVideoByCourseId(String courseId);
}
