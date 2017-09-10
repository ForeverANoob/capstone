
package stumasys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder; // RIP 80 column limit, the Spring framework took you with brutal vigour.
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.beans.factory.annotation.Qualifier;

/*
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
*/

@Configuration
@EnableWebSecurity
public class WebSecurityCfg extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests() 
                /*
                .antMatchers("/login_/*").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/final").permitAll()
                .antMatchers("/greeting").permitAll() // auto login
                .antMatchers("/login#").permitAll()
                .antMatchers("/course/**").permitAll()
                //.antMatchers("/admin/**").hasRole("ADMIN_STAFF")
                //.anyRequest().authenticated()
                */
                .anyRequest().permitAll() // TODO: stage 4: correct the permissions to not allow arbitrary people to access effectively everything
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            //.httpBasic()    // http page
            //    .and()
            .logout()     // auto logout ?
                .permitAll();
        http.csrf().disable(); // TODO: stage 4: re-enable this, it's a security feature that helps stop XSS attacks. causing issues right now, so it's getting disable til we've got time.
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("as123").password("123qwe").roles("ADMIN_STAFF"); // TODO: stage 4: replace with real, database-powered user system, with more than one user type */


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
