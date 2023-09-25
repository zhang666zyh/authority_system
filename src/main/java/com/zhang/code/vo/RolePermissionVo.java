package com.zhang.code.vo;

import com.zhang.code.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限菜单数据回显vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionVo {
    /**
     * 菜单数据
     */
    private List<Permission> permissionList = new ArrayList<>();

    /**
     * 该角色拥有的菜单权限数据
     */
    private Object[] checkList;
}
