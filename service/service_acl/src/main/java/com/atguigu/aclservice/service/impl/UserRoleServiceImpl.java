package com.atguigu.aclservice.service.impl;

import com.atguigu.aclservice.entity.UserRole;
import com.atguigu.aclservice.mapper.UserRoleMapper;
import com.atguigu.aclservice.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
