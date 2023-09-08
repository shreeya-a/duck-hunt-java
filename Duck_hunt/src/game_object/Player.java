package game_object;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.util.Objects;

public class Player extends Hp_render{
    public Player(){
        super(new HP(50,50));
        this.image = new ImageIcon(getClass().getResource("/game_image/plane.png")).getImage();
        this.image_speed = new ImageIcon(getClass().getResource("/game_image/plane.png")).getImage();
        Path2D p =new Path2D.Double();
        p.moveTo(0,15);
        p.lineTo(20,5);
        p.lineTo(psize+15,psize/2);
        p.lineTo(20,psize-5);
        p.lineTo(0,psize-15);
        playershap = new Area(p);
    }

    public static final double psize = 64;
    private double x;
    private double y;
    private final float Max_Speed=1f;
    private boolean speedUp;
    private float speed = 0f;
    private float angle = 0f;
    private final Area playershap;
    private final Image image;
    private final Image image_speed;
    private boolean alive = true;
    public void changeLocation(double x,double y){
        this.x = x;
        this.y = y;
    }
    public void update(){
        x+=Math.cos(Math.toRadians(angle))*speed;
        y+=Math.sin(Math.toRadians(angle))*speed;
    }
    public void changeAngle(float angle){
        if(angle<0){
            angle = 359;
        } else if (angle>359) {
            angle=0;
        }
        this.angle = angle;
    }
    public void draw(Graphics2D g){
        AffineTransform oldTransform = g.getTransform();
        g.translate(x,y);
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle +45),psize/2,psize/2);
        g.drawImage(speedUp ? image_speed:image,tran,null);
        HpRender(g,getShape(),y);
        g.setTransform(oldTransform);
//        g.setColor(new Color(12,173,84));
//        g.draw(getShape());
    }
    public Area getShape(){
        AffineTransform afx = new AffineTransform();
        afx.translate(x,y);
        afx.rotate(Math.toRadians(angle+45),psize/2,psize/2);
        return new Area(afx.createTransformedShape(playershap));
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    public float getangle() {
        return angle;
    }
    public void speedUp(){
        speedUp=true;
        if(speed>Max_Speed){
            speed=Max_Speed;
        }else{
            speed+=0.01f;
        }
    }
    public void speedDown(){
        speedUp=false;
        if(speed<=0){
            speed=0;
        }else{
            speed-=0.003f;
        }
    }
    public boolean isAlive(){
        return alive;
    }
    public void setAlive(boolean alive){
        this.alive = alive;
    }
    public void reset(){
        alive= true;
        resetHP();
        angle=0;
        speed = 0;

    }
}
