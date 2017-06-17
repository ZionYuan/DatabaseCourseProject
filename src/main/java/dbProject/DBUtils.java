package dbProject;

/**
 * Created by yuanzi on 2017/6/8.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.sun.xml.internal.fastinfoset.util.StringArray;

public class DBUtils {
    private static Connection connection= null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    private static final String urlFrontHalf="jdbc:mysql://";
    private String username;
    private String password;

    public static Boolean ConnectToDatebase(String ip,String database,String username,String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(urlFrontHalf+ip+"/"+database,username,password);
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return false;
        }

    }
    public static List<String> showTables(String ip,String database,String username,String password) throws Exception {
        if(ConnectToDatebase(ip,database,username,password)) {
            String sql = "show tables";
            Statement statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            List<String> tables_list = new ArrayList<>();
            while (resultSet.next()) {
                tables_list.add(resultSet.getString(1));
            }
            return tables_list;
        }
        return null;
    }
    public static List<String[]> showTablesData(String ip,String database,String username,String password,String table) throws Exception {
        if(ConnectToDatebase(ip,database,username,password)) {
            List<String[]> tables_data_list = new ArrayList<>();
            String sql = "select * from " + table;
            Statement statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            int columCounts = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                String[] a = new String[columCounts];
                for (int i = 0; i < columCounts; i++) {
                    a[i] = resultSet.getString(i + 1);
                }
                tables_data_list.add(a);
            }
            return tables_data_list;
        }else{
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        if(DBUtils.ConnectToDatebase("127fefefwef.0.1","Project","root","5647477230")){
            System.out.print("yes");
        }else{
            System.out.print("no");
        }
//        List<String> table_list = DBUtils.showTablesData("CUSTOMER");
//        for(int i = 0;i<table_list.size();i++){
//            System.out.println(table_list.get(i));
//        }
//        List<String[]> list = DBUtils.showTablesData("CUSTOMER");
//        for(String[] s : list){
//            for(int i = 0;i< s.length;i++){
//                System.out.println(s[i]);
//            }
//        }
    }
}
