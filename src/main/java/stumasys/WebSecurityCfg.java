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

    DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
       auth.jdbcAuthentication().dataSource(dataSource)
    		.usersByUsernameQuery(
    		    "select id,password,enabled from users.user_info where id=?")
    		.authoritiesByUsernameQuery(
            	"select id,role from users.user_info where id=?");
    	}
}
