package com.zhang.code.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.code.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhang.code.vo.query.DepartmentQueryVo;

import java.util.List;


public interface DepartmentService extends IService<Department> {
    /**
     * 查询部门列表
     * @param departmentQueryVo
     * @return
     */
    List<Department> findDepartmentList(DepartmentQueryVo departmentQueryVo);

    /**
     * 查询上级部门列表
     * @return
     */
    List<Department> findParentDepartment();

    /**
     * 判断部门下是否有子部门
     * @return
     */
    boolean hasChildrenOfDepartment(Long id);

    /**
     * 判断部门下是否存在用户
     * @param id
     * @return
     */
    boolean hasUserOfDepartment(Long id);
}
