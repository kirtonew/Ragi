import java.util.Random;
public class GameControl {
    int[][] Map;
    int Height;
    int Width;
    GameLogic logic=new GameLogic();
    GameControl(int height,int width){
    	 //      第一处优化
    	// for(int j=0;j<100000;j++)
        this.Height=height;
        this.Width=width;
         
        logic.setHeight(height);
        logic.setWidth(width);
        
        //初始化
        Map=new int[Width][Height];

        for (int i = 0; i < Width; i++) {
            for (int j = 0; j < Height; j++) {
                Map[i][j] = 0;
            }
        }

    }
    public void deleteMap(){

        for (int i = 0; i < Width; i++)
            for (int j = 0; j < Height; j++)
                Map[i][j]=0;
        }
    public void reinitMap(){
        Random random = new Random();

        for (int i = 0; i < Width; i++) {
            for (int j = 0; j < Height; j++) {
                Map[i][j] = Math.abs(random.nextInt() % 2);
            }
        }
    }
    public void setMap(){
        Map=logic.setLifeState(Map);
    }
    public int setMap(int i,int j){
        if(Map[i][j]==0)Map[i][j]=1;
        else Map[i][j]=0;
        return Map[i][j];
    }

}

