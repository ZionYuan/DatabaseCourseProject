package dbProject;

/**
 * Created by yuanzi on 2017/6/8.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public static List<String[]> showTablesData(String ip,String database,String username,String password,String table,int linenumbers,String sql) throws Exception {
        if(ConnectToDatebase(ip,database,username,password)) {
            List<String[]> tables_data_list = new ArrayList<>();
//            tables_data_list.add(showColunmsNames(ip,database,username,password,table));
            List<String> list = showColunmsNames(ip,database,username,password,table);
            String[] ss = new String[list.size()];
            for(int i =0;i<list.size();i++){
                ss[i] = list.get(i);
            }
            tables_data_list.add(ss);

            Statement statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            int columCounts = resultSet.getMetaData().getColumnCount();
            int line = 0;
            if(linenumbers == -1){
                while (resultSet.next()) {
                    String[] a = new String[columCounts];
                    for (int i = 0; i < columCounts; i++) {
                        a[i] = resultSet.getString(i + 1);
                    }
                    tables_data_list.add(a);
                    line++;
                }
            }else {
                resultSet.beforeFirst();
                while (resultSet.next() && line < linenumbers) {
                    String[] a = new String[columCounts];
                    for (int i = 0; i < columCounts; i++) {
                        a[i] = resultSet.getString(i + 1);
                    }
                    tables_data_list.add(a);
                    line++;
                }
            }
            return tables_data_list;
        }else{
            return null;
        }
    }
    public static List<String> showColunmsNames(String ip,String database,String username,String password,String table) throws SQLException {
        if(ConnectToDatebase(ip,database,username,password)){
            List<String> list = new ArrayList<>();
            String sql = "select * from " + table;
            Statement statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            for(int i = 1;i<resultSet.getMetaData().getColumnCount()+1;i++){
                list.add(resultSet.getMetaData().getColumnName(i));
            }
            return list;
        }
        return null;
    }
    public static Boolean Update(String ip,String database,String username,String password,String table,String sql) throws SQLException {
        if(ConnectToDatebase(ip,database,username,password)) {
            Statement statement = (Statement) connection.createStatement();
            int count = statement.executeUpdate(sql);
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
//        if(DBUtils.ConnectToDatebase("127fefefwef.0.1","Project","root","5647477230")){
//            System.out.print("yes");
//        }else{
//            System.out.print("no");
//        }
//        List<String> table_list = DBUtils.showColunmsNames("127.0.0.1","Project","root","5647477230","LINEITEM");
//        for(int i = 0;i<table_list.size();i++){
//            System.out.println(table_list.get(i));
//        }
//        List<String[]> list = DBUtils.showTablesData("127.0.0.1","Project","root","5647477230","NATION",-1,"update REGION set R_NAME = '00' where R_REGIONKEY = '122'");
//        for(String[] s : list){
//            for(int i = 0;i< s.length;i++){
//                System.out.println(s[i]);
//            }
//        }
        if(Update("127.0.0.1","Project","root","5647477230","NATION","update REGION set R_NAME = '10',R_COMMENT = '00' where R_REGIONKEY = '122'")){
            System.out.println("yes");
        }else {
            System.out.println("no");
        }
    }
}
