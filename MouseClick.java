import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class MouseClick extends MouseAdapter {

    Terrain land;
    private FlowPanel panel;
    public MouseClick(FlowPanel p){
        super();
        panel = p;
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        //water waterObj = new water(e.getX(),e.getY());
        //waterObj.setWaterDepth();
        //panel.addWater(waterObj);

        panel.fillWater(e.getX(), e.getY());

    }
}
