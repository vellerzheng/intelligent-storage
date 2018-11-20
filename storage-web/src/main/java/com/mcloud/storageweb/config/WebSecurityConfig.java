package com.mcloud.storageweb.config;


import com.mcloud.storageweb.service.User.authSecurity.AuthenticFailureHandler;
import com.mcloud.storageweb.service.User.authSecurity.AuthenticSuccessHandler;
import com.mcloud.storageweb.service.User.impl.AuthUserRoleService;
import com.mcloud.storageweb.service.filter.CustomAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



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
                        "/loginUser",
                        "/logout",
                        "/verificodeServlet",
                        "/verificateCodeServlet",
                        "/img/validateCode",
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
                .antMatchers("/**").hasAnyRole("ROLE_USER","ROLE_ADMIN")
                .antMatchers("/filemanager","/cloudConf").authenticated() //用户登录后可访问
                .and() //Login Form configuration for all others
                .formLogin()
              //这里必须要写formLogin()，不然原有的UsernamePasswordAuthenticationFilter不会出现，也就无法配置我们重新的UsernamePasswordAuthenticationFilter
                  .loginPage("/login")
                 .loginProcessingUrl("/loginUser")       //自定义登录接口
/*                .usernameParameter("username")
                .passwordParameter("password")*/
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
        //配置form和json都可以提交，用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }


    //注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(new AuthenticSuccessHandler());
        filter.setAuthenticationFailureHandler(new AuthenticFailureHandler());
        filter.setFilterProcessesUrl("/loginUser"); //自定义longin验证API

        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
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
