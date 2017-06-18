package dbProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by yuanzi on 2017/6/8.
 */
public class MainFrame extends JFrame implements ActionListener {
    public static void main(String[] args) throws Exception {
        MainFrame mainFrame = new MainFrame("数据库管理系统");

    }
    JPanel LEFT,RIGHT;
    JPanel jp1,jp2,jp12,jp3,jp4,jp5;
    JLabel ip,database,username,password,info,show,linenumbers;
    JLabel tiaojian;
    JTextField ip_textfield,database_textfield,username_textfield,password_textfield,linenumbers_textfield,cx_columname,gx_content_field;
    JComboBox show_combobox;
    JButton connect,query,add,delete,hide,queryCondition,update;
    JButton tj,sc,gx,cx;
    JTextArea display,tiaojian_textfield,cx_tiaojian_textarea,gx_condition_area;

    public MainFrame(String title) throws Exception {
        super(title);
        setSize(1100,500);
        getContentPane().setLayout(
                new BoxLayout(getContentPane(), BoxLayout.X_AXIS)
        );
        LEFT = new JPanel();
        RIGHT = new JPanel();
        LEFT.setLayout(new BoxLayout(LEFT,BoxLayout.Y_AXIS));
        RIGHT.setLayout(new BoxLayout(RIGHT,BoxLayout.Y_AXIS));

        ip = new JLabel("ip地址：");
        ip_textfield = new JTextField(10);
        database = new JLabel("数据库名称：");
        database_textfield = new JTextField(10);
        jp1 = new JPanel();
        jp1.setLayout(new BoxLayout(jp1,BoxLayout.X_AXIS));
        jp1.add(ip);
        jp1.add(ip_textfield);
        jp1.add(database);
        jp1.add(database_textfield);

        username = new JLabel("用户名：");
        username_textfield = new JTextField(10);
        password = new JLabel("密码");
        password_textfield = new JTextField(10);
        connect = new JButton("连接");
        info = new JLabel("");
        info.setForeground(Color.red);
        info.setVisible(false);
        jp2 = new JPanel();
        jp2.setLayout(new BoxLayout(jp2,BoxLayout.X_AXIS));
        jp2.add(username);
        jp2.add(username_textfield);
        jp2.add(password);
        jp2.add(password_textfield);
        jp2.add(connect);
        jp2.add(info);

        jp3 = new JPanel();
        jp3.setLayout(new BoxLayout(jp3,BoxLayout.X_AXIS));
        show = new JLabel("选择表格:");
        show_combobox = new JComboBox();


        jp3.add(show);
        jp3.add(show_combobox);

        jp4 = new JPanel();
        jp4.setLayout(new BoxLayout(jp4,BoxLayout.X_AXIS));

        linenumbers = new JLabel("显示行数：");
        linenumbers_textfield = new JTextField(10);

//        queryCondition = new JLabel("查询条件");
//        queryConditionTextfield = new JTextField(10);

        query = new JButton("查看表数据");
        query.addActionListener(this);
        query.setEnabled(false);

        queryCondition = new JButton("查询数据");
        queryCondition.addActionListener(this);
        queryCondition.setEnabled(false);

        add = new JButton("增加数据");
        add.addActionListener(this);
        add.setEnabled(false);

        delete = new JButton("删除数据");
        delete.addActionListener(this);
        delete.setEnabled(false);

        update = new JButton("更新数据");
        update.addActionListener(this);
        update.setEnabled(false);

        hide = new JButton("隐藏侧边框");
        hide.addActionListener(this);

//        jp4.add(queryCondition);
//        jp4.add(queryConditionTextfield);
        jp4.add(linenumbers);
        jp4.add(linenumbers_textfield);
        jp4.add(query);
        jp4.add(queryCondition);
        jp4.add(add);
        jp4.add(delete);
        jp4.add(update);
        jp4.add(hide);


        jp5 = new JPanel();
        jp5.setLayout(new BoxLayout(jp5,BoxLayout.Y_AXIS));
        tj = new JButton("添加");
        RIGHT.add(tj,-1);
        tj.addActionListener(this);
        sc = new JButton("删除");
        RIGHT.add(sc,-1);
        sc.addActionListener(this);
        cx = new JButton("查询");
        cx.addActionListener(this);
        RIGHT.add(cx,-1);
        gx = new JButton("更新");
        gx.addActionListener(this);
        RIGHT.add(gx,-1);



        LEFT.add(jp1);
        LEFT.add(jp2);
        LEFT.add(jp3);
        LEFT.add(jp4);
        RIGHT.add(jp5);
        RIGHT.setVisible(false);


        display = new JTextArea(20,70);
        display.setEditable(false);

        //设置滚动框，并保持滚动条一直在最下面.
        JScrollPane scrollPane = new JScrollPane(display);
        display.setLineWrap(true);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        display.setCaretPosition(display.getText().length());
        LEFT.add(scrollPane);
        connect.addActionListener(this);


        add(LEFT);
        add(RIGHT);

        setVisible(true);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("连接")){
            if(!ip_textfield.getText().equals("")
                    && !database_textfield.getText().equals("")
                    && !username_textfield.getText().equals("")
                    && !password_textfield.getText().equals("")){
                info.setVisible(false);
                try {
                    if (!(DBUtils.ConnectToDatebase(ip_textfield.getText(),
                            database_textfield.getText(),
                            username_textfield.getText(),
                            password_textfield.getText()))) {
                        display.append("数据库连接失败！" + "\n=========================================");
                    }
                    else{
                        query.setEnabled(true);
                        add.setEnabled(true);
                        delete.setEnabled(true);
                        update.setEnabled(true);
                        queryCondition.setEnabled(true);
                        java.util.List<String> tables_list = DBUtils.showTables(ip_textfield.getText(),
                                                                                    database_textfield.getText(),
                                                                                    username_textfield.getText(),
                                                                                    password_textfield.getText()
                                                                                    );
                        show_combobox.removeAllItems();
                        for (int i = 0; i < tables_list.size(); i++) {
                            show_combobox.addItem(tables_list.get(i));
                        }
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        display.append("\n已连接数据库 "+database_textfield.getText()+"        "+time+"\n=========================================");
                        }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }


            }
            else{
                info.setVisible(true);
                info.setText("请填写完整信息");

            }
        }
        if(e.getActionCommand().equals("查询数据")){
            RIGHT.setVisible(true);
            sc.setVisible(false);
            cx.setVisible(true);
            tj.setVisible(false);
            gx.setVisible(false);
            jp5.removeAll();
            cx_columname = new JTextField(20);
            cx_tiaojian_textarea = new JTextArea(50,20);
            cx_tiaojian_textarea.setLineWrap(true);
            JLabel lieming = new JLabel("需要显示的列名");
            JLabel tiaojian = new JLabel("查询限制条件");
            jp5.add(lieming);
            jp5.add(cx_columname);
            jp5.add(tiaojian);
            jp5.add(cx_tiaojian_textarea);
        }
        if(e.getActionCommand().equals("查询")){
            String sql = "select "+cx_columname.getText()+" from "+show_combobox.getSelectedItem().toString()+" where "+cx_tiaojian_textarea.getText();
            showdata(sql);
        }
        if(e.getActionCommand().equals("查看表数据")){
            showdata("select * from "+show_combobox.getSelectedItem().toString());
        }
        int x = 1100;
        int y = 500;
        if(e.getActionCommand().equals("增加数据")){
            RIGHT.setVisible(true);
            sc.setVisible(false);
            cx.setVisible(false);
            tj.setVisible(true);
            gx.setVisible(false);
            jp5.removeAll();
//            jp5 = new JPanel();
//            jp5.setLayout(new BoxLayout(jp4,BoxLayout.Y_AXIS));
            List<String> columnNames = null;
            try {
                columnNames = DBUtils.showColunmsNames(ip_textfield.getText(),
                        database_textfield.getText(),
                        username_textfield.getText(),
                        password_textfield.getText(),
                        show_combobox.getSelectedItem().toString());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            for(int i=0;i<columnNames.size();i++){
                JPanel jPanel = new JPanel();
                JLabel jLabel = new JLabel(columnNames.get(i));
                JTextField jTextField = new JTextField(10);
                jPanel.add(jLabel);
                jPanel.add(jTextField);
                jp5.add(jPanel);
            }
            setSize(x++,y++);

        }
        if(e.getActionCommand().equals("隐藏侧边框")){
            RIGHT.setVisible(false);
        }
        if(e.getActionCommand().equals("添加")){
            List<String> textFieldList = new ArrayList<>();
            int count = jp5.getComponentCount();
            for(int i = 0 ;i < count;i++){
                Object obj = jp5.getComponent(i);
                Object objjj = ((JPanel)obj).getComponent(1);
                if(objjj instanceof JTextField){
                     textFieldList.add(((JTextField)objjj).getText().toString());
                }
            }
            String sql = "insert into "+show_combobox.getSelectedItem().toString()+" values(";
            for(int i =0 ;i<textFieldList.size();i++){
                if(i == textFieldList.size()-1){
                    sql = sql + "'"+textFieldList.get(i)+"'";
                }else {
                    sql = sql + "'" + textFieldList.get(i) + "'" + ",";
                }
            }
            sql = sql + ")";
            try {
                if(DBUtils.Update(ip_textfield.getText(),
                        database_textfield.getText(),
                        username_textfield.getText(),
                        password_textfield.getText(),
                        show_combobox.getSelectedItem().toString(),
                        sql)){
                    display.append("\n=========================================\n插入成功\n=========================================\n");
                }
                else{
                    display.append("\n=========================================\n插入失败\n=========================================\n");
                }
            } catch (SQLException e1) {
                display.append("\n============EXCEPTION=========================\n"+e1.toString()+"\n============EXCEPTION=========================\n");

            }
            /**插入数据*/
        }
        if (e.getActionCommand().equals("删除数据")){
            RIGHT.setVisible(true);
            sc.setVisible(true);
            cx.setVisible(false);
            tj.setVisible(false);
            gx.setVisible(false);
            jp5.removeAll();
            tiaojian = new JLabel("删除条件");
            tiaojian_textfield = new JTextArea();
            tiaojian_textfield.setLineWrap(true);
            jp5.add(tiaojian);
            jp5.add(tiaojian_textfield);

        }
        if(e.getActionCommand().equals("删除")){
            String sql;
            sql = "delete from "+show_combobox.getSelectedItem().toString()+" where "+tiaojian_textfield.getText();
            try {
                if(DBUtils.Update(ip_textfield.getText(),
                        database_textfield.getText(),
                        username_textfield.getText(),
                        password_textfield.getText(),
                        show_combobox.getSelectedItem().toString(),
                        sql)){
                    display.append("\n=========================================\n删除成功\n=========================================\n");
                }
                else{
                    display.append("\n=========================================\n删除失败,未找到数据或其他错误\n=========================================\n");
                }
            } catch (SQLException e1) {
                display.append("\n============EXCEPTION=========================\n"+e1.toString()+"\n============EXCEPTION=========================\n");

            }
        }
        if(e.getActionCommand().equals("更新数据")){

            RIGHT.setVisible(true);
            sc.setVisible(false);
            cx.setVisible(false);
            tj.setVisible(false);
            gx.setVisible(true);
            jp5.removeAll();
            JLabel gx_content = new JLabel("更新内容");
            gx_content_field = new JTextField(20);
            JLabel gx_condition = new JLabel( "更新条件");
            gx_condition_area = new JTextArea(50,20);
            gx_condition_area.setLineWrap(true);
            jp5.add(gx_content);
            jp5.add(gx_content_field);
            jp5.add(gx_condition);
            jp5.add(gx_condition_area);
        }
        if(e.getActionCommand().equals("更新")){
            String sql = "update "+show_combobox.getSelectedItem().toString()+" set "+gx_content_field.getText()+" where "+gx_condition_area.getText();
            try {
                if(DBUtils.Update(ip_textfield.getText(),
                        database_textfield.getText(),
                        username_textfield.getText(),
                        password_textfield.getText(),
                        show_combobox.getSelectedItem().toString(),
                        sql)){
                    display.append("\n=========================================\n更新成功\n=========================================\n");
                }
                else{
                    display.append("\n=========================================\n更新失败,未找到数据或其他错误\n=========================================\n");
                }
            } catch (SQLException e1) {
                display.append("\n============EXCEPTION=========================\n"+e1.toString()+"\n============EXCEPTION=========================\n");

            }
        }
    }
    public void showdata(String sql){
        try {
            int linenumbers = Integer.parseInt(linenumbers_textfield.getText());
            java.util.List<String[]> list = null;
            if(linenumbers >= 0) {
                list = DBUtils.showTablesData(ip_textfield.getText(),
                        database_textfield.getText(),
                        username_textfield.getText(),
                        password_textfield.getText(),
                        show_combobox.getSelectedItem().toString(),
                        linenumbers,sql);
            }
            else{
                list = DBUtils.showTablesData(ip_textfield.getText(),
                        database_textfield.getText(),
                        username_textfield.getText(),
                        password_textfield.getText(),
                        show_combobox.getSelectedItem().toString(),
                        -1,sql);
            }
            display.append("\n=========================================\n");
            for(String[] s : list){
                for(int i = 0;i< s.length;i++){
                    display.append(s[i]+"      ");
                }
                display.append("\n");
            }
        } catch (Exception e1) {
            display.append("\n============EXCEPTION=========================\n"+e1.toString()+"\n============EXCEPTION=========================\n");
        }

    }
}
