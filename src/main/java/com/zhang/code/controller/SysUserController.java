package com.zhang.code.controller;

import com.zhang.code.config.redis.RedisService;
import com.zhang.code.entity.Permission;
import com.zhang.code.entity.User;
import com.zhang.code.entity.UserInfo;
import com.zhang.code.utils.JwtUtils;
import com.zhang.code.utils.MenuTree;
import com.zhang.code.utils.Result;
import com.zhang.code.vo.RouterVo;
import com.zhang.code.vo.TokenVo;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sysUser")
public class SysUserController {

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private RedisService redisService;

    /**
     * 刷新token的方法
     *
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/refreshToken")
    public Result refreshToken(HttpServletRequest httpServletRequest) {
        // 从headers中获取token信息
        String token = httpServletRequest.getHeader("token");

        // 判断headers头部是否存在token信息
        if (ObjectUtils.isEmpty(token)) {
            token = httpServletRequest.getParameter("token");
        }

        // 从spring security上下文中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 获取用户身份信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // 定义变量，保存新的token信息
        String newToken = "";
        // 验证提交过来的token信息是否是合法的, 如果合法
        if (jwtUtils.validateToken(token, userDetails)) {
            // 重新生成新的token
            newToken = jwtUtils.refreshToken(token);
        }

        // 获取本次token到期的时间
        long expireTime = Jwts.parser()
                .setSigningKey(jwtUtils.getSecret())
                .parseClaimsJws(newToken.replace("jwt_", ""))
                .getBody().getExpiration().getTime();

        // 清除原来的token信息
        String oldTokenKey = "token_" + token;
        redisService.del(oldTokenKey);

        // 将新的token保存到缓存中
        String newTokenKey = "token_" + newToken;
        redisService.set(newTokenKey, newToken, jwtUtils.getExpiration() / 1000);
        // 创建TokenVo对象
        TokenVo tokenVo = new TokenVo(expireTime, newToken);
        // 返回数据
        return Result.ok(tokenVo).message("token刷新成功");
    }

    /**
     * 查询用户信息
     * @return
     */
    @GetMapping("/getInfo")
    public Result getInfo() {
        // 从spring security上下文中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 判断身份信息authentication是否是空的
        if(authentication == null){
            return Result.error().message("用户信息查询失败");
        }

        // 获取用户的信息, 这里不需要指定用户id, 因为上面的 authentication 就是从token里获取当前用户信息
        User user = (User) authentication.getPrincipal();
        // 获取该用户拥有的角色权限
        List<Permission> permissionList = user.getPermissionList();
        Object[] roles = permissionList.stream().filter(Objects::nonNull).map(
                item -> item.getCode()
        ).toArray();

        // 创建用户信息
        UserInfo userInfo = new UserInfo(
                user.getId(),user.getNickName(), user.getAvatar(), null, roles
        );

        return Result.ok(userInfo).message("用户信息查询成功");
    }

    @GetMapping("/getMenuList")
    public Result getMenuList(){
        // 从spring security上下文中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 获取用户的信息
        User user = (User) authentication.getPrincipal();
        // 获取该用户拥有的角色权限
        List<Permission> permissionList = user.getPermissionList();
        // 筛选当前用户拥有的目录和菜单数据
        List<Permission> collect = permissionList.stream()
                // 只筛选目录和菜单数据, 按钮不需要添加到路由中
                .filter(item -> item != null && item.getType() != 2) // 0-目录 1-菜单 2-按钮
                .collect(Collectors.toList());

        // 生成路由数据
        List<RouterVo> routerVoList = MenuTree.makeRouter(collect, 0L);
        // 返回数据
        return Result.ok(routerVoList).message("菜单数据获取成功");
    }

    /**
     * 退出登录
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    @PostMapping("/logout")
    public Result logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        // 获取token信息
        // 头部获取
        String token = httpServletRequest.getHeader("token");
        // 如果头部中没token, 从param获取
        if(ObjectUtils.isEmpty(token)){
            token = httpServletRequest.getParameter("token");
        }

        // 从spring security上下文对象获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 判断用户信息是否为空, 不为空, 则需要清空用户信息
        if(authentication != null){
            // 清除用户信息
            // 1.清除上下文中的用户登录信息
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, authentication);
            // 2.清除redis里的缓存token
            redisService.del("token_" + token);

            return Result.ok().message("用户退出成功");

        }

        return Result.error().message("用户退出失败");

    }
}
