package com.atguigu.aclservice.service;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.aclservice.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PermissionService extends IService<Permission> {

    List<Permission> queryAllMenu();

    List<Permission> selectAllMenu(String roleId);

    void saveRolePermissionRealtionShip(String roleId, String[] permissionId);

    void removeChildById(String id);

    List<String> selectPermissionValueByUserId(String id);

    List<JSONObject> selectPermissionByUserId(String id);

    List<Permission> queryAllMenuGuli();

    void removeChildByIdGuli(String id);

    void saveRolePermissionRealtionShipGuli(String roleId, String[] permissionId);
}
