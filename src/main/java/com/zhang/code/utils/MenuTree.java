package com.zhang.code.utils;

import com.zhang.code.entity.Permission;
import com.zhang.code.vo.RouterVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 生成菜单树工具类
 */
public class MenuTree {

    /**
     * 生成路由
     * @param menuList 菜单列表
     * @param pid 父菜单的id
     * @return
     */
    public static List<RouterVo> makeRouter(List<Permission> menuList, Long pid){
        // 创建集合保存路由信息
        List<RouterVo> routerVoList = new ArrayList<RouterVo>();

        // 判断菜单列表是否为空, 如果不为空则使用菜单列表, 否则创建集合对象
        Optional.ofNullable(menuList).orElse(new ArrayList<Permission>())
                // 筛选不为空的菜单及与菜单父id相同的数据
                .stream().filter(item -> item!=null && item.getParentId() == pid)
                .forEach(item -> {
                    RouterVo routerVo = new RouterVo();
                    routerVo.setName(item.getName());
                    routerVo.setPath(item.getPath());
                    // 判断当前菜单是否是1级菜单
                    if(item.getParentId() == 0L){
                        routerVo.setComponent("Layout"); // 一级菜单组件
                        routerVo.setAlwaysShow(true); // 显示路由
                    }else{
                        routerVo.setComponent(item.getUrl()); // 具体某个组件
                        routerVo.setAlwaysShow(false); // 折叠路由
                    }

                    // 设置Meta信息
                    routerVo.setMeta(routerVo.new Meta(item.getLabel(), item.getIcon(), item.getCode().split(",")));

                    // 递归生成路由
                    List<RouterVo> children = makeRouter(menuList, item.getId());// 子菜单
                    routerVo.setChildren(children); // 设置子路由到路由对象中
                    // 将路由信息添加到集合中
                    routerVoList.add(routerVo);
                });

        // 返回路由信息
        return routerVoList;
    }

    /**
     * 生成菜单树
     * @param menuList
     * @param pid
     * @return
     */
    public static List<Permission> makeMenuTree(List<Permission> menuList, Long pid){
        // 创建集合保存菜单数据
        List<Permission> permissionList = new ArrayList<Permission>();

        // 判断菜单列表是否为空, 如果不为空则使用菜单列表, 否则创建集合对象
        Optional.ofNullable(menuList).orElse(new ArrayList<Permission>())
                .stream().filter(item -> item!=null && item.getParentId() == pid)
                .forEach(item -> {
                    // 创建权限菜单对象
                    Permission permission = new Permission();

                    // 将原有的属性复制给菜单对象
                    BeanUtils.copyProperties(item, permission);

                    // 获取每一个item对象的子菜单, 递归生成菜单树
                    List<Permission> children = makeMenuTree(menuList, item.getId());

                    // 设置子菜单
                    permission.setChildren(children);

                    // 将菜单对象添加到集合
                    permissionList.add(permission);
                });

        // 返回菜单信息
        return permissionList;
    }
}
