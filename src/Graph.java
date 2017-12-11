import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.lang.Thread;
public class Graph extends JPanel
{
    int [] head;//链式前向星存图
    int [][] edge;  //存放有向边
    int countOfEdge;
    int numOfPoint; //顶点数
    int numOfEdge;  //边数
    int inf=0x3f3f3f3f; //无穷大
    int start,tt;      //源汇点
    int change;     //开关量
    int []mark;     //最短路的标记
    int []dis;      //距离
    int []got;      //是否需要闪烁标记（正在被更新）
    Points [] point;//点类
    int [][] lines;
    int marks;
    Thread thread;
    boolean threadFlag;
    boolean automaticFlag;
    double step;
    boolean stopFlag;

    public Graph(){                                                             //初始化构造函数
        stopFlag = false;
        marks=1;
        threadFlag = false;
        step = 0.1;
        automaticFlag = true;//
        head=new int [1005];
        edge=new int [5000][3];
        lines= new int [10000][5];
        dis=new int [1006];
        got=new int [5000];
        mark=new int [1006];
        countOfEdge =0;
        change=0;
        for(int i=0;i<1000;i++) {
            head[i]=-1;
            dis[i]=inf;
            got[i]=mark[i]=0;
        }
    }
    //添加一条有向边
    public void addEdge(int from,int to,int w){
        edge[countOfEdge][0]=to;            //以from为起点 to为终点
        edge[countOfEdge][1]=head[from];    //head[from]存储以from为起点的上一条边的位置
        head[from]= countOfEdge;
        edge[countOfEdge][2] = w;
        countOfEdge++;
    }

    //延时重画函数
    void magic0(){
        long t = (long)(step*1000);
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.repaint();
    }

    //闪烁控制函数
    void magic() {
        int numOfChange=2;                                                              //变换次数
        while(numOfChange!=0) {
            change=1;//灯开
            magic0();
            change=0;//灯灭
            magic0();
            numOfChange--;
        }
    }

    //读入图函数
    public void readIn()throws Exception{
        java.io.File file= new java.io.File("D:\\java_files\\Dijkstra_demo\\scores.txt");  //读入数据
        Scanner input =new Scanner(file);
        while(input.hasNext()){
            numOfPoint =input.nextInt();
            numOfEdge =input.nextInt();
            int from,to,w;
            for(int i = 0; i< numOfEdge; i++) {
                from=input.nextInt();
                to=input.nextInt();
                w=input.nextInt();
                addEdge(from,to,w);
            }
            start=input.nextInt();//源点
            tt=input.nextInt();//汇点
            dis[start]=0;
        }
        input.close();
        //读入坐标点
        java.io.File file2 = new java.io.File("D:\\java_files\\Dijkstra_demo\\points.txt");
        Scanner input2 =new Scanner(file2);
        point =new Points [numOfPoint+1];//从 1 开始
        for(int i = 1; i<= numOfPoint; i++) {
            point[i]=new Points(input2.nextInt(),input2.nextInt());
        }
        input2.close();
    }

    //Dijkstra算法
    public void play() {
        this.repaint();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println("thread"+0);
                    while(marks==1){
                        System.out.println("marks"+0);
                        if (threadFlag) {
                            int mins = inf;//初始化无穷大
                            marks = 0;
                            int cur = 0;
                            for (int i = 1; i <= numOfPoint; i++) {//找出离初始点最短路径的点
                                if (mark[i] == 0 && dis[i] < mins) {
                                    mins = dis[i];
                                    cur = i;
                                    marks = 1;
                                }
                                while (stopFlag){//暂停
//                                    for(int k=0;k<10;k++){}
                                    System.out.println("stopFlag1");
                                }
                            }
                            got[cur] = 1; //点需要闪
                            magic();
                            got[cur] = 0;//关闭该点，之后就不再闪

                            mark[cur] = 1;//该点已被访问到
                            System.out.print("head:" + head[cur]);
                            for (int j = head[cur]; j != -1; j = edge[j][1]) {
                                while (stopFlag){//暂停
//                                    for(int k=0;k<10;k++){}
                                    System.out.println("stopFlag2");
                                }
                                int to = edge[j][0];
                                if (mark[to] == 0 && dis[to] > dis[cur] + edge[j][2]) {
                                    dis[to] = dis[cur] + edge[j][2];
                                    lines[j][0] = 1;                                              //该线已变色
                                    lines[j][1] = 1;                                              //该线需要闪
                                    magic();
                                    lines[j][1] = 0;                                              //无需再闪
                                }
                            }
                            System.out.print("\n");
                        }
                        if (!automaticFlag){
                            threadFlag = false;
                        }

                    }
                }
            }
        });
        thread.start();
    }
    //画图函数
    protected void paintComponent( Graphics g){

        super.paintComponent(g);                                                //清屏
        //设置点的大小
        int diameter = 40;
        int pointRadius = diameter/2;
        g.setColor(Color.green);
        Font font = new Font("Arial", Font.BOLD, 25);                           //字体大小
        g.setFont(font);
        for(int i = 1; i<= numOfPoint; i++)
        {
            if(got[i]==1)                                                        //该点在闪
            {
                if(change==1)                                                    //灯开
                    g.setColor(Color.red);
                else  g.setColor(Color.GREEN);                                    //灯灭
            }
            else
            {
                if(mark[i]==1)                                                   //该点已变
                    g.setColor(Color.red);
                else g.setColor(Color.GREEN);
            }



            g.fillOval(point[i].x, point[i].y, diameter, diameter);
            String ts;
            if(dis[i]==inf)
                ts="+∞";
            else
                ts=new String().valueOf(dis[i]);
            g.drawString(ts,point[i].x,point[i].y);
            String pointName = ""+i;
            g.drawString(pointName,point[i].x+40,point[i].y+40);
        }
        g.setColor(Color.GRAY);
        for(int i = 1; i<= numOfPoint; i++)
        {
            for(int j=head[i];j!=-1;j=edge[j][1])
            {
                if(lines[j][1]==1)                                                      //需要闪烁
                {
                    if(change==1)
                        g.setColor(Color.BLUE);
                    else
                        g.setColor(Color.GRAY);

                }
                else
                {
                    if(lines[j][0]==1)                                                   //已经变色
                        g.setColor(Color.BLUE);
                    else   g.setColor(Color.GRAY);
                }
                Graphics2D g2= (Graphics2D)g;
                drawAL(point[i].x+pointRadius,point[i].y+pointRadius, point[edge[j][0]].x+pointRadius, point[edge[j][0]].y+pointRadius,g2);    //划边
                int mx=(point[i].x+pointRadius+point[edge[j][0]].x+pointRadius)/2;
                int my=(point[i].y+pointRadius+point[edge[j][0]].y+pointRadius)/2;
                g.drawString(new String().valueOf(edge[j][2]), mx,my);
            }
        }
    }
    public static void drawAL(int sx, int sy, int ex, int ey, Graphics2D g2)
    {
        double H = 20;                                                                          // 箭头高度
        double L = 6;                                                                           // 底边的一半
        int x3,y3,x4,y4;
        double awrad = Math.atan(L / H);                                                        // 箭头角度
        double arraow_len = Math.sqrt(L * L + H * H);                                           // 箭头的长度
        double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
        double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
        double x_3 = ex - arrXY_1[0];                                                           // (x3,y3)是第一端点
        double y_3 = ey - arrXY_1[1];
        double x_4 = ex - arrXY_2[0];                                                           // (x4,y4)是第二端点
        double y_4 = ey - arrXY_2[1];
        Double X3 = new Double(x_3);
        x3 = X3.intValue();
        Double Y3 = new Double(y_3);
        y3 = Y3.intValue();
        Double X4 = new Double(x_4);
        x4 = X4.intValue();
        Double Y4 = new Double(y_4);
        y4 = Y4.intValue();
        g2.drawLine(sx, sy, ex, ey);                                                             // 画线
        GeneralPath triangle = new GeneralPath();
        triangle.moveTo(ex, ey);
        triangle.lineTo(x3, y3);
        triangle.lineTo(x4, y4);
        triangle.closePath();
        g2.fill(triangle);                                                                      //实心箭头
        //g2.draw(triangle);                                                                    //非实心箭头
    }
    public static double[] rotateVec(int px, int py, double ang,                // 计算箭头的左右俩的点
                                     boolean isChLen, double newLen)
    {
        double mathstr[] = new double[2];
        // 参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度
        double vx = px * Math.cos(ang) - py * Math.sin(ang);
        double vy = px * Math.sin(ang) + py * Math.cos(ang);
        if (isChLen) {
            double d = Math.sqrt(vx * vx + vy * vy);
            vx = vx / d * newLen;
            vy = vy / d * newLen;
            mathstr[0] = vx;
            mathstr[1] = vy;
        }
        return mathstr;
    }
}
class Points {
    int x,y;
    Points(int x,int y){
        this.x=x;
        this.y=y;
    }
}