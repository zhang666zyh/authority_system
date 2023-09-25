package com.zhang.code.config.security;

import com.zhang.code.config.security.filter.CheckTokenFilter;
import com.zhang.code.config.security.handler.AnonymousAuthenticationHandler;
import com.zhang.code.config.security.handler.CustomerAccessDeniedHandler;
import com.zhang.code.config.security.handler.LoginFailureHandler;
import com.zhang.code.config.security.handler.LoginSuccessHandler;
import com.zhang.code.config.security.service.CustomerUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private LoginSuccessHandler loginSuccessHandler;
    @Resource
    private LoginFailureHandler loginFeatureHandler;
    @Resource
    private AnonymousAuthenticationHandler anonymousAuthenticationHandler;
    @Resource
    private CustomerAccessDeniedHandler customerAccessDeniedHandler;
    @Resource
    private CustomerUserDetailService customerUserDetailService;

    @Resource
    private CheckTokenFilter checkTokenFilter;

    /**
     * 注入加密类
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 处理登录认证
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 登录之前进行token过滤
        http.addFilterBefore(checkTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // super.configure(http);
        // 登录过程的处理
        http.formLogin() // 表单登录
                .loginProcessingUrl("/api/user/login") // 登录请求的url地址, 自定义即可
                .successHandler(loginSuccessHandler) // 认证成功处理器
                .failureHandler(loginFeatureHandler) // 认证失败处理器
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 不创建Session
                .and()
                .authorizeRequests()
                .antMatchers("/api/user/login").permitAll() // 登录请求放行
                .anyRequest().authenticated() // 其他一律请求都要拦截, 进行身份认证
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(anonymousAuthenticationHandler) // 匿名无权限访问
                .accessDeniedHandler(customerAccessDeniedHandler) // 认证用户访问无权限接口
                .and()
                .cors(); // 支持跨域请求
    }

    /**
     * 配置认证处理器的方法
     * 判断用户登录信息是否正确, 正确登录成功, 返回用户信息详情(用户对象)
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        auth.userDetailsService(customerUserDetailService).passwordEncoder(passwordEncoder());
    }
}
