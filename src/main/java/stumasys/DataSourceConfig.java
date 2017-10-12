package stumasys;

import org.mariadb.jdbc.MariaDbDataSource; // the alternative to: import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {
    private static String portNumber = "3306";
    private static String userName = "root";
    private static String password = "j9bct840s";
    private static String dbms = "mysql";
    private static String serverName = "localhost";

    @Bean(name = "dataSource")
    public static DriverManagerDataSource dataSource() {
        DriverManagerDataSource dmds = null;

        try {

            dmds = new DriverManagerDataSource();

            dmds.setDriverClassName("org.mariadb.jdbc.Driver");
            dmds.setUrl("jdbc:" + dbms + "://" + serverName + ":" + portNumber + "/");
            dmds.setUsername(userName);
            dmds.setPassword(password);
            System.out.println("-><--><--><--><--><--><--><--><-");
            System.out.println(dmds);
            System.out.println("-><--><--><--><--><--><--><--><-");
        } catch (Exception e) {
            System.err.println("-><--><--><--><--><--><--><--><-");
            e.printStackTrace();
        }

        return dmds;
    }
}
