import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
public class GameV extends JFrame implements ActionListener {
	/* 第三处优化 static占用*/
	
//	private   static JButton[][] GMap;
//    private  static GameControl GameC;
    
    private    JButton[][] GMap;
    private   GameControl GameC;

  
    private JButton clear,init,startNext,start,stop,ok,EXIT,SETTIME;
    private JTextField rowCells,colCells,setTime;
    private JLabel row,col,settime;
    private static GameV frame;
    private Thread thread;
    private int N=0;
    private int TIME=800;
    GameLogic logic =new GameLogic();
    public static void main(String args[]){
        frame=new GameV("生命游戏");
    }
    GameV(String name){
        super(name);
        initGameV();
    }
    public void initGameV(){
        GameC=new GameControl(logic.getHeight(),logic.getWidth());
        JPanel backPanel,centerPanel,bottomPanel;
        backPanel=new JPanel(new BorderLayout());
        centerPanel=new JPanel(new GridLayout(logic.getWidth(), logic.getHeight()));
        bottomPanel=new JPanel();
        this.setContentPane(backPanel);
        backPanel.add(centerPanel,"Center");
        backPanel.add(bottomPanel,"North");
        SETTIME=new JButton("确定周期");
        ok = new JButton("确定");
        clear=new JButton("清空");
        init=new JButton("随机生成细胞");
        startNext=new JButton("下一步演化");
        start=new JButton("开始不间断演化");
        stop=new JButton("停止不间断演化");
        GMap=new JButton[GameC.Width][GameC.Height];
        rowCells=new JTextField(2);
        colCells=new JTextField(2);
        EXIT=new JButton("退出");
        rowCells.setText("");
        colCells.setText("");
        row=new JLabel("行细胞");
        col=new JLabel("列细胞");
        setTime=new JTextField(3);
        setTime.setText("");
        settime=new JLabel("繁衍周期");
        bottomPanel.add(settime);
        bottomPanel.add(setTime);
        bottomPanel.add(SETTIME);
        bottomPanel.add(col);
        bottomPanel.add(rowCells);
        bottomPanel.add(row);
        bottomPanel.add(colCells);
        bottomPanel.add(ok);
        bottomPanel.add(clear);
        bottomPanel.add(init);
        bottomPanel.add(start);
        bottomPanel.add(startNext);
        bottomPanel.add(stop);
        bottomPanel.add(EXIT);

        for(int i=0;i<logic.getWidth();i++)//初始化细胞颜色
            for(int j=0;j< logic.getHeight();j++){
                GMap[i][j]=new JButton("");
                GMap[i][j].addActionListener(this);
                if(GameC.Map[i][j]==0)
                    GMap[i][j].setBackground(Color.WHITE);
                else
                    GMap[i][j].setBackground(Color.BLACK);
                centerPanel.add(GMap[i][j]);
            }
        this.setSize(1000,800);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
        SETTIME.addActionListener(this);
        clear.addActionListener(this);
        ok.addActionListener(this);
        init.addActionListener(this);
        startNext.addActionListener(this);
        start.addActionListener(this);
        stop.addActionListener(this);
        EXIT.addActionListener(this);
    }
    //@edu.umd.cs.findbugs.annotations.SuppressFBWarnings({"DM_BOXED_PRIMITIVE_FOR_PARSING", "DM_EXIT"})
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==SETTIME){
            TIME=Integer.valueOf(setTime.getText());
        }
        else if(e.getSource()==ok){
            logic.setHeight(Integer.valueOf(rowCells.getText()));
            logic.setWidth(Integer.valueOf(colCells.getText()));
            GameC=new GameControl(logic.getHeight(), logic.getWidth());
            initGameV();
        }
        else if(e.getSource()==clear){
            GameC.deleteMap();
            frame.showMap();
        }else if(e.getSource()==init){
            GameC.reinitMap();
            frame.showMap();
        }else if(e.getSource()==startNext){
            frame.updateMap();
            frame.showMap();
        }else if(e.getSource()==start){
            if(N==0){
            N=1;
            thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    while(N==1){
                        frame.updateMap();
                        frame.showMap();
                        try{
                            TimeUnit.MILLISECONDS.sleep(TIME);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
            });
            thread.start();}
        }else if(e.getSource()==stop){
            N=0;
            thread=null;
        }else if(e.getSource()==EXIT){
            System.exit(0);
        }else{
            for(int i=0;i<logic.getWidth();i++)
                for(int j=0;j< logic.getHeight();j++){
                    if(e.getSource()==GMap[i][j]){
                        GameC.setMap(i,j);
                    }
                }
                frame.showMap();
        }
    }
    public void updateMap(){
        GameC.setMap();
    }
    public void showMap(){
        for(int i=0;i< logic.getWidth();i++)//更新细胞颜色
            for(int j=0;j< logic.getHeight();j++){
                if(GameC.Map[i][j]==0)
                    GMap[i][j].setBackground(Color.WHITE);
                else
                    GMap[i][j].setBackground(Color.BLACK);
            }
    }
}
