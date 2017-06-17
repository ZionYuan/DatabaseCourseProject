package dbProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by yuanzi on 2017/6/8.
 */
public class MainFrame extends JFrame implements ActionListener {
    public static void main(String[] args) throws Exception {
        MainFrame mainFrame = new MainFrame("数据库管理系统");

    }

    JPanel jp1,jp2,jp12,jp3,jp4;
    JLabel ip,database,username,password,info,show;
    JTextField ip_textfield,database_textfield,username_textfield,password_textfield;
    JComboBox show_combobox;
    JButton connect,query,add,delete;
    JTextArea display;

    public MainFrame(String title) throws Exception {
        super(title);
        setSize(700,400);
        getContentPane().setLayout(
                new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)
        );
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
        query = new JButton("查看数据");
        query.addActionListener(this);
        query.setEnabled(false);

        add = new JButton("增加数据");
        add.addActionListener(this);
//        add.setEnabled(false);

        delete = new JButton("删除数据");
        delete.addActionListener(this);
        delete.setEnabled(false);


        jp4.add(query);
        jp4.add(add);
        jp4.add(delete);





        add(jp1);
        add(jp2);
        add(jp3);
        add(jp4);

        display = new JTextArea(10,10);
        display.setEditable(false);

        //设置滚动框，并保持滚动条一直在最下面.
        JScrollPane scrollPane = new JScrollPane(display);
        display.setLineWrap(true);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        display.setCaretPosition(display.getText().length());
        add(scrollPane);
        connect.addActionListener(this);
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
                        display.append("数据库连接失败！" + "\n");
                    }
                    else{
                        java.util.List<String> tables_list = DBUtils.showTables(ip_textfield.getText(),
                                                                                    database_textfield.getText(),
                                                                                    username_textfield.getText(),
                                                                                    password_textfield.getText()
                                                                                    );
                        show_combobox.removeAllItems();
                        for (int i = 0; i < tables_list.size(); i++) {
                        show_combobox.addItem(tables_list.get(i));
                            }
                        display.append("已连接数据库 "+database_textfield.getText()+"\n");
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
        if(e.getActionCommand().equals("查看数据")){
            try {
                java.util.List<String[]> list = DBUtils.showTablesData(ip_textfield.getText(),
                                                                        database_textfield.getText(),
                                                                        username_textfield.getText(),
                                                                        password_textfield.getText(),
                                                                        show_combobox.getSelectedItem().toString());
                for(String[] s : list){
                    for(int i = 0;i< s.length;i++){
                        display.append(s[i]+"      ");
                    }
                    display.append("\n"+"\n");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
        if(e.getActionCommand().equals("增加数据")){
            new AddFrame();
        }
    }
}
