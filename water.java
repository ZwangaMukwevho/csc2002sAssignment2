import java.awt.Color;
import java.awt.Graphics;

public class water {
    private Color color;
    private int x;
    private int y;

    public water(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Color getColor(){
        return color;
    }

    public void draw(Graphics g){
    g.setColor(Color.BLUE);
    g.fillOval(x, y, 100, 100);
    

    }

}
