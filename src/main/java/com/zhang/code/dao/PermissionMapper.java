package com.zhang.code.dao;

import com.zhang.code.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据用户id查询权限菜单列表
     * @Return Permission
      */
    List<Permission> findPermissionListByUserId(Long userId);

    /**
     * 根据角色id查询权限列表
     * @param roleId
     * @return
     */
    List<Permission> findPermissionListByRoleId(Long roleId);
}
