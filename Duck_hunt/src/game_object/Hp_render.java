package game_object;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Hp_render {
    private final HP hp;
    public Hp_render(HP hp){
        this.hp=hp;
    }

    protected void HpRender(Graphics2D g,Shape shape,double y){
//        if(hp.getCurrent_hp()!=hp.getMax_hp()) {
            double hpY = shape.getBounds().getY()-y-10;
            g.setColor(new Color(70, 70, 70));
            g.fill(new Rectangle2D.Double(0, hpY, Player.psize, 2));
            g.setColor(new Color(253, 91, 91));
            double hpSize = hp.getCurrent_hp() / hp.getMax_hp() * Player.psize;
            g.fill(new Rectangle2D.Double(0, hpY, hpSize, 2));
        }
//    }
    public boolean updateHP(double cutHP){
        hp.setCurrent_hp(hp.getCurrent_hp()- cutHP);
        return hp.getCurrent_hp()>0;
    }
    public double getHP(){
        return hp.getCurrent_hp();
    }
    public void resetHP(){
        hp.setCurrent_hp(hp.getMax_hp());
    }
}
