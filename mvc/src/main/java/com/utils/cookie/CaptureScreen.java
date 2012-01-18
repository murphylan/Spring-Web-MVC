package com.utils.cookie;
/**
 *
 * @author Murphy
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
public class CaptureScreen extends JFrame implements ActionListener{
    private JButton start,cancel,save;
    private JPanel c;
    private BufferedImage get;
    /** Creates a new instance of CaptureScreen */
    public CaptureScreen() {
        super("屏幕截取软件");
        initWindow();
    }
    private void initWindow(){
        start=new JButton("开始截取");
        cancel=new JButton("退出");
        save=new JButton("保存");
        save.setEnabled(false);
        save.addActionListener(this);
        start.addActionListener(this);
        cancel.addActionListener(this);
        JPanel buttonJP=new JPanel();
        c=new JPanel(new BorderLayout());
        JLabel jl=new JLabel("屏幕截取",JLabel.CENTER);
        JLabel jl1=new JLabel("Author：Murphy",JLabel.CENTER);
        jl.setFont(new Font("黑体",Font.BOLD,40));
        jl1.setFont(new Font("宋体",Font.BOLD,20));
        jl.setForeground(Color.RED);
        jl1.setForeground(Color.BLUE);
        c.add(jl,BorderLayout.CENTER);
        c.add(jl1,BorderLayout.SOUTH);
        buttonJP.add(start);
        buttonJP.add(save);
        buttonJP.add(cancel);
        this.getContentPane().add(c,BorderLayout.CENTER);
        this.getContentPane().add(buttonJP,BorderLayout.SOUTH);
        this.setSize(300,300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void updates(){
        if(get!=null){
            ImageIcon ii=new ImageIcon(get);
            JLabel jl=new JLabel(ii);
            c.removeAll();
            c.add(new JScrollPane(jl),BorderLayout.CENTER);
            SwingUtilities.updateComponentTreeUI(this);
        }
    }
    private void doStart(){
        try{
            Robot ro=new Robot();
            Toolkit tk=Toolkit.getDefaultToolkit();
            Dimension di=tk.getScreenSize();
            Rectangle rec=new Rectangle(0,0,di.width,di.height);
            BufferedImage bi=ro.createScreenCapture(rec);
            JFrame jf=new JFrame();
            jf.getContentPane().add(new Temp(jf,bi,di.width,di.height));
            jf.setUndecorated(true);
            jf.setSize(di);
            jf.setVisible(true);
            jf.setAlwaysOnTop(true);
        } catch(Exception exe){
            exe.printStackTrace();
        }
    }
    private void doSave(){
        try{
            JFileChooser jfc=new JFileChooser(".");
            jfc.addChoosableFileFilter(new JPGfilter());
            jfc.addChoosableFileFilter(new PNGfilter());
            int i=jfc.showSaveDialog(this);
            if(i==JFileChooser.APPROVE_OPTION){
                File file=jfc.getSelectedFile();
                String about="PNG";
                String ext=file.toString().toLowerCase();
                javax.swing.filechooser.FileFilter ff=jfc.getFileFilter();
                if(ff instanceof JPGfilter){
                    if(!ext.endsWith(".jpg")){
                        String ns=ext+".jpg";
                        file=new File(ns);
                        about="JPG";
                    }
                } else if(ff instanceof PNGfilter){
                    if(!ext.endsWith(".png")){
                        String ns=ext+".png";
                        file=new File(ns);
                        about="PNG";
                    }
                }
                
                if(ImageIO.write(get,about,file)){
                    JOptionPane.showMessageDialog(this,"保存成功！");
                    save.setEnabled(false);
                } else
                    JOptionPane.showMessageDialog(this,"保存失败！");
            }
        } catch(Exception exe){
            exe.printStackTrace();
        }
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==start){
            doStart();
        } else if(ae.getSource()==cancel){
            System.exit(0);
        } else if(ae.getSource()==save){
            doSave();
        }
    }
    //一个文件后缀名选择器
    private class JPGfilter extends javax.swing.filechooser.FileFilter{
        public JPGfilter(){
            
        }
        public boolean accept(File file){
            if(file.toString().toLowerCase().endsWith(".jpg")||
                    file.toString().toLowerCase().endsWith(".jpeg")||
                    file.isDirectory()){
                return true;
            } else
                return false;
        }
        public String getDescription(){
            return "*.JPG,*.JPEG(JPG,JPEG图像)";
        }
    }
    private class PNGfilter extends javax.swing.filechooser.FileFilter{
        public boolean accept(File file){
            if(file.toString().toLowerCase().endsWith(".png")||
                    file.isDirectory()){
                return true;
            } else
                return false;
        }
        public String getDescription(){
            return "*.PNG(PNG图像)";
        }
    }
    //一个暂时类，用于显示当前的屏幕图像
    private class Temp extends JPanel implements MouseListener,MouseMotionListener{
        private BufferedImage bi;
        private int width,height;
        private int startX,startY,endX,endY,tempX,tempY;
        private JFrame jf;
        private Rectangle select=new Rectangle(0,0,0,0);//表示选中的区域
        private Cursor cs;//表示一般情况下的鼠标状态
        private States current=States.DEFAULT;// 表示当前的编辑状态
        private Rectangle[] rec;//表示八个编辑点的区域
        public Temp(JFrame jf,BufferedImage bi,int width,int height){
            this.jf=jf;
            this.bi=bi;
            this.width=width;
            this.height=height;
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
            Image icon=Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("icon.png"));
            cs=Toolkit.getDefaultToolkit().createCustomCursor(icon,new Point(0,0),"icon");
            this.setCursor(cs);
            initRecs();
        }
        private void initRecs(){
            rec=new Rectangle[8];
            for(int i=0;i<rec.length;i++){
                rec[i]=new Rectangle();
            }
        }
        public void paintComponent(Graphics g){
            g.drawImage(bi,0,0,width,height,this);
            g.setColor(Color.RED);
            g.drawLine(startX,startY,endX,startY);
            g.drawLine(startX,endY,endX,endY);
            g.drawLine(startX,startY,startX,endY);
            g.drawLine(endX,startY,endX,endY);
            int x=startX<endX?startX:endX;
            int y=startY<endY?startY:endY;
            select=new Rectangle(x,y,Math.abs(endX-startX),Math.abs(endY-startY));
            int x1=(startX+endX)/2;
            int y1=(startY+endY)/2;
            g.fillRect(x1-2,startY-2,5,5);
            g.fillRect(x1-2,endY-2,5,5);
            g.fillRect(startX-2,y1-2,5,5);
            g.fillRect(endX-2,y1-2,5,5);
            g.fillRect(startX-2,startY-2,5,5);
            g.fillRect(startX-2,endY-2,5,5);
            g.fillRect(endX-2,startY-2,5,5);
            g.fillRect(endX-2,endY-2,5,5);
            rec[0]=new Rectangle(x-5,y-5,10,10);
            rec[1]=new Rectangle(x1-5,y-5,10,10);
            rec[2]=new Rectangle((startX>endX?startX:endX)-5,y-5,10,10);
            rec[3]=new Rectangle((startX>endX?startX:endX)-5,y1-5,10,10);
            rec[4]=new Rectangle((startX>endX?startX:endX)-5,(startY>endY?startY:endY)-5,10,10);
            rec[5]=new Rectangle(x1-5,(startY>endY?startY:endY)-5,10,10);
            rec[6]=new Rectangle(x-5,(startY>endY?startY:endY)-5,10,10);
            rec[7]=new Rectangle(x-5,y1-5,10,10);
        }
        public void mouseMoved(MouseEvent me){
            if(select.contains(me.getPoint())){
                this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                current=States.MOVE;
            } else{
                States[] st=States.values();
                for(int i=0;i<rec.length;i++){
                    if(rec[i].contains(me.getPoint())){
                        current=st[i];
                        this.setCursor(st[i].getCursor());
                        return;
                    }
                }
                this.setCursor(cs);
                current=States.DEFAULT;
            }
        }
        public void mouseExited(MouseEvent me){
            
        }
        public void mouseEntered(MouseEvent me){
            
        }
        public void mouseDragged(MouseEvent me){
            int x=me.getX();
            int y=me.getY();
            if(current==States.MOVE){
                startX+=(x-tempX);
                startY+=(y-tempY);
                endX+=(x-tempX);
                endY+=(y-tempY);
                tempX=x;
                tempY=y;
            }else if(current==States.EAST){
                if(startX>endX){
                    startX+=(x-tempX);
                    tempX=x;
                } else{
                    endX+=(x-tempX);
                    tempX=x;
                }
            }else if(current==States.NORTH){
                if(startY<endY){
                    startY+=(y-tempY);
                    tempY=y;
                }else{
                    endY+=(y-tempY);
                    tempY=y;
                }
            }else if(current==States.WEST){
                if(startX<endX){
                    startX+=(x-tempX);
                    tempX=x;
                } else{
                    endX+=(x-tempX);
                    tempX=x;
                }
            }else if(current==States.SOUTH){
                if(startY>endY){
                    startY+=(y-tempY);
                    tempY=y;
                }else{
                    endY+=(y-tempY);
                    tempY=y;
                }
            } else if(current==States.NORTH_EAST){
                if(startX>endX){
                    startX+=(x-tempX);
                    tempX=x;
                } else{
                    endX+=(x-tempX);
                    tempX=x;
                }
                if(startY<endY){
                    startY+=(y-tempY);
                    tempY=y;
                }else{
                    endY+=(y-tempY);
                    tempY=y;
                }
            }else if(current==States.NORTH_WEST){
                if(startX<endX){
                    startX+=(x-tempX);
                    tempX=x;
                } else{
                    endX+=(x-tempX);
                    tempX=x;
                }
                if(startY<endY){
                    startY+=(y-tempY);
                    tempY=y;
                }else{
                    endY+=(y-tempY);
                    tempY=y;
                }
            }else if(current==States.SOUTH_EAST){
                if(startY>endY){
                    startY+=(y-tempY);
                    tempY=y;
                }else{
                    endY+=(y-tempY);
                    tempY=y;
                }
                if(startX>endX){
                    startX+=(x-tempX);
                    tempX=x;
                } else{
                    endX+=(x-tempX);
                    tempX=x;
                }
            }else if(current==States.SOUTH_WEST){
                if(startY>endY){
                    startY+=(y-tempY);
                    tempY=y;
                }else{
                    endY+=(y-tempY);
                    tempY=y;
                }
                if(startX<endX){
                    startX+=(x-tempX);
                    tempX=x;
                } else{
                    endX+=(x-tempX);
                    tempX=x;
                }
            }

            else{
                startX=tempX;
                startY=tempY;
                endX=me.getX();
                endY=me.getY();
            }
            this.repaint();
        }
        public void mousePressed(MouseEvent me){
            tempX=me.getX();
            tempY=me.getY();
        }
        public void mouseReleased(MouseEvent me){
            if(me.isPopupTrigger()){
                if(current==States.MOVE){
                    startX=0;
                    startY=0;
                    endX=0;
                    endY=0;
                    repaint();
                } else{
                    jf.dispose();
                    updates();
                }
                
            }
        }
        public void mouseClicked(MouseEvent me){
            if(me.getClickCount()==2){
                //Rectangle rec=new Rectangle(startX,startY,Math.abs(endX-startX),Math.abs(endY-startY));
                Point p=me.getPoint();
                if(select.contains(p)){
                    if(select.x+select.width<this.getWidth()&&select.y+select.height<this.getHeight()){
                        get=bi.getSubimage(select.x,select.y,select.width,select.height);
                        jf.dispose();
                        save.setEnabled(true);
                        updates();
                    }else{
                        int wid=select.width,het=select.height;
                        if(select.x+select.width>=this.getWidth()){
                            wid=this.getWidth()-select.x;
                        }
                        if(select.y+select.height>=this.getHeight()){
                            het=this.getHeight()-select.y;
                        }
                        get=bi.getSubimage(select.x,select.y,wid,het);
                        jf.dispose();
                        save.setEnabled(true);
                        updates();
                    }
                    
                }
            }
        }
    }
    
    public static void main(String args[]){
        new CaptureScreen();
    }
}
enum States{
    NORTH_WEST(new Cursor(Cursor.NW_RESIZE_CURSOR)),//表示西北角
    NORTH(new Cursor(Cursor.N_RESIZE_CURSOR)),
    NORTH_EAST(new Cursor(Cursor.NE_RESIZE_CURSOR)),
    EAST(new Cursor(Cursor.E_RESIZE_CURSOR)),
    SOUTH_EAST(new Cursor(Cursor.SE_RESIZE_CURSOR)),
    SOUTH(new Cursor(Cursor.S_RESIZE_CURSOR)),
    SOUTH_WEST(new Cursor(Cursor.SW_RESIZE_CURSOR)),
    WEST(new Cursor(Cursor.W_RESIZE_CURSOR)),
    MOVE(new Cursor(Cursor.MOVE_CURSOR)),
    DEFAULT(new Cursor(Cursor.DEFAULT_CURSOR));
    private Cursor cs;
    States(Cursor cs){
        this.cs=cs;
    }
    public Cursor getCursor(){
        return cs;
    }
}
