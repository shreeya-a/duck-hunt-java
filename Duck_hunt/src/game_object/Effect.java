package game_object;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Effect{
    private final double x;
    private final double y;
    private final double max_dis;
    private final int  max_size;
    private final Color color;
    private final int totalEffect;
    private final float speed;
    private  double current_dis;
    private ModelBoom booms[];
    private float alpha = 1f;

    public Effect(double x, double y, double max_dis, int max_size, Color color, int totalEffect, float speed) {
        this.x = x;
        this.y = y;
        this.max_dis = max_dis;
        this.max_size = max_size;
        this.color = color;
        this.totalEffect = totalEffect;
        this.speed = speed;
        createRandom();
    }
    public void createRandom(){
        booms = new ModelBoom[totalEffect];
        float per = 360f/totalEffect;
        Random random = new Random();
        for (int i =1; i<=totalEffect; i++){
            int r = random.nextInt((int) per ) +1;
            int boomSize = random.nextInt(max_size)+1;
            float angle =  i* per +r;
            booms[i-1] = new ModelBoom(boomSize, angle);
        }
    }
    public void draw(Graphics2D g){
        AffineTransform oldTransform = g.getTransform();
        Composite oldComposite = g.getComposite();
        g.setColor(color);
        g.translate(x,y);
        for(ModelBoom b : booms){
            double bx = Math.cos(Math.toRadians(b.getAngle()))*current_dis;
            double by = Math.sin(Math.toRadians(b.getAngle()))*current_dis;
            double boomSize = b.getSize();
            double space = boomSize/2;
            if(current_dis>= max_dis-(max_dis*0.7f)){
                alpha = (float) ((max_dis-current_dis)/(max_dis*0.7f));
            }
            if(alpha>1){
                alpha = 1;
            } else if (alpha < 0) {
                alpha =0;
            }else {
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
                g.fill(new Rectangle2D.Double(bx-space,by-space,boomSize,boomSize));
            }
        }
        g.setComposite(oldComposite);
        g.setTransform(oldTransform);
    }
//    for moving effect
    public void update(){
                current_dis += speed;
    }
    public boolean check(){
        return current_dis < max_dis;
    }
}
