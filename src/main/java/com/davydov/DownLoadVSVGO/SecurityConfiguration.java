package com.davydov.DownLoadVSVGO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/*")
        .authenticated()
        .and()
        .httpBasic()
        .and()
        .anonymous()
        .principal("anonymous")
        .and()
        .rememberMe()
        .key("Some secret");
  }

  //    @Bean
  //    public PasswordEncoder passwordEncoder() {
  //        return NoOpPasswordEncoder.getInstance();
  //    }

  @Autowired
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser("admin").password("vostok").roles("ADMIN");
  }
}
