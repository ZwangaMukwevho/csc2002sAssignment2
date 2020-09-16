import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseClick extends MouseAdapter {

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

        water waterObj1 = new water(e.getX()+1,e.getY());
        waterObj1.setWaterDepth();
        panel.addWater(waterObj1);

       water waterObj2 = new water(e.getX()-1,e.getY());
        waterObj2.setWaterDepth();
        panel.addWater(waterObj2);

        water waterObj3 = new water(e.getX(),e.getY()-1);
        waterObj3.setWaterDepth();
        panel.addWater(waterObj3);

        water waterObj4 = new water(e.getX(),e.getY()+1);
        waterObj4.setWaterDepth();
        panel.addWater(waterObj4);
       
    }
}
