import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;
import java.lang.Thread;
import java.lang.Runnable;
public class Dij_Main
{
    public static void main(String[] args) throws Exception
    {
        //建立
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,1300,800);
        frame.setBackground(Color.WHITE);//背景色
        frame.setTitle("单源最短路Dijkstra算法动态过程演示系统");
        frame.setLayout(null);
        //首先将演示加入到
        Graph temp=new Graph();
        temp.setBounds(50, 50, 800, 800);
        temp.readIn();
        frame.add(temp);
        temp.play();
        //自动 - 手动  切换
        JRadioButton wayAuto = new JRadioButton("Auto",true);
        JRadioButton wayManual = new JRadioButton("Manual");
        ButtonGroup wayGroup = new ButtonGroup();
        wayGroup.add(wayAuto);
        wayGroup.add(wayManual);
//-----------------自动--------------------
        JPanel panelAuto = new JPanel();
        panelAuto.setBounds(1000,0,50,30);
        frame.add(panelAuto);
        wayAuto.setEnabled(true);
        panelAuto.add(wayAuto);

        JPanel contentPane1=new JPanel();
        contentPane1.setBounds(1000,40,200,100);
        contentPane1.setBackground(Color.WHITE);
        frame.add(contentPane1);

        JPanel panelStep = new JPanel();
        panelStep.setBackground(Color.WHITE);
        contentPane1.add(panelStep);
        JLabel label1=new JLabel("步进间隔/s:");
        NumberInputer textField1 = new NumberInputer();
        textField1.setColumns(10);
        panelStep.add(label1);
        panelStep.add(textField1);
//-----------------手动--------------------
        JPanel panelManual = new JPanel();
        panelManual.setBounds(1000,160,60,30);
        frame.add(panelManual);
        wayManual.setEnabled(true);
        panelManual.add(wayManual);

        JPanel contentPane2=new JPanel();
        contentPane2.setBounds(1010,200,70,40);
        frame.add(contentPane2);

        //下一步 按钮
        JButton next = new JButton("next");
        next.addActionListener(e -> {
            temp.threadFlag = true;
        });
        contentPane2.add(next);

        //开始按钮
        JButton start = new JButton("Start");
        start.setBounds(1000,300,100,30);
        start.addActionListener(e -> {
            if (wayAuto.isSelected()){
                String setStep=textField1.getText().toString();
                temp.step = Double.parseDouble(setStep);//设置步进间隔；
                temp.automaticFlag = true;
                temp.threadFlag = true;
                temp.stopFlag = false;
                System.out.println("stopFlag:"+temp.stopFlag);
            }else {
                temp.automaticFlag = false;
                temp.threadFlag = true;
                temp.stopFlag = false;
            }

        });
        frame.add(start);

        //暂停按钮
        JButton stop = new JButton("Stop");
        stop.setBounds(1000,400,100,30);
        stop.addActionListener(e -> {
            temp.stopFlag = true;
        });
        frame.add(stop);


        //reset按钮
        JButton reset = new JButton("Reset");
        reset.setBounds(1000,500,100,30);
        reset.addActionListener(e -> {
            temp.threadFlag = false;
            temp.step = 0.1;
            temp.automaticFlag = true;//
            temp.head=new int [1005];
            temp.edge=new int [5000][3];
            temp.lines= new int [10000][5];
            temp.dis=new int [1006];
            temp.got=new int [5000];
            temp.mark=new int [1006];
            temp.countOfEdge =0;
            temp.change=0;
            for(int i=0;i<1000;i++) {
                temp.head[i]=-1;
                temp.dis[i]=temp.inf;
                temp.got[i]=temp.mark[i]=0;
            }
            temp.marks=1;
            try {
                temp.readIn();
            }catch (Exception e1){}
            temp.play();
        });
        frame.add(reset);
        frame.setVisible(true);
    }
}