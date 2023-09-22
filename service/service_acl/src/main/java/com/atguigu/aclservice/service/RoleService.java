package com.atguigu.aclservice.service;

import com.atguigu.aclservice.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


public interface RoleService extends IService<Role> {

    Map<String, Object> findRoleByUserId(String userId);

    void saveUserRoleRealtionShip(String userId, String[] roleId);

    List<Role> selectRoleByUserId(String id);
}
