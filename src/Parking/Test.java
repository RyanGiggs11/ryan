
package Parking;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.JOptionPane;
class Car          
{
public	String id;
        int number;
        Date arrive;
        Date change;
        Date depart;
        String design;
        int  type;
        Car(String id,int num,String disg,int big)
        {
                this.id=id;
                this.number=num;
                this.arrive=new Date();
                this.design=disg;
                this.type=big;
                this.change=null;
        }
        Car(){	};
public void apark(String s)
{
        this.design=s;
}
public void dpark()
        {
                this.depart=new Date();
        }
}
class Equeue                 
{
        LinkedList mylist=new LinkedList();
public void enqueue(Car car)  //入列
{
        mylist.add(car);
}
public Car dequeue()          //出列
{
        if(mylist.size()!=0) return (Car)mylist.removeFirst();
        else return null;
}
public boolean isempty()  //是否为空
{
        if(mylist.size()==0) return false;
        else return true;
}
}
class Sstack  //堆栈定义部分
{
        LinkedList mylist=new LinkedList();
public void push(Car car)//压栈
{
        mylist.add(car);
}
public Car pop()  //弹栈
{
   Car temp=new Car();
   if(mylist.size()!=0) {temp=(Car)mylist.getLast(); mylist.removeLast();return (Car)temp;}
   else return null;
}
public boolean empty()//是否为空
{
        if(mylist.size()==0) return true;
        else return false;
}
}
class Win extends JFrame implements ActionListener         //win类定义部分
{
    int stationlen;
    Equeue  queue1=new Equeue();
    Equeue  queue2=new Equeue();
    Sstack stack1=new Sstack();
    Sstack stack2=new Sstack();
    MenuBar menubar;
    Menu menu1,menu2,menu3,menu4 ;
    MenuItem item1,item2,item3,item4,item5,item6,item7,item8;
    int b[],c[];
    int count1=0;
    int count2=0;
    int flag=0;
    int price1,price2;
    JTable table;
    Object a[][];
    Object name[]={"车牌号","车位号","进站时间","出站时间","备注","车型号"};
    Object addname[]={"车牌号","备注"};
    JButton button1,button2,button3,button4,button5,button6;
    JTextField  text2,text1;
    JDialog ss;
    Box box1,box2,box3;
    Stack stack3;
    Choice choice;
    Win(String s )              
    {
               super(s);
               stationlen=5;
               price1=2;
               price2=1;
               b=new int[stationlen];
        for(int i=0;i<stationlen;i++)
            b[i]=0;
            choice=new Choice();
            choice.add("小汽车");
            choice.add("中型客车");
            choice.add("货车");
            menubar=new MenuBar();
            menu1=new Menu("进/出站");
            menu2=new Menu("查询");
            menu3=new Menu("车辆列表");
            menu4=new Menu("帮助");
            item1=new MenuItem("进站");
            item2=new MenuItem("出站");
            item2.enable(false);
            item3=new MenuItem("退出");
            item4=new MenuItem("查找汽车") ;
            item5=new MenuItem("查询空车位");
            item6=new MenuItem("打印");
            item7=new MenuItem("帮助说明");
            item8=new MenuItem("查询汽车总数量");
            item1.addActionListener(this);
            item2.addActionListener(this);
            item3.addActionListener(this);
            item4.addActionListener(this);
            item5.addActionListener(this);
            item6.addActionListener(this);
            item7.addActionListener(this);
            item8.addActionListener(this);
            menu1.add(item1);
            menu1.add(item2);
            menu1.addSeparator();
            menu1.add(item3);
            menu2.add(item4);
            menu2.add(item5);
            menu2.add(item8);
            menu3.add(item6);
            menu4.add(item7);
            menubar.add(menu1);
            menubar.add(menu2);
            menubar.add(menu3);
            menubar.add(menu4);
            setMenuBar(menubar);
            button1=new JButton("确定");
            button1.addActionListener(this);
            button2=new JButton("确定");
            button2.addActionListener(this);
            button3=new JButton("确定");
            button3.addActionListener(this);
            button4=new JButton("确定");
            button4.addActionListener(this);
            button5=new JButton("确定");
            button5.addActionListener(this);
            button6=new JButton("取消");
            button6.addActionListener(this);
            getContentPane().add(new JScrollPane(table),BorderLayout.CENTER);
            setLocation(120,60);
            setSize(800,600);
            setResizable(false);
            setVisible(true);
            validate();
    }
public void  enableitem2()
{
   int k=0;
   for(;k<stationlen;k++)
                 {if(b[k]==1) break;}
   if(k==stationlen)   item2.enable(false);
   else item2.enable(true);
}
public int checklink(int b[],int big,int len)   //判断是否有足够连续的车位
{
        int i,j,temp=0;
        for(i=0;i<len-big+1;i++)
         {  temp=0;
            for(j=0;j<big;j++)
            {if(b[i+j]==0) ++temp;}
            if (temp==big)  return i;
         }
         return len;
}
public void  menuable(boolean h)      //设定菜单栏属性
{
        menu1.enable(h);
        menu2.enable(h);
        menu3.enable(h);
        menu4.enable(h);
}
public void removetable(boolean h)  //设置表格在窗体中显示
{
        if(h)
          {
            tableset(1,3,addname);
                  getContentPane().add(new JScrollPane(table),BorderLayout.CENTER);
                  getContentPane().remove(table);
          }
        else
          {
                  tableset(1,3,addname);
                  getContentPane().remove(table);
            getContentPane().add(new JScrollPane(table),BorderLayout.CENTER);
          }

}
public void tableset(int i,int j,Object name1[] )   //设置表格
{
        a=new Object[i][j];
                table=new JTable(a, name1);
                getContentPane().removeAll();
            getContentPane().add(new JScrollPane(table),BorderLayout.CENTER);
            table.repaint();
            validate();
}
public Car checksame(String word )          //判断停车场或者便车道是否有和最近添加相同的车牌号
{
    Car temp=new Car(),htemp=new Car();
    htemp=null;
    char cword[]=word.toCharArray();
    while(!stack1.empty())
         {	temp=stack1.pop();
                 stack2.push(temp);
                 if(word.equals(temp.id))
                    htemp=temp;
         }
         while(!stack2.empty())
                     { temp=stack2.pop();
                       stack1.push(temp);
                     }
          while(queue1.isempty())
                 {
                   temp=queue1.dequeue();
                   queue2.enqueue(temp) ;
                   if(word.equals(temp.id))
                            htemp=temp;
                 }
                 while(queue2.isempty())
                                 { temp=queue2.dequeue();
                                   queue1.enqueue(temp);
                                 }
         return htemp;
}
public boolean checkformat(String word)     //判断输入字符是否为数字
{
         char cword[]=word.toCharArray();
         for(int i=0;i<cword.length;i++)
                 if(!(Character.isDigit(cword[i]))  )
                    return false;
           return true;
}
public void Dialog_ss_action()
{
    ss.addWindowListener(new WindowAdapter()
         {public void windowClosing(WindowEvent e)
         {menuable(true);}});
}
public boolean tranfer(Car temp ,Sstack stack1,int n,int b[])  //便车道车进入 停车场
{  int t=0;
   if(checklink(b,temp.type,n)!=n)
                 {
                         Date h=new Date();
                         temp.change=new Date();
                         temp.number=checklink(b,temp.type,n);
                         temp.design=h+"车号"+temp.id+"进入停车场"+String.valueOf(temp.number)+"位";
                         for(t=temp.number;t<temp.number+temp.type;t++)
                             b[t]=1;
                         stack1.push(temp);
                         count2--;
                         count1++;
                         enableitem2();
            JOptionPane.showMessageDialog(this, temp.design,"告对话框 ",JOptionPane.WARNING_MESSAGE);
             return true;
             }
         return false;
}

public void   tableprint(Equeue queue1,Equeue queue2,Sstack stakc1,Sstack stack2) //打印停车场
{
        Car temp=new Car();
                  int  i=0,j=6;
                  tableset(count1+count2,j,name);
             table.setVisible(false);
                 while(!stack1.empty())
                    {
                                    temp=(Car)stack1.pop();
                                 a[i][0]=temp.id;
                                 a[i][1]=String.valueOf(temp.number)  ;
                                 a[i][2]=temp.arrive;
                                 a[i][3]=temp.depart;
                                 a[i][4]=temp.design;
                                 a[i][5]=String.valueOf(temp.type);
                                  stack2.push(temp);
                                  i++;
                         }
                 while(!stack2.empty())
                 {
                         temp=(Car)stack2.pop();
                         stack1.push(temp);
                 }
                 while(queue1.isempty())
                 {       temp=queue1.dequeue();
                                 a[i][0]=temp.id;
                                 a[i][1]=String.valueOf(temp.number);
                                 a[i][2]=temp.arrive;
                                 a[i][3]=temp.depart;
                                 a[i][4]=temp.design;
                                 a[i][5]=String.valueOf(temp.type);
                                  queue2.enqueue(temp);
                                  i++;
                 }
                 while(queue2.isempty())
                 {   temp=queue2.dequeue();
                         queue1.enqueue(temp);
                 }

}
public void actionPerformed(ActionEvent e)         //动作监听函数
{
    if(e.getSource()==item1)   //进站
    {
        menuable(false);
        removetable(true);
            ss=new JDialog();
            ss.setTitle("进站");
            ss.setModal(true);
            text1=new JTextField(16);
            text1.setText("无");
            text2=new JTextField(16);
            text2.setText("无");
            box1=Box.createVerticalBox();
            box1.add(new JLabel("请输入ID"));
            box1.add(Box.createVerticalStrut(8));
            box1.add(new JLabel("请输入备注"));
            box1.add(Box.createVerticalStrut(8));
            box1.add(new JLabel("请选择类型"));
            box2=Box.createVerticalBox();
            box2.add(text1);
            box2.add(Box.createVerticalStrut(8));
            box2.add(text2);
            box2.add(Box.createVerticalStrut(8));
            box2.add(choice);
            box3=Box.createHorizontalBox();
            box3.add(box1);
            box3.add(Box.createHorizontalStrut(10));
            box3.add(box2);

                 ss.getContentPane().setLayout(new FlowLayout());
                 ss.setBounds(320,150,300,180);
                 Dialog_ss_action();
                 ss.getContentPane().add(box3);


            //ss.getContentPane().setResizable(false);
            ss.getContentPane().add(button1);
            ss.getContentPane().add(button6);
            ss.setVisible(true);
            ss.validate();
    }

        if(e.getSource()==item2) //出站
        {
                menuable(false);
                removetable(true);
            ss=new JDialog();
            ss.setTitle("出站");
            ss.setModal(true);
            text1=new JTextField(16);
             text1.setText("无");
            ss.getContentPane().setLayout(new FlowLayout());
                 ss.setBounds(320,150,200,120);
                 ss.setResizable(false);
                 Dialog_ss_action();
        ss.getContentPane().add(new JLabel("请输入ID"));
        ss.getContentPane().add(text1);
        ss.getContentPane().add(button2);
        ss.getContentPane().add(button6);
        ss.setVisible(true);
            ss.validate();
     }
        if(e.getSource()==item3)  //退出
        {System.exit(0);	}
        if(e.getSource()==item4)//查找汽车信息
        {
            menuable(false);
            removetable(true);
        ss=new JDialog();
            ss.setTitle("查找车辆");
            ss.setModal(true);
           ss.getContentPane().setLayout(new FlowLayout());
                 ss.setBounds(320,225,300,120);
                 ss.setResizable(false);
                 Dialog_ss_action();
        ss.getContentPane().add(new JLabel("请输入ID"));
        text1=new JTextField(16);
             text1.setText("无");
        ss.getContentPane().add(text1);
        ss.getContentPane().add(button3);
        ss.getContentPane().add(button6);
        ss.setVisible(true);
            ss.validate();
        }
         if(e.getSource()==item5) //查询空车位
        {
                removetable(true);
                String s="";               
                if(checklink(b,1,stationlen)==stationlen)
                  JOptionPane.showMessageDialog(this,"停车场以满,请进入便车道","提示对话框 ",JOptionPane.WARNING_MESSAGE);
            else
             {	for(int i=0;i<stationlen;i++)  //没有空车位怎提示
                      {if(b[i]==0)
                      	 s=s+String.valueOf(i)+" "; 	
                       if(s.length()>=20)
                          {
                          	s=s+".....等";
                          	break;
                          }
                      }
                JOptionPane.showMessageDialog(this,"空车位有:"+s+"号车位","提示对话框 ",JOptionPane.WARNING_MESSAGE);
             }
        }
         if(e.getSource()==item6)   //打印停车场汽车列表包括便车道
         {
                  removetable(false);
                  tableprint(queue1,queue2,stack1,stack2);
                  tableprint(queue1,queue2,stack1,stack2);
                 table.setVisible(true);
                 table.repaint();
                 table.enable(false);
         }
         
         
         if(e.getSource()==item7)
         {

            removetable(true);
            ss=new JDialog();
            ss.setTitle("帮助");
            ss.setModal(true);
            ss.getContentPane().setLayout(new FlowLayout());
                 ss.setBounds(320,225,400,120);
             ss.setResizable(false);
                 Dialog_ss_action();
        ss.getContentPane().add(new JLabel("本系统可以实现停车场汽车出站,进站管理.谢谢使用!"));
        ss.setVisible(true);
            ss.validate();
         }
         if(e.getSource()==item8)
         {
            removetable(true);
            JOptionPane.showMessageDialog(this,"停车场数量:"+String.valueOf(count1)+"  便车道数量:"+String.valueOf(count2),"提示对话框 ",JOptionPane.WARNING_MESSAGE);
         }
         if(e.getSource()==button1)        //处理入站
        {
                String s=text1.getText().toString();
            char c[]=s.toCharArray();
                if(c.length==0)		      //判断车牌号是否为空并给予提示
                    JOptionPane.showMessageDialog(this,"车牌号不能为空","警告对话框 ",JOptionPane.WARNING_MESSAGE);
                else
                 if ( checksame(s)!=null )       //判断是否有重号并给予提示如果没有怎进入下面
                      JOptionPane.showMessageDialog(this,"车牌号重复","提示",JOptionPane.WARNING_MESSAGE);
             else
                   if(!checkformat(s))
                       JOptionPane.showMessageDialog(this,"输入非法字符","提示",JOptionPane.WARNING_MESSAGE);
                   else
                   {
                      int j=choice.getSelectedIndex()+1;
                      int i=checklink(b,j,stationlen) ;
              if(i==stationlen)    //停车场满汽车进入便车道
                {
                   int sign=1000;    //便车道同意标志
                   Car temp=new Car(text1.getText().toString(),sign,text2.getText().toString(),j);
                                              queue1.enqueue(temp);
                         count2++;
                         JOptionPane.showMessageDialog(this,"没有足够的空车位汽车进入便车位号:1000车号","提示",JOptionPane.WARNING_MESSAGE);
                 }
              else                   //进入停车场
                 {
                    Car temp=new Car(text1.getText().toString(),i,text2.getText().toString(),j);
                          temp.change=new Date();
                          stack1.push(temp);
                          count1++;
                    JOptionPane.showMessageDialog(this,"成功进入"+i+"车位","提示",JOptionPane.WARNING_MESSAGE);
                          for(int k=i;k<i+j;k++)     b[k]=1;
                  }

                   }
                  enableitem2();
                  ss.setVisible(false);
                  menuable(true);
         }
         if(e.getSource()==button2)         //处理出站
         {
            removetable(false);
            Car temp,htemp=new Car();
                 if(text1.getText().length()==0)  //判断是否输入车牌号
                     JOptionPane.showMessageDialog(this,"车牌号不能为空","警告 ",JOptionPane.WARNING_MESSAGE);
                 else
                 {{
                  if(flag==0)    //先检测是否在停车场
                    { while(!stack1.empty())
                             {	temp=(Car)stack1.pop();
                                     if(text1.getText().equals(temp.id))   //如果找到怎从停车场中删除
                                         {   int t=0 ;
                                                 count1--;
                                             for(t=temp.number;t<temp.number+temp.type;t++)   b[t]=0;
                                         htemp=temp;   flag=1;
                                         while(queue1.isempty())
                                           {
                                               temp=queue1.dequeue();
                                              if(!tranfer(temp,stack1,stationlen,b))
                                               queue2.enqueue(temp);
                                           }
                                          while(queue2.isempty())
                                          {
                                             temp=queue2.dequeue();
                                             queue1.enqueue(temp);
                                          }
                                         }
                                         else stack2.push(temp);
                             }
                      while(!stack2.empty())
                                 {temp=(Car)stack2.pop();stack1.push(temp);	}
                    }
                   if(flag!=1)     //如果不在停车场怎检测便车道
                            {
                           while(queue1.isempty())
                                 {	temp=queue1.dequeue();
                                          if(text1.getText().equals(temp.id))
                                                  { count2--;flag=2;htemp=temp;break;}
                                          else
                                                   queue2.enqueue(temp);
                                  }
                           while(queue2.isempty())
                                 {  temp=queue2.dequeue();
                                         queue1.enqueue(temp) ;
                                 }
                         }
                    }
                    if(flag==0)   //如果在停车场和便车道均没有发现怎输出不存在
                                   JOptionPane.showMessageDialog(this,"该车不在车站","警告对话框 ",JOptionPane.WARNING_MESSAGE);
                    else        //找到该汽车输入 相关信息
                          {
                                  int i=1,j=6;
                                  flag=0;
                                  htemp.dpark();
                                  tableset(i,j,name);
                                  table.setVisible(true);
                                  a[0][0]=htemp.id;
                                  a[0][1]=String.valueOf(htemp.number) ;
                                  a[0][2]=htemp.arrive;
                                  a[0][3]=htemp.depart;
                                  a[0][4]=htemp.design;
                                  a[0][5]=String.valueOf(htemp.type);
                                  table.repaint();
                                  table.enable(false);
                                  
                                  
                   }	}
              enableitem2();
                  menuable(true);
                  ss.setVisible(false);
    }
         if(e.getSource()==button3)  //处理查找汽车信息
        {
            removetable(false);
            Car htemp=new Car();
            htemp=checksame(text1.getText());
            if(htemp==null)
                JOptionPane.showMessageDialog(this,"很遗憾,该车不在停车场","警告对话框 ",JOptionPane.WARNING_MESSAGE);
            else
            {  	    tableset(1,6,name);table.setVisible(true);table.enable(false);
                        a[0][0]=htemp.id;a[0][1]=String.valueOf(htemp.number) ;a[0][2]=htemp.arrive;
                                  a[0][3]=htemp.depart;a[0][4]=htemp.design;a[0][5]=String.valueOf(htemp.type);
                              table.repaint();
        }
      menuable(true);
           ss.setVisible(false);
        }
         if(e.getSource()==button4)  //处理设定停车场大小
         {
                 int temp=stationlen;
             int htemp=stationlen;
                 if(!checkformat(text2.getText()))
                       JOptionPane.showMessageDialog(this,"输入非法,保持初始大小"+String.valueOf(stationlen),"提示",JOptionPane.WARNING_MESSAGE);
                else
                       temp=Integer.parseInt(text2.getText());
             if(temp<stationlen )
                    JOptionPane.showMessageDialog(this,"设置小于当前值,保持初始大小"+String.valueOf(stationlen),"提示",JOptionPane.WARNING_MESSAGE);
             else   {
                          stationlen=temp;
                      JOptionPane.showMessageDialog(this,"更新成功,当前大小为:"+String.valueOf(stationlen),"提示",JOptionPane.WARNING_MESSAGE);
                    }
             b=new int[stationlen];
             for(int te=0;te<stationlen;te++)
                  if (te<htemp) b[te]=c[te];
                  else b[te]=0;
       menuable(true);
            ss.setVisible(false);
         }
         if(e.getSource()==button5)  //处理价格设定
         {
           if(!checkformat(text2.getText())||!checkformat(text1.getText()))
               JOptionPane.showMessageDialog(this,"输入非法,保持初始大小","提示",JOptionPane.WARNING_MESSAGE);
           else
             {
                     int temp1=Integer.parseInt(text1.getText());
                     int temp2=Integer.parseInt(text2.getText());
                     if(temp1>20||temp1<=0||temp2<=0||temp2>20||temp1<temp2)
                        JOptionPane.showMessageDialog(this,"设定不合法可能:便车道价格大于停车场,单价大于20或者为零.(保持初始大小)","警告",JOptionPane.WARNING_MESSAGE);
                     else
                        {price1=temp1;
                         price2=temp2;
                         JOptionPane.showMessageDialog(this,"更新成功" ,"提示",JOptionPane.WARNING_MESSAGE);
                        }
             }
            menuable(true);
            ss.setVisible(false);
         }
         if(e.getSource()==button6)  //取消函数
         {
           menuable(true);
           ss.setVisible(false);
         }

}
}
public class Test        //主主函数
{
        public static void main(String args[])
        {
                   Win win=new Win("停车场" );
        }
}

