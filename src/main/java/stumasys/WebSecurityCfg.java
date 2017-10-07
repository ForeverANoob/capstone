package stumasys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder; // RIP 80 column limit, the Spring framework took your life with brutal vigour.
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.sql.DataSource;

/* The methods in this class are called by the Spring Security framework to
 * configure it's behaviour.
 */

@EnableWebSecurity
public class WebSecurityCfg extends WebSecurityConfigurerAdapter {

/* TODO: JDBC authentication
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
                // We allow anyone to access the static css, js and image files
                .antMatchers("/css/*").permitAll()
                .antMatchers("/js/*").permitAll()
                .antMatchers("/img/*").permitAll()

                // but all other URIs require authentication (TODO: of appropriate levels) to function
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
            .logout().logoutSuccessUrl("/login");
        http.csrf().disable(); // TODO: re-enable this, it's a security feature, but it's messing with attempts at writing a simple REST api

    }

    // configures the basic in-memory authentication service
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

    @Autowired
    	DataSource dataSource;

    	@Autowired
    	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

    	  auth.jdbcAuthentication().dataSource(dataSource)
    		.usersByUsernameQuery(
    			"select username,password, enabled from users where username=?")
    		.authoritiesByUsernameQuery(
    			"select username, role from user_roles where username=?");
    	}

    	@Override
    	protected void configure(HttpSecurity http) throws Exception {

    	  http.authorizeRequests()
    		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
    		.and()
    		  .formLogin().loginPage("/login").failureUrl("/login?error")
    		  .usernameParameter("username").passwordParameter("password")
    		.and()
    		  .logout().logoutSuccessUrl("/login?logout")
    		.and()
    		  .exceptionHandling().accessDeniedPage("/403")
    		.and()
    		  .csrf();
    	}


}
