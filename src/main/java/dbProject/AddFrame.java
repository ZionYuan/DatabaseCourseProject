package dbProject;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yuanzi on 2017/6/17.
 */
public class AddFrame extends JFrame {
    public AddFrame() throws HeadlessException {
        setTitle("增加数据");
        setSize(400,200);
        setVisible(true);
    }

    public static void main(String[] args) {
        AddFrame addFrame = new AddFrame();
    }
}
