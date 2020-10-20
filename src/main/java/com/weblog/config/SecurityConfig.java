package com.weblog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weblog.service.Impl.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.io.PrintWriter;

/**
 * @author G
 * @version 1.0
 * @date 2020/10/8 16:46
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginService loginService;
    @Bean
    PasswordEncoder passwordEncoder() {
        //以明文的方式存储
        //return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //在内存中配置用户密码，会覆盖配置文件中的内容
        auth.inMemoryAuthentication()
                .withUser("gqb")
                .password("123456")
                .roles("admin")
                //用and可以配置多个
                .and()
                .withUser("wzs")
                .password("123456")
                .roles("user");
    }*/
    //跟上面一样的配置
 /*   @Override
    @Bean
    protected UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("gqb").password("123456").roles("admin").build());
        manager.createUser(User.withUsername("wzs").password("123456").roles("admin").build());
        return manager;
    }*/

    /**
     * 从数据库读取用户密码
     * @return
     */
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(loginService);
    }

    @Bean
    RoleHierarchy roleHierarchy() {
        //继承角色关系
        RoleHierarchyImpl hierarchyImpl = new RoleHierarchyImpl();
        //要带前缀
        hierarchyImpl.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchyImpl;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //不拦截静态资源
        // web.ignoring().antMatchers("/**");
        //注册不拦截
        web.ignoring().antMatchers("/user/register");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //配置自定义登录页
        http.authorizeRequests()
                //从上往下匹配拦截规则,顺序很重要！
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                .antMatchers("/article/**").hasRole("user")
                //所有请求认证后才能访问
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //表单接口没配置的情况下默认也叫login.html
                //.loginPage("/login.html")
                .loginProcessingUrl("/doLogin")
                .usernameParameter("username")
                .passwordParameter("password")
                //登录成功之后服务端跳转
//                .successForwardUrl("/hello")
                //重定向
//                .defaultSuccessUrl("/hello")
                //登录成功回调
                .successHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    //获取当前用户信息
                    out.write(new ObjectMapper().writeValueAsString(authentication.getPrincipal()));
                    out.flush();
                    out.close();
                })
                //登录失败回调
                .failureHandler((req, resp, exception) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(exception.getMessage()));
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                //注销之后
                .logout()
//                .logoutUrl("/logout")
                .logoutSuccessHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString("登录注销"));
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .csrf().disable()
                //未登录返回json而不跳转到login
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, exception) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString("请登录"));
                    out.flush();
                    out.close();
                });
    }
}
