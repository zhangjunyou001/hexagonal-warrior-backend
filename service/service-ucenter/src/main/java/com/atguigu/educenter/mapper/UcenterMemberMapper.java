package com.atguigu.educenter.mapper;

import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer countRegister(String day);
}
