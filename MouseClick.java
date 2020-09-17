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

        water waterObj1;
        waterObj1 = new water(e.getX()+1,e.getY());
        waterObj1.setWaterDepth();
        panel.addWater(waterObj1);
        

       for(int i = 1; i<=7;i++){
         for(int j = 1;j<=7;j++){
             waterObj1 = new water(e.getX()+i,e.getY()+j);
            waterObj1.setWaterDepth();
            panel.addWater(waterObj1);}
       }
       
    }
}
