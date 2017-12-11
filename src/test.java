import javax.swing.*;
import java.awt.*;
public class test  extends JFrame
{
    public static int wait=0;
    JButton stop= new JButton("STOP");                                            //暂停按钮
    JButton go= new JButton("GO");                                                //开始按钮
    JButton next = new JButton("next");
    JRadioButton wayAuto = new JRadioButton("Auto");
    JRadioButton wayManual = new JRadioButton("Manual");
    ButtonGroup wayGroup = new ButtonGroup();

    public test ()throws Exception {
        setBounds(0, 0, 1380, 800);//设定大小
        this.setBackground(Color.WHITE);                                          //背景色
        this.setTitle("单源最短路Dijkstra算法动态过程演示系统");
        this.setLayout(null);

        wayAuto.setEnabled(true);
        wayManual.setEnabled(true);

        Graph temp=new Graph();                                                      // 建图
        temp.setBounds(90, 50, 1000, 800);
        temp.readIn();
        add(temp);
        this.setVisible(true);
        wayAuto.setBounds(1005,0,30,30);
        wayManual.setBounds(1005,50,30,30);
        wayGroup.add(wayAuto);
        wayGroup.add(wayManual);

        add(wayAuto);
        add(wayManual);
        next.setBounds(1005,205,100,30);
        next.addActionListener(e -> {
            temp.threadFlag = true;
        });
        add(next);
        temp.play();                                                             //演示算法过程




    }
}