import java.awt.Color;
import java.awt.Graphics;


public class water {
    private Color color;
    private int x;
    private int y;
    private int waterDepth;

    public water(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Color getColor(){
        return color;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }


    public synchronized int getWaterDepth(){
        return waterDepth;
    }

    public synchronized void increamentDepth(){
        waterDepth++;
    }

    public synchronized void decreamenetDepth(){
        waterDepth--;
    }

    public synchronized void setWaterDepth(){
        waterDepth = 4;
    }

    


    public void draw(Graphics g){
    g.setColor(Color.BLUE);
    g.fillRect(x, y, 3, 3);
    
    }
    

    public void drawFlow(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 1, 1);
    }

}
