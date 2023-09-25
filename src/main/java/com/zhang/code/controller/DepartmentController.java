package com.zhang.code.controller;


import com.zhang.code.entity.Department;
import com.zhang.code.service.DepartmentService;
import com.zhang.code.utils.Result;
import com.zhang.code.vo.query.DepartmentQueryVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;


    /**
     * 查询部门列表
     * @param departmentQueryVo
     * @return
     */
    @GetMapping("/list")
    public Result list(DepartmentQueryVo departmentQueryVo){
        // 调用查询部门列表方法
        List<Department> departmentList = departmentService.findDepartmentList(departmentQueryVo);

//        System.out.println(departmentList);

        // 返回数据
        return Result.ok(departmentList);
    }

    /**
     * 查询上级部门列表
     * @param
     * @return
     */
    @GetMapping("/parent/list")
    public Result getParentDepartment(){
        // 调用查询上级部门列表方法
        List<Department> departmentList = departmentService.findParentDepartment();

        // 返回数据
        return Result.ok(departmentList);
    }

    /**
     * 添加部门
     * @param
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Department department){
        if(departmentService.save(department)){
            return Result.ok().message("部门添加成功");
        }

        return Result.error().message("部门添加失败");
    }

    /**
     * 修改部门
     * @param
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Department department){
        if(departmentService.updateById(department)){
            return Result.ok().message("部门修改成功");
        }

        return Result.error().message("部门修改失败");
    }

    /**
     * 删除部门
     * @param
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        if(departmentService.removeById(id)){
            return Result.ok().message("部门删除成功");
        }

        return Result.error().message("部门删除失败");
    }

    /**
     * 检查某个部门下是否存在子部门
     * @param
     * @return
     */
    @GetMapping("/check/{id}")
    public Result check(@PathVariable Long id){

        // 判断该部门下子部门数量是否大于0
        if(departmentService.hasChildrenOfDepartment(id)){
            return Result.exist().message("该部门下存在子部门, 无法删除");
        }

        // 判断该部门下是否存在用户
        if(departmentService.hasUserOfDepartment(id)){
            return Result.exist().message("该部门下存在用户, 无法删除");
        }

        return Result.ok();
    }
}

