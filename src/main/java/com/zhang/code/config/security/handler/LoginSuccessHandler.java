package com.zhang.code.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhang.code.config.redis.RedisService;
import com.zhang.code.entity.User;
import com.zhang.code.utils.JwtUtils;
import com.zhang.code.utils.LoginResult;
import com.zhang.code.utils.ResultCode;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * 登录认证成功处理器类
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 设置响应的编码格式
        response.setContentType("application/json;charset=utf-8");

        // 获取当前登录用户信息
        User user = (User) authentication.getPrincipal();


        // ----------------------- 生成token -------------------------
        String token = jwtUtils.generateToken(user);
        // 设置token的签名密钥及过期时间
        long expireTime = Jwts.parser()
                .setSigningKey(jwtUtils.getSecret()) // 设置签名密钥
                .parseClaimsJws(token.replace("jwt_", ""))
                .getBody().getExpiration().getTime(); // 设置过期时间



        // 创建LoginResult登录结果对象
        // 这里我们登录成功, 返回结果不返回用户各种信息了，直接给个用户在数据库里的id
        LoginResult loginResult = new LoginResult(user.getId(), ResultCode.SUCCESS, token, expireTime);

        // ------------------------------------------------------------

        // 将对象转换成json格式, 并消除循环引用
        String result = JSON.toJSONString(loginResult, SerializerFeature.DisableCircularReferenceDetect);

        // 获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

        // 将token信息保存到redis中
        String tokenKey = "token_" + token;
//        redisService.set(tokenKey, token, jwtUtils.getExpiration() / 1000);
    }
}
