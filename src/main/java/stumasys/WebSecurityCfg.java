
package stumasys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder; // RIP 80 column limit, the Spring framework took you with brutal vigour.
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.sql.DataSource;

/*
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
*/
//@Configuration
@EnableWebSecurity
public class WebSecurityCfg extends WebSecurityConfigurerAdapter {

/*
    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }
*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
                .antMatchers("/").authenticated()
                .anyRequest().permitAll() // TODO: stage 4: correct the permissions to not allow arbitrary people to access effectively everything
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
            .logout().logoutSuccessUrl("/login");
        http.csrf().disable(); // TODO: stage 4: re-enable this, it's a security feature that helps stop XSS attacks. causing issues right now, so it's getting disable til we've got time.

    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
            .withUser("brrand016").password("qwe").roles("STUDENT").and()
            .withUser("grncla007").password("qwe").roles("STUDENT").and()
            .withUser("krydan003").password("qwe").roles("STUDENT").and()
            .withUser("xyzmlg420").password("qwe").roles("STUDENT").and()

            .withUser("100001").password("qwe").roles("LECTURER").and()
            .withUser("100002").password("qwe").roles("LECTURER").and()
            .withUser("100003").password("qwe").roles("LECTURER").and()
            .withUser("100004").password("qwe").roles("LECTURER").and()

            .withUser("200001").password("qwe").roles("ADMIN_STAFF").and()
            .withUser("200002").password("qwe").roles("ADMIN_STAFF");
    }
    /*
    @Bean
	public UserDetailsService userDetailsService() throws Exception {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("user").password("123").roles("ADMIN_STAFF").build());
		return manager;
	}
    */
}
