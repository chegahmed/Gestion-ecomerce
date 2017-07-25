package com.entreprise.security;


import javax.sql.DataSource;

import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


	


@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth , DataSource dataSource) throws Exception {
		
		auth.eraseCredentials(false);
		
	    auth.jdbcAuthentication()  
		.dataSource(dataSource)
		.usersByUsernameQuery("select username as principal , password as credentials, true from user where username = ? ")
		.authoritiesByUsernameQuery("select username as principal , roleUser as role from user where username = ? ")
		.rolePrefix("ROLE_");
		

		
		
	}
	
	protected void configure(HttpSecurity http) throws Exception {
	
		
	//	http.authorizeRequests().antMatchers("/verifSerie2/**").permitAll();
	//	http.authorizeRequests().antMatchers("/verifSerie2").permitAll();
		http.authorizeRequests().antMatchers("/assets/**").permitAll();
	//	http.authorizeRequests().antMatchers("/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers("/auto/**").hasRole("ADMIN");
		
		http.authorizeRequests()
		.anyRequest().fullyAuthenticated()
		.and()
		.formLogin().loginPage("/login.html").failureUrl("/login.html").permitAll()
		.and()
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login.html");
		
		http.authorizeRequests()
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.loginPage("/login.html")
		.permitAll()
		.defaultSuccessUrl("/index.html")
		.failureUrl("/login.html");
		

		 }
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/verifSerie2");
	  //  web.ignoring().antMatchers("/login.html");
	    web.ignoring().antMatchers("/login");
	}

}
