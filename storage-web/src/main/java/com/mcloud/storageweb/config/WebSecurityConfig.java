package com.mcloud.storageweb.config;

import com.mcloud.storageweb.service.User.impl.AuthUserRoleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 16:51 2018/6/12
 * @Modify By:
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        //忽略css.jq.img等文件
        web.ignoring().antMatchers("/css/**.css", "/error/**", "/fonts/**", "/images/**", "/js/**.js","/third-party/**");
    }

    @Override
    protected  void configure(HttpSecurity http) throws Exception {

        http.csrf().disable() //HTTP with Disable CSRF
                .authorizeRequests() //Authorize Request Configuration
                .antMatchers(
                        "/",
                        "/index",
                        "/register",
                        "/login",
                        "/logout",
                        "/test/**",
                        "/api/**",
                        "/static/**",
                        "/filemanager/**",
                        "/**/flyway",
                        "/**/auditevents",
                        "/**/jolokia").permitAll() //放开"/api/**"：为了给被监控端免登录注册并解决Log与Logger冲突
                .anyRequest().authenticated()
          //      .and()
           //     .authorizeRequests()
           //     .antMatchers("/**").hasRole("user")
        //        .antMatchers("/**").authenticated()
                .and() //Login Form configuration for all others
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .sessionRegistry(getSessionRegistry())
                .and() //Logout Form configuration
                .and()
                .logout()
                .deleteCookies("remove")
                .logoutSuccessUrl("/").permitAll()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)  throws Exception {
        auth.userDetailsService(AuthUserRoleService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    UserDetailsService AuthUserRoleService(){ //注册UserDetailsService 的bean
        return new AuthUserRoleService();
    }

    @Bean
    public SessionRegistry getSessionRegistry(){
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }

    /**

     * 密码加密

     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();

    }
}
