import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
public class GameV extends JFrame implements ActionListener {
	/* �������Ż� staticռ��*/
	
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
        frame=new GameV("������Ϸ");
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
        SETTIME=new JButton("ȷ������");
        ok = new JButton("ȷ��");
        clear=new JButton("���");
        init=new JButton("�������ϸ��");
        startNext=new JButton("��һ���ݻ�");
        start=new JButton("��ʼ������ݻ�");
        stop=new JButton("ֹͣ������ݻ�");
        GMap=new JButton[GameC.Width][GameC.Height];
        rowCells=new JTextField(2);
        colCells=new JTextField(2);
        EXIT=new JButton("�˳�");
        rowCells.setText("");
        colCells.setText("");
        row=new JLabel("��ϸ��");
        col=new JLabel("��ϸ��");
        setTime=new JTextField(3);
        setTime.setText("");
        settime=new JLabel("��������");
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

        for(int i=0;i<logic.getWidth();i++)//��ʼ��ϸ����ɫ
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
        for(int i=0;i< logic.getWidth();i++)//����ϸ����ɫ
            for(int j=0;j< logic.getHeight();j++){
                if(GameC.Map[i][j]==0)
                    GMap[i][j].setBackground(Color.WHITE);
                else
                    GMap[i][j].setBackground(Color.BLACK);
            }
    }
}
