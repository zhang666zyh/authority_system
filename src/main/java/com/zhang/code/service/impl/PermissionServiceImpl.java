package com.zhang.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.code.dao.UserMapper;
import com.zhang.code.entity.Permission;
import com.zhang.code.dao.PermissionMapper;
import com.zhang.code.entity.User;
import com.zhang.code.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhang.code.utils.MenuTree;
import com.zhang.code.vo.RolePermissionVo;
import com.zhang.code.vo.query.PermissionQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;


@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<Permission> findPermissionListByUserId(Long userId) {
        return baseMapper.findPermissionListByUserId(userId);
    }

    /**
     * 查询菜单列表
     *
     * @param permissionQueryVo
     * @return
     */
    @Override
    public List<Permission> findPermissionList(PermissionQueryVo permissionQueryVo) {
        // 创建条件构造器对象
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        // 设置排序自动
        queryWrapper.orderByAsc("order_num");
        // 调用查询菜单列表的方法
        List<Permission> permissionList = baseMapper.selectList(queryWrapper);
        // 生成菜单树
        List<Permission> menuTree = MenuTree.makeMenuTree(permissionList, 0L);

        return menuTree;
    }

    /**
     * 查询上级菜单列表
     *
     * @param
     * @return
     */
    @Override
    public List<Permission> findParentPermissionList() {
        // 创建条件构造器对象
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        // 只查询目录(0)和菜单(1)的数据
        queryWrapper.in("type", Arrays.asList(0, 1));
        // 设置排序自动
        queryWrapper.orderByAsc("order_num");
        // 调用查询菜单列表的方法
        List<Permission> permissionList = baseMapper.selectList(queryWrapper);
        // 构造顶级菜单对象
        Permission permission = new Permission();
        permission.setId(0L);
        permission.setParentId(-1L);
        permission.setLabel("顶级菜单");
        // 将permission对象添加到集合
        permissionList.add(permission);

        // 生成菜单数据
        List<Permission> menuTree = MenuTree.makeMenuTree(permissionList, -1L);
        return menuTree;
    }

    /**
     * 判断菜单是否有子菜单
     *
     * @param id
     * @return
     */
    @Override
    public boolean hasChildrenOfPermission(Long id) {
        // 创建条件构造器对象
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        // 查询父级id
        queryWrapper.eq("parent_id", id);
        // 判断数量是否大于0, 如果大于0则表示存在
        if (baseMapper.selectCount(queryWrapper) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public RolePermissionVo findPermissionTree(Long userId, Long roleId) {
        // 查询出当前用户信息
        User user = userMapper.selectById(userId);
        // 创建集合保存权限菜单
        List<Permission> list = null;

        /**
         * 判断当前登录用户是否是管理员
         * 如果是，则查询所有权限信息
         * 如果不是，则需要根据当前用户id查询出当前用户所拥有的权限信息
         */
        if (user != null && ObjectUtils.isEmpty(user.getIsAdmin()) && user.getIsAdmin() == 1) {
            // 是admin, 查询出所有权限信息
            list = baseMapper.selectList(null);
        } else {
            // 不是, 根据当前用户id查询出当前用户所有权限信息
            list = baseMapper.findPermissionListByUserId(userId);
        }

        // 将登录用户所拥有的菜单权限封装成菜单树
        List<Permission> permissionList = MenuTree.makeMenuTree(list, 0L);
        // 查询要分配角色拥有的权限列表
        List<Permission> rolePermissions = baseMapper.findPermissionListByRoleId(roleId);

        // 创建集合保存权限id
        List<Long> listIds = new ArrayList<>();

        // 进行数据回显
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .stream().filter(Objects::nonNull)
                .forEach(item -> {
                    Optional.ofNullable(rolePermissions).orElse(new ArrayList<>())
                            .stream()
                            .filter(Objects::nonNull)
                            .forEach(obj -> {
                                /**
                                 * 判断两者的权限id是否一致
                                 * 如果一致，则表示拥有权限
                                 */
                                if (item.getId().equals(obj.getId())) {
                                    // 将权限id保存到集合中
                                    listIds.add(obj.getId());
                                    return;
                                }
                            });
                });

        // 创建RolePermissionVo类对象
        RolePermissionVo vo = new RolePermissionVo(permissionList, listIds.toArray());

        return vo;
    }
}
