package com.zhang.code.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhang.code.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhang.code.vo.query.UserQueryVo;

import java.util.List;

public interface UserService extends IService<User> {
    // 根据用户名查找用户
    User findUserByUserName(String username);

    /**
     * 分页查询用户信息
     *
     * @param page
     * @param userQueryVo
     * @return
     */
    IPage<User> findUserListByPage(IPage<User> page, UserQueryVo userQueryVo);

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    boolean deleteById(Long id);

    /**
     * 分配角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
    boolean saveUserRole(Long userId, List<Long> roleIds);
}
