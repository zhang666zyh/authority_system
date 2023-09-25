package com.zhang.code.config.security.filter;

import com.zhang.code.config.redis.RedisService;
import com.zhang.code.config.security.exception.CustomerAuthenticationException;
import com.zhang.code.config.security.handler.LoginFailureHandler;
import com.zhang.code.config.security.service.CustomerUserDetailService;
import com.zhang.code.utils.JwtUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Data
@Component
public class CheckTokenFilter extends OncePerRequestFilter {

    // 登录请求地址
    @Value("${request.login.url}")
    private String loginUrl;

    // redis工具类
    @Resource
    private RedisService redisService;

    // jwt工具类
    @Resource
    private JwtUtils jwtUtils;

    // 用户查询验证工具类
    @Resource
    private CustomerUserDetailService customerUserDetailService;

    @Resource
    private LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            // 获取当前请求的url地址
            String requestURI = request.getRequestURI();
            System.out.println(requestURI);

            // 判断当前请求是否是登录请求, 如果不是登录请求, 则需要验证token
            if(!requestURI.equals(loginUrl)){
                // 进行token验证
                this.validateToken(request);
            }
        }catch(AuthenticationException e){
            // 验证失败
            loginFailureHandler.onAuthenticationFailure(request, response, e);
        }

        // 登录请求不需要携带token, 可以直接放行
        doFilter(request, response, filterChain);
    }

    /**
     * 验证token信息
     * @param request
     */
    private void validateToken(HttpServletRequest request) throws CustomerAuthenticationException {
        // 要获取前端提交过来的token信息(header或params)
        // 先从header头部获取token信息
        String token = request.getHeader("token");

        // 如果header没有token, 从params中取token
        if(ObjectUtils.isEmpty(token)){
            System.out.println(1);
            token = request.getParameter("token");
        }

        // 都没有token, 抛异常
        if(ObjectUtils.isEmpty(token)){
            System.out.println(2);
            throw new CustomerAuthenticationException("token不存在");
        }

//        // 判断redis里有没有token
//        String tokenKey = "token_" + token;
//        String redisToken = redisService.get(tokenKey);
//        // 判断redis中是否存在token信息, 如果没有，说明token失效
//        if(ObjectUtils.isEmpty(redisToken)){
//            throw new CustomerAuthenticationException("token已过期");
//        }
//
//        // 如果token和redis中token不一致，则也失败
//        if(!token.equals(redisToken)){
//            throw new CustomerAuthenticationException("token验证失败");
//        }

        // 如果token存在, 则从token中解析出用户名
        String usernameFromToken = jwtUtils.getUsernameFromToken(token);
        System.out.println(usernameFromToken);
        // 判断用户名是否为空
        if(ObjectUtils.isEmpty(usernameFromToken)){
            throw new CustomerAuthenticationException("token解析失败");
        }

        // 获取用户信息
        UserDetails userDetails = customerUserDetailService.loadUserByUsername(usernameFromToken);

        // 判断用户信息是否为空
        if(userDetails == null){
            throw new CustomerAuthenticationException("token验证失败");
        }

        // 创建用户身份认证对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // 设置请求信息
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 将验证的信息交给Spring Security 上下文
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


    }
}
