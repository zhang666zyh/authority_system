package com.zhang.code.controller;


import com.zhang.code.entity.Permission;
import com.zhang.code.service.PermissionService;
import com.zhang.code.utils.Result;
import com.zhang.code.vo.query.PermissionQueryVo;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    /**
     * 查询菜单列表
     * @param permissionQueryVo
     * @return
     */
    @GetMapping("/list")
    public Result list(PermissionQueryVo permissionQueryVo){
        // 调用查询菜单列表的方法
        List<Permission> permissionList = permissionService.findPermissionList(permissionQueryVo);

        // 返回数据
        return Result.ok(permissionList);
    }

    /**
     * 查询上级菜单列表
     * @param
     * @return
     */
    @GetMapping("/parent/list")
    public Result getParentList(){
        // 调用查询上级菜单列表的方法
        List<Permission> permissionList = permissionService.findParentPermissionList();

        // 返回数据
        return Result.ok(permissionList);
    }

    /**
     * 新增菜单
     * @param
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Permission permission){
        // 调用添加菜单的方法
        if(permissionService.save(permission)){
            return Result.ok().message("菜单添加成功");
        }

        return Result.error().message("菜单添加失败");
    }

    /**
     * 修改菜单
     * @param
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Permission permission){
        // 调用修改菜单的方法
        if(permissionService.updateById(permission)){
            return Result.ok().message("菜单修改成功");
        }

        return Result.error().message("菜单修改失败");
    }

    /**
     * 删除菜单
     * @param
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result add(@PathVariable Long id){
        // 调用删除菜单的方法
        if(permissionService.removeById(id)){
            return Result.ok().message("菜单删除成功");
        }

        return Result.error().message("菜单删除失败");
    }

    /**
     * 新增菜单
     * @param
     * @return
     */
    @GetMapping("/check/{id}")
    public Result check(@PathVariable Long id){
        // 判断菜单是否有子菜单
        if(permissionService.hasChildrenOfPermission(id)){
            return Result.exist().message("该菜单下有子菜单, 无法删除");
        }

        return Result.ok();
    }
}

