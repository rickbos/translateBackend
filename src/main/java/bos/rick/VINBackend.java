package bos.rick;
import org.json.JSONArray;
import org.json.JSONObject;
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
                Class.forName("org.postgresql.Driver");
                conn_ = DriverManager.getConnection("jdbc:postgresql://mydb:5432/rick", "rick", "brodie1");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return conn_;
    }

    public static String Reset(){
        conn_ = null;
        return "reset vin";
    }


    private static String ResultSetToJson(ResultSet rs) throws SQLException {
        JSONArray json = new JSONArray();
        ResultSetMetaData rsmd = rs.getMetaData();
        while(rs.next()) {
            int numColumns = rsmd.getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i=1; i<=numColumns; i++) {
                String column_name = rsmd.getColumnName(i);
                obj.put(column_name, rs.getObject(column_name));
            }
            json.put(obj);
        }
        return json.toString(3);
    }
    public static String allVins() {
        StringBuffer sb = new StringBuffer();
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from vindb.vin");

            sb.append(ResultSetToJson(rs));
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();


    }
    public static String searchVin(String vin) {
        StringBuffer sb = new StringBuffer();
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from vindb.vin where vin = "  + vin);

            sb.append(ResultSetToJson(rs));
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();


    }
}