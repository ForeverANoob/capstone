package stumasys;

//import org.h2.jdbcx.JdbcDataSource;
//import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
//import org.apache.derby.jdbc.ClientDataSource;
//import oracle.jdbc.pool.OracleDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.commons.dbcp.BasicDataSource;
import java.util.Properties;

import javax.sql.DataSource;
import java.nio.file.Files;

import stumasys.db.serverInfo;


@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource createDataSource() {
        MysqlDataSource datasource = new MysqlDataSource();
        //datasource.
        return ;
    }
}
