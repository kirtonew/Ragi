public class GameLogic {
    private int width = 10;
    private int height = 10;
    public int[][] setLifeState(int[][] Map){//���µ�ͼ
        int[][] nextMap=new int[width][height];
        for(int i=0;i<width;i++)
            for(int j=0;j<height;j++){
                nextMap[i][j]=findNextState(i,j,Map);
            }
        return nextMap;
    }
    public int findNextState(int x,int y,int[][] Map) {//����ϸ������һ������״̬
        int num = 0;
        int State = Map[x][y];
        if(x!=0)num+=Map[x-1][y];//���
        if(y!=0)num+=Map[x][y-1];//�ϱ�
        if(x!=width-1)num+=Map[x+1][y];//�ұ�
        if(y!=height-1)num+=Map[x][y+1];//�±�
        if(x!=0&&y!=0)num+=Map[x-1][y-1];//����
        if(x!=0&&y!=height-1)num+=Map[x-1][y+1];//����
        if(x!=width-1&&y!=0)num+=Map[x+1][y-1];//����
        if(x!=width-1&&y!=height-1)num+=Map[x+1][y+1];//����
        if(Map[x][y]==1) 
        {
        	if(num==3||num==2);
        	else State=0;
        }
        else if(num==3)
        		State=1;
        
        //�ڶ���
//        if(Map[x][y]==1) 
//        {
//        	if(num==3||num==2);
//        	else State=0;
//        }
//        else if(num==3)
//        		State=1;
//        	
     
        
        return State;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public void setWidth(int width){
        this.width=width;
    }
    public void setHeight(int height){
        this.height=height;
    }


}

