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
       panel.addWater(new water(e.getX(),e.getY()));
       
    }
}
