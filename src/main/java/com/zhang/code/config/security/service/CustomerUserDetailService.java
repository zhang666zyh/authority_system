package com.zhang.code.config.security.service;

import com.zhang.code.entity.Permission;
import com.zhang.code.entity.User;
import com.zhang.code.service.PermissionService;
import com.zhang.code.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerUserDetailService implements UserDetailsService {
    @Resource
    private UserService userService;

    @Resource
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 调用根据用户名查询用户信息的方法
        User user = userService.findUserByUserName(username);

        // 判断对象为空
        if(user == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        // 如果不为空
        /**
         * 查询当前用户拥有的权限列表
         */
        List<Permission> permissionListByUserId = permissionService.findPermissionListByUserId(user.getId());
        // 获取对应的权限编码
        List<String> codeList = permissionListByUserId.stream().filter(item -> item!=null)
                .map(item -> item.getCode())
                .filter(Objects::nonNull) // Objects:nonNull 等价于 item -> item!=null
                .collect(Collectors.toList());

        // 将权限编码列表转换为数组
        String [] strings = codeList.toArray(new String[codeList.size()]);
        // 设置权限列表
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(strings);
        // 将权限设置给用户对象
        user.setAuthorities(authorityList);
        // 设置该用户拥有的菜单信息
        user.setPermissionList(permissionListByUserId);

        // 查询成功
        return user;
    }
}
