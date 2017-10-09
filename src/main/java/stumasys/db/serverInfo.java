package stumasys.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.util.Properties;

public class serverInfo{

    private static String portNumber = "3306";
    private static String userName = "root";
    private static String password = "j9bct840s";
    private static String dbms = "mysql";
    private static String serverName = "localhost";
    private static String dbName = "testing";
    private static Connection con;

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);

        if(dbms.equals("mysql")) {
            conn = DriverManager.getConnection(
                   "jdbc:" + dbms + "://" +
                   serverName +
                   ":" + portNumber + "/",
                   connectionProps);
           System.out.println("jdbc:" + dbms + "://" +  serverName + ":" + portNumber + "/" + connectionProps);
        } else if (dbms.equals("derby")){
            conn = DriverManager.getConnection(
                   "jdbc:" + dbms + ":" +
                   dbName +
                   ";create=true",
                   connectionProps);
                   System.out.println("Give up");
        }
        System.out.println();
        con = conn;
        return conn;
    }

    public static String getSqlServer(){

        return"jdbc:" + dbms + "://" + serverName + ":" + portNumber + "/";
    }

    public static Connection getCon(){
        return con;
    }
    public static DataSource getDataSource(){
        return ;
    }

}
