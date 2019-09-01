package bos.rick;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.*;


public class VINBackend {

    private static Connection conn_;

    public static Connection getConnection() {
        if (conn_ == null ) {
            try {
                conn_ = DriverManager.getConnection("jdbc:postgresql://mydb:5432/postgres", "rick", "brodie1");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn_;
    }

    public static String Reset(){
        conn_ = null;
        return "reset vin";
    }



    public static String Test() {
        StringBuffer sb = new StringBuffer();
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from vindb.vin");

            while ( rs.next()) {
                sb.append(rs.getString("vin")+"\n");
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();


    }
}